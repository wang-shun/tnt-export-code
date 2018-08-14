package com.lvmama.tnt.biz.service.impl.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.lvmama.tnt.biz.service.TntBatchOrderService;
import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.common.domain.OrderInfoDTO;
import com.lvmama.tnt.common.domain.PageResponseDTO;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.common.util.BeanCopyUtils;
import com.lvmama.tnt.dal.domain.TntBatchOrderPO;
import com.lvmama.tnt.dal.domain.TntCodeBatchJoinCodeGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeBatchPO;
import com.lvmama.tnt.export.code.api.constant.BatchStatus;
import com.lvmama.tnt.export.code.api.constant.ExportCodeType;
import com.lvmama.tnt.export.code.api.domain.vo.ExportBatchInfoVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportOrderVO;
import com.lvmama.tnt.export.code.api.service.ITntBatchOrderRemoteService;
import com.lvmama.tnt.reference.service.IScenicRefService;
import com.lvmama.tnt.reference.service.ITntOrderRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@Service(group = "export",timeout = 600000)
public class TntBatchOrderRemoteServiceImpl implements ITntBatchOrderRemoteService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger logger = LoggerFactory.getLogger(TntBatchOrderRemoteServiceImpl.class);

    @Autowired
    private TntCodeBatchService tntCodeBatchService;
    @Autowired
    private TntBatchOrderService tntBatchOrderService;
    @Autowired
    private ITntOrderRefService tntOrderRefService;

    @Autowired
    private IScenicRefService scenicRefService;

    @Override
    public PageResponseDTO<ExportOrderVO> findOrderByPage(ExportOrderVO exportOrderVO) {
        PageResponseDTO<ExportOrderVO> responseDTO = new PageResponseDTO<>();
        if (exportOrderVO == null || StringUtils.isEmpty(exportOrderVO.getBatchNo())) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("batchNo is null");
            return responseDTO;
        }
        try {
            //step1 batchInfo
            String batchNo = exportOrderVO.getBatchNo();
            TntCodeBatchJoinCodeGoodsPO joinCodeGoodsPO = tntCodeBatchService.findByBatchNo(batchNo);
            ExportOrderVO vo = new ExportOrderVO();
            BeanCopyUtils.copyBean(joinCodeGoodsPO, vo);
            vo.setUserId(exportOrderVO.getUserId());
            vo.setOrderSuccessNum(tntBatchOrderService.countOrderSuccessNum(joinCodeGoodsPO.getBatchNo()));
            vo.setCodeSuccessNum(tntBatchOrderService.countPassCodeSuccessNum(joinCodeGoodsPO.getBatchNo()));
            //step2 batch oneToMany order
            TntBatchOrderPO orderPO = new TntBatchOrderPO();
            orderPO.setBatchNo(batchNo);
            orderPO.setOrderId(exportOrderVO.getOrderId());
            long totalCount = tntBatchOrderService.totalCount(orderPO);
            List<TntBatchOrderPO> listBatchOrder = tntBatchOrderService.findByPage(orderPO, exportOrderVO.getPageNo(), exportOrderVO.getPageSize());

            //step3 query orderInfo
            List<Long> orderIds = new ArrayList<>();
            Map<Long, TntBatchOrderPO> batchOrderMap = new HashMap<>();
            for (TntBatchOrderPO po : listBatchOrder) {
                orderIds.add(po.getOrderId());
                batchOrderMap.put(po.getOrderId(), po);
            }
            List<OrderInfoDTO> orderInfoDTOS = tntOrderRefService.findOrdersByIds(orderIds, exportOrderVO.getUserId(), exportOrderVO.getPerformStatus());
            for (OrderInfoDTO dto : orderInfoDTOS) {
                dto.setGoodsName(joinCodeGoodsPO.getGoodsName());
                dto.setGoodsId(joinCodeGoodsPO.getGoodsId());
                dto.setBatchNo(joinCodeGoodsPO.getBatchNo());
                dto.setPaymentType(joinCodeGoodsPO.getPaymentType());
                dto.setPassCodeStatus(batchOrderMap.get(dto.getOrderId()).getPasscodeApplyStatus());
                dto.setPaymentStatus(batchOrderMap.get(dto.getOrderId()).getPaymentStatus());
            }
            vo.setBatchOrderDTOS(orderInfoDTOS);

            responseDTO.setTotalCount(totalCount);
            responseDTO.setResult(vo);
        } catch (Exception e) {
            logger.error("", e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }


        return responseDTO;
    }

    private static int create_order_time = 5;
    private static int threshold = 5*60;
    private static String waiting_template = "开启任务：预计等待 %d 分钟开启！";
    private static String finish_template = "订单成功数：%d，凭证码成功数：%d。";
    private static String processing_template = "任务预计将花费%d分钟完成，已进行%d分钟！";
    public static final SimpleDateFormat querySdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public PageResponseDTO<List<ExportBatchInfoVO>> findByPage(ExportBatchInfoVO exportBatchInfoVO) {
        PageResponseDTO<List<ExportBatchInfoVO>> responseDTO = new PageResponseDTO<>();
        TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO = new TntCodeBatchJoinCodeGoodsPO();
        try {
            int pageNO = exportBatchInfoVO.getPageNo();
            int pageSize = exportBatchInfoVO.getPageSize();
            BeanCopyUtils.copyBean(exportBatchInfoVO, tntCodeBatchJoinCodeGoodsPO);
            if (!StringUtils.isEmpty(exportBatchInfoVO.getCreateTimeStartStr())) {
                tntCodeBatchJoinCodeGoodsPO.setCreateTime(querySdf.parse(exportBatchInfoVO.getCreateTimeStartStr()));
            }
            if (!StringUtils.isEmpty(exportBatchInfoVO.getCreateTimeEndStr())) {
                tntCodeBatchJoinCodeGoodsPO.setCreateTimeB(querySdf.parse(exportBatchInfoVO.getCreateTimeEndStr()));
            }

            Long totalCount = tntCodeBatchService.totalCountByJoin(tntCodeBatchJoinCodeGoodsPO);
            List<TntCodeBatchJoinCodeGoodsPO> listPO = tntCodeBatchService.findByPageJoin(tntCodeBatchJoinCodeGoodsPO, pageNO, pageSize);
            List<ExportBatchInfoVO> list = new ArrayList<>();
            ExportBatchInfoVO vo = null;
            Map<String, Integer> waitCodeMap = new HashMap<>();
            List<Map<String,Integer>> waitingCodes = tntCodeBatchService.queryWaitingCodeNum();
            for (Map map : waitingCodes) {
                waitCodeMap.put((String)map.get("key"), (Integer)map.get("value"));
            }
            for (TntCodeBatchJoinCodeGoodsPO po : listPO) {
                vo = new ExportBatchInfoVO();
                BeanCopyUtils.copyBean(po, vo);
                vo.setCreateTimeStart(po.getCreateTime());
                vo.setBatchEndTime(po.getUpdateTime());
                vo.setOrderSuccessNum(tntBatchOrderService.countOrderSuccessNum(po.getBatchNo()));
                vo.setCodeSuccessNum(tntBatchOrderService.countPassCodeSuccessNum(po.getBatchNo()));
                try {
                    if (BatchStatus.FINISHED.name().equals(po.getBatchStatus())) {
                        vo.setStatusDesc(String.format(finish_template, vo.getOrderSuccessNum(), vo.getCodeSuccessNum()));
                    } else if (BatchStatus.WAITING.name().equals(vo.getBatchStatus())) {
                        //开启任务预计等待时间=单个导码订单时间*等待导码数量+单批次延迟风险阈值（暂定5分钟）*批次数量
                        Integer sum = 0;
                        for (Integer num : waitCodeMap.values()) {
                            sum += (create_order_time * num + threshold);
                        }
                        vo.setStatusDesc(String.format(waiting_template,sum/60));
                        waitCodeMap.remove(po.getBatchNo());
                    } else if (BatchStatus.PROCESSING.name().equals(vo.getBatchStatus())) {
                        vo.setStatusDesc(String.format(processing_template, (create_order_time * vo.getCodeNum() + threshold) / 60, calculateTime(po.getUpdateTime())));
                    } else {
                        vo.setStatusDesc(po.getStatusMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add(vo);
            }
             responseDTO.setResult(list);
            responseDTO.setPageSize(pageSize);
            responseDTO.setPageNo(pageNO);
            responseDTO.setTotalCount(totalCount);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
            logger.error("", e);
        }

        return responseDTO;
    }

    private long calculateTime(Date updateTime) {
        long diff = System.currentTimeMillis() - updateTime.getTime();
        return diff/1000/60;
    }

    @Override
    public ResponseDTO<ExportBatchInfoVO> findByBatchNo(String batchNo, Long userId) {
        ResponseDTO<ExportBatchInfoVO> responseDTO = new ResponseDTO<ExportBatchInfoVO>();
        logger.info("find batch:{} detail info!", batchNo);
        try {
            ExportBatchInfoVO vo = new ExportBatchInfoVO();
            TntCodeBatchJoinCodeGoodsPO po = tntCodeBatchService.findByBatchNo(batchNo);
            BeanCopyUtils.copyBean(po, vo);
            vo.setCreateTimeStart(po.getCreateTime());
            vo.setBatchEndTime(po.getUpdateTime());
            //batch order sum amount
//            List<Long> orderIds = tntBatchOrderService.findAllOrderIdsByBatchNo(batchNo);
//            vo.setTotalOrderAmount(tntOrderRefService.ordersSumAmount(orderIds, userId));
            if (vo.getPrice() != null && vo.getCodeNum() != null) {
                vo.setTotalOrderAmount(vo.getPrice()*vo.getCodeNum());
            }
            vo.setPriceYuan();
            vo.setTotalOrderAmountYuan();

            responseDTO.setResult(vo);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
            logger.error("", e);
        }

        return responseDTO;
    }

    /**
     * 停止批次任务
     * @param batchNo
     * @return
     */
    @Override
    public ResponseDTO<String> stopBatchTask(String batchNo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (StringUtils.isEmpty(batchNo)) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("batchNo is null");
        }

        String batchStatus = null;
        String respMsg = "该批次任务已终止成功！";
        TntCodeBatchPO  tntCodeBatchPO = tntCodeBatchService.findByBatchNo(batchNo);
        if (tntCodeBatchPO != null) {
            batchStatus = tntCodeBatchPO.getBatchStatus();
        }
        if (BatchStatus.WAITING.name().equals(batchStatus)) {
            tntCodeBatchService.updateBatchStatusAndMessage(tntCodeBatchPO.getId(), BatchStatus.TERMINATE, "");
            //码库解锁
            if(ExportCodeType.HOARD.name().equals(tntCodeBatchPO.getCodeType())){

                boolean released = scenicRefService.releaseHoardStock(batchNo);
                logger.info("releaseHoardStock ："+batchNo +"，result:"+released);
            }

        } else {
            respMsg = "该批次任务已经开始执行，无法终止！";
        }
        logger.info("stop the {} batch task success!", batchNo);
        responseDTO.setRespMsg(respMsg);
        return responseDTO;
    }

    @Override
    public ResponseDTO<Boolean> createBatch(ExportBatchInfoVO exportBatchInfoVO) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();

        TntCodeBatchPO tntBatchOrderPO = convert2BatchPO(exportBatchInfoVO);

        boolean isSuccess = tntCodeBatchService.saveCodeBatch(tntBatchOrderPO);
        responseDTO.setSuccess(isSuccess);
        return responseDTO;
    }

    private  TntCodeBatchPO convert2BatchPO(ExportBatchInfoVO exportBatchInfoVO){
        TntCodeBatchPO tntBatchOrderPO = new TntCodeBatchPO();
        BeanCopyUtils.copyBean(exportBatchInfoVO, tntBatchOrderPO);
        //初始值0
        tntBatchOrderPO.setRetryCount(0);
        tntBatchOrderPO.setCreateTime(exportBatchInfoVO.getCreateTimeStart());
        tntBatchOrderPO.setUpdateTime(exportBatchInfoVO.getCreateTimeStart());
        return tntBatchOrderPO;
    }

    @Override
    public ResponseDTO<String> findBatchNo(Long orderId) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        TntBatchOrderPO batchOrderPO = tntBatchOrderService.findByOrderId(orderId);
        String batchNo = batchOrderPO == null ? "" : batchOrderPO.getBatchNo();

        responseDTO.setSuccess(true);
        responseDTO.setResult(batchNo);
        return responseDTO;
    }

    @Override
    public void updateBatchOrderStatus(Long orderId, String payStatus, String passcodeStatus) {
        TntBatchOrderPO updateItem = new TntBatchOrderPO();
        updateItem.setOrderId(orderId);
        updateItem.setPaymentStatus(payStatus);
        updateItem.setPasscodeApplyStatus(passcodeStatus);
        updateItem.setUpdateTime(new Date());
        tntBatchOrderService.updateBatchOrder(updateItem);

    }

    @Override
    public ResponseDTO<Void> updateBatchFileId(String batchNo, Long fileId) {
        if (StringUtils.isEmpty(batchNo)){
            throw new IllegalArgumentException("batchNo is null ");
        }

        boolean success = tntCodeBatchService.updateFileId(batchNo, fileId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(success);
        return  responseDTO;
    }
}
