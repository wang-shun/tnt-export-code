package com.lvmama.tnt.web.task;

import com.lvmama.boot.core.Contexts;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.utils.ComTaskInterrupt;
import com.lvmama.log.util.LogTrackContext;
import com.lvmama.order.api.base.vo.RequestBody;
import com.lvmama.order.api.base.vo.ResponseBody;
import com.lvmama.order.vo.comm.OrderVo;
import com.lvmama.tnt.biz.channel.NoticeRequest;
import com.lvmama.tnt.biz.channel.NoticeRequestFactory;
import com.lvmama.tnt.biz.channel.NoticeService;
import com.lvmama.tnt.biz.converter.Batch2OrderRequestConverter;
import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.biz.order.filter.OrderFilterChain;
import com.lvmama.tnt.biz.order.service.ExportCodeOrderService;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.biz.payment.ExportCodeOrderPaymentService;
import com.lvmama.tnt.biz.payment.OrderPaymentRequest;
import com.lvmama.tnt.biz.payment.OrderPaymentResponse;
import com.lvmama.tnt.biz.service.TntBatchOrderService;
import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import com.lvmama.tnt.export.code.api.constant.BatchStatus;
import com.lvmama.tnt.export.code.api.constant.ExportCodeType;
import com.lvmama.tnt.export.code.api.constant.PaymentType;
import com.lvmama.tnt.finance.common.vo.ErrorRes;
import com.lvmama.tnt.reference.service.IComlogService;
import com.lvmama.tnt.reference.service.IFinanceRefService;
import com.lvmama.tnt.reference.service.IScenicRefService;
import com.lvmama.tnt.reference.service.impl.IApiOrderQueryServiceRemote;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  导码下单支付job
 */
@RestController
@RequestMapping("/task/exportcode")
public class ExportCodeTask {
    private static final Logger logger = LoggerFactory.getLogger(ExportCodeTask.class);

    @Autowired
    private TntCodeBatchService tntCodeBatchService;

//    @Autowired
//    private OrderFilterChain orderFilterChain;

    @Autowired
    private ExportCodeOrderService exportCodeOrderService;

    @Autowired
    private ExportCodeOrderPaymentService exportCodeOrderPaymentService;

    @Autowired
    private IApiOrderQueryServiceRemote iApiOrderQueryServiceRemote;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private IScenicRefService scenicRefService;

    @Autowired
    private IComlogService comlogService;

    @Autowired
    private Batch2OrderRequestConverter batch2OrderRequestConverter;

    @Autowired
    private IFinanceRefService financeRefService;

    @Autowired
    private TntBatchOrderService tntBatchOrderService;

    @RequestMapping("/createBatchOrder")
    public TaskResult createBatchOrder(Long logId, String parameter){
        logger.info("do createBatchOrder task，parameter：" + parameter);
        TaskResult taskResult = new TaskResult();

        try{
            List<TntCodeBatchPO> processBatchs = tntCodeBatchService.findStatusBatch(BatchStatus.PROCESSING.name());
            if (CollectionUtils.isNotEmpty(processBatchs)){
                //如果存在批次进行中，job不做处理
                taskResult.setRunStatus(TaskResult.RUN_STATUS.SUCCESS);
                return taskResult;
            }

            List<TntCodeBatchPO> waitBatchs = tntCodeBatchService.findStatusBatch(BatchStatus.WAITING.name());
            for (TntCodeBatchPO waitBatch : waitBatchs){
                StringBuilder logContent = new StringBuilder();
                logContent.append(waitBatch.getBatchNo()).append(" processing");
                comlogService.addBatchLog(logContent.toString(), Long.valueOf(waitBatch.getBatchNo().substring(2)));
                runBatch(logId,waitBatch, waitBatch.getCodeNum());
            }

            taskResult.setRunStatus(TaskResult.RUN_STATUS.SUCCESS);
        } catch (Exception e){
            logger.error("createBatchOrder errors:", e);
            taskResult.setRunStatus(TaskResult.RUN_STATUS.FAILED);
            taskResult.setResult(e.toString());
        }
        return  taskResult;
    }

    /**
     * 执行批次
     * @return
     */
    private void runBatch(Long logId,TntCodeBatchPO batch, int needRunCount){
        logger.info(">>>>run batch start, batchNo:"+batch.getBatchNo());
        OrderFilterChain orderFilterChain = (OrderFilterChain) Contexts.getApplicationContext().getBean("orderFilterChain");

        //更新批次状态为进行时
        tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(), BatchStatus.PROCESSING, "");
        OrderRequest orderRequest = batch2OrderRequestConverter.convert(batch);
        OrderResponse orderResponse = new OrderResponse();
        try {
            orderFilterChain.process(orderRequest, orderResponse);
        }catch (Exception e){
            logger.error("orderFilterChain error：" + e.getMessage(), e);
            //更新异常状态
            tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(),BatchStatus.EXCEPTION, e.getMessage());
            //码库解锁
            releaseHoradStock(batch.getCodeType(), batch.getBatchNo());
            //Email通知宋俊保和李健
            NoticeRequest noticeRequest = NoticeRequestFactory.newExceptionNoticeRequest(batch.getBatchNo(),
                    batch.getGoodsId(), batch.getVisitTime(), e.getMessage());
            noticeService.notice(noticeRequest);
            comlogService.addBatchLog("批次异常", Long.valueOf(batch.getBatchNo().substring(2)));
            //下一个批次
            return ;
        }

        for (int i = 0; i < needRunCount; i++){

            //初始化trackNo
            LogTrackContext.initTrackNumber();
            ComTaskInterrupt.interruptJob(logId);
            orderResponse = exportCodeOrderService.createOrder(orderRequest);
            if (!orderResponse.isSuccess()){
                tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(),BatchStatus.UNFINISHED, orderResponse.getMessage());
                //下一个批次
                return;
            }

            if (!payOrder(orderResponse, batch)){
                return;
            }

        }

        //更新批次状态为已完成
        tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(), BatchStatus.FINISHED, "");
        //发送完成短信
        NoticeRequest noticeRequest = NoticeRequestFactory.newFinishedNoticeRequest(batch.getBatchNo(),
                batch.getContactMobile());
        noticeService.notice(noticeRequest);
        logger.info(">>>>run batch end, batchNo:"+batch.getBatchNo());
    }



    private boolean payOrder(OrderResponse orderResponse, TntCodeBatchPO batch){
        //订单在线支付且未支付
        if (TntConstant.PAY_TARGET.isPayToLvmama(orderResponse.getPaymentTarget())
                && TntConstant.PAYMENT_STATUS.isUnPayed(orderResponse.getPaymentStatus())){

            OrderPaymentRequest paymentRequest = new OrderPaymentRequest();
            paymentRequest.setUserId(batch.getUserId());
            paymentRequest.setOrderAmount(orderResponse.getOughtAmount());
            paymentRequest.setAccountType(batch.getPaymentType());
            paymentRequest.setOrderId(orderResponse.getOrderId());

            OrderPaymentResponse paymentResponse = exportCodeOrderPaymentService.pay(paymentRequest);
            if (!paymentResponse.isSuccess()){
                //余额不足
                if (ErrorRes.Blance.getErrorCode().equals(paymentResponse.getCode())){
                    //更新暂停状态
                    tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(),BatchStatus.STOP, "");
                    //通知分销商
                    Long needBalance = getRestOrderNum(batch).getRestUnpayNum() * batch.getPrice();

                    NoticeRequest noticeRequest = NoticeRequestFactory.newStopNoticeRequest(batch.getBatchNo(),
                            needBalance,batch.getContactMobile());
                    noticeService.notice(noticeRequest);

                }else {
                    tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(),BatchStatus.UNFINISHED,paymentResponse.getMessage());
                }
            }
            return paymentResponse.isSuccess();
        }
        return false;
    }



    @RequestMapping("/createStopBatchOrder")
    public TaskResult createStopBatchOrder(Long logId, String parameter){
        logger.info("do createStopBatchOrder task，parameter：" + parameter);
        TaskResult taskResult = new TaskResult();
        try{
            List<TntCodeBatchPO> stopBatchs = tntCodeBatchService.findStatusBatch(BatchStatus.STOP.name());
            for (TntCodeBatchPO stopBatch : stopBatchs){
                Date now = new Date();
                //暂停时间超过30分钟,直接终止
                if (now.after(DateUtils.addMinutes(stopBatch.getUpdateTime(), 30))){
                    tntCodeBatchService.updateBatchStatusAndMessage(stopBatch.getId(),BatchStatus.TERMINATE, ExportCodeConstant.STOP_FAIL_MESSAGE);
                    //码库解锁
                    releaseHoradStock(stopBatch.getCodeType(), stopBatch.getBatchNo());
                    NoticeRequest noticeRequest = NoticeRequestFactory.newStopFailNoticeRequest(stopBatch.getBatchNo(), stopBatch.getContactMobile());
                    noticeService.notice(noticeRequest);
                }else {
                    RestOrderNum orderNum = getRestOrderNum(stopBatch);
                    Long restOrderAmount = orderNum.getRestUnpayNum() * stopBatch.getPrice();
                    boolean balanceEnough = checkUserBalance(stopBatch.getUserId(), stopBatch.getPaymentType(), restOrderAmount);
                    if (balanceEnough){
                        payHoardUnpayedOrder(stopBatch);
                        runBatch(logId, stopBatch, orderNum.getRestOrderNum());
                    }
                }
            }

            taskResult.setRunStatus(TaskResult.RUN_STATUS.SUCCESS);
        } catch (Exception e){
            logger.error("createStopBatchOrder errors:", e);
            taskResult.setRunStatus(TaskResult.RUN_STATUS.FAILED);
            taskResult.setResult(e.toString());
        }
        return  taskResult;


    }


    private boolean checkUserBalance(Long userId, String paymentType, Long orderAmount){
        if (PaymentType.CASH.name().equals(paymentType)){
            ResponseDTO<Long> responseDTO =  financeRefService.validateCashPayBalance(userId, orderAmount);
            return responseDTO.isSuccess();
        }else if (PaymentType.CREDIT.name().equals(paymentType)){
            ResponseDTO<Long> responseDTO =  financeRefService.validateCreditPayBalance(userId, orderAmount);
            return responseDTO.isSuccess();
        }

        return false;

    }



    /**
     * 囤码产生的主站订单
     * @param batchNo
     * @return
     */
    private List<OrderVo> findHoardOrders(String batchNo){
        List<OrderVo> orders = new ArrayList<>();
        RequestBody<String> batchNoRequest = new RequestBody<>();
        batchNoRequest.setT(batchNo);
        ResponseBody<List<OrderVo>> response = iApiOrderQueryServiceRemote.listOrdorderByBatchNo(batchNoRequest);
        if (response != null && response.isSuccess()){
            orders= response.getT();
        }

        return orders;
    }



    private List<OrderResponse> convertUnpayOrderResponse(List<OrderVo> orderVos){
        List<OrderResponse> orderResponses = new ArrayList<>();
        if (CollectionUtils.isEmpty(orderVos)){
            return orderResponses;
        }
        for (OrderVo order : orderVos){
            //过滤已支付订单
            if (TntConstant.PAYMENT_STATUS.isPayed(order.getPaymentStatus())){
                continue;
            }

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(order.getOrderId());
            orderResponse.buildPaymentTarget(order.getPaymentTarget())
                    .buildPaymentStatus(order.getPaymentStatus())
                    .buildOrderStatus(order.getOrderStatus());
        }

        return orderResponses;
    }


    @RequestMapping("/createUnfinishedBatchOrder")
    public TaskResult createUnfinishedBatchOrder(Long logId, String parameter){
        logger.info("do createUnfinishedBatchOrder task，parameter：" + parameter);
        TaskResult taskResult = new TaskResult();

        try{
            List<TntCodeBatchPO> unfinishedBatchs = tntCodeBatchService.findStatusBatch(BatchStatus.UNFINISHED.name());
            for (TntCodeBatchPO batch : unfinishedBatchs){
                int retryCount = batch.getRetryCount();
                if (retryCount >= 5){
                    tntCodeBatchService.updateBatchStatusAndMessage(batch.getId(),BatchStatus.TERMINATE,"");

                    //码库解锁
                    releaseHoradStock(batch.getCodeType(), batch.getBatchNo());

                    NoticeRequest noticeRequest = NoticeRequestFactory.newTerminateNoticeRequest(batch.getBatchNo()
                            , batch.getContactMobile());
                    noticeService.notice(noticeRequest);
                    continue;
                }
                payHoardUnpayedOrder(batch);
                runBatch(logId, batch, getRestOrderNum(batch).getRestOrderNum());
                retryCount++;
                //更新
                tntCodeBatchService.updateRetryCount(batch.getId(), retryCount);

            }

            taskResult.setRunStatus(TaskResult.RUN_STATUS.SUCCESS);
        } catch (Exception e){
            logger.error("createUnfinishedBatchOrder errors:", e);
            taskResult.setRunStatus(TaskResult.RUN_STATUS.FAILED);
            taskResult.setResult(e.toString());
        }
        return  taskResult;

    }

    /**
     * 获得剩余订单数（剩余要创建订单数 和剩余要支付订单数）
     * @param batch
     * @return
     */
    private RestOrderNum getRestOrderNum(TntCodeBatchPO batch){
        RestOrderNum orderNum = new RestOrderNum();

        int restOrderNum = 0;
        int restUnpayNum = 0;

        if (ExportCodeType.HOARD.name().equals(batch.getCodeType())){
            List<OrderVo> orderVos = findHoardOrders(batch.getBatchNo());
            //占用码库以主站的订单为准
            restOrderNum = batch.getCodeNum() - orderVos.size();
            List<OrderResponse> orderResponses = convertUnpayOrderResponse(orderVos);
            restUnpayNum = restOrderNum + orderResponses.size();

        }else {
            restOrderNum = batch.getCodeNum() - tntBatchOrderService.countOrderSuccessNum(batch.getBatchNo());
            restUnpayNum = restOrderNum;
        }
        orderNum.setRestOrderNum(restOrderNum);
        orderNum.setRestUnpayNum(restUnpayNum);
        return orderNum;
    }

    /**
     * 支付已创建成功但未支付囤码订单
     * @param batch
     */
    private void payHoardUnpayedOrder(TntCodeBatchPO batch){
        if (ExportCodeType.HOARD.name().equals(batch.getCodeType())){
            List<OrderVo> orderVos = findHoardOrders(batch.getBatchNo());
            List<OrderResponse> orderResponses = convertUnpayOrderResponse(orderVos);
            //囤码占库存不能重复创建订单，所以获取已经生成但未支付的订单
            for (OrderResponse orderResponse : orderResponses){
                payOrder(orderResponse, batch);
            }

        }

    }

    /**
     * 码库解锁 解锁失败通知
     * @param codeType
     * @param batchNo
     */
    private void releaseHoradStock(String codeType, String batchNo){
        if(ExportCodeType.HOARD.name().equals(codeType)){
            boolean release = scenicRefService.releaseHoardStock(batchNo);
            if (!release){
                NoticeRequest noticeRequest = NoticeRequestFactory.newReleaseFailNoticeRequest(batchNo);
                noticeService.notice(noticeRequest);
            }
        }

    }

}
