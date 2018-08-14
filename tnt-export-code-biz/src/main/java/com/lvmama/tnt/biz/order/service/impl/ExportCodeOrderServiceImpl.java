package com.lvmama.tnt.biz.order.service.impl;

import com.lvmama.common.util.facade.Status;
import com.lvmama.scenic.api.vo.ResultHandleT;
import com.lvmama.scenic.api.vo.order.OrdPersonBaseVo;
import com.lvmama.scenic.api.vo.order.OrderBaseVo;
import com.lvmama.scenic.api.vo.order.ScenicOrderInfoBaseVo;
import com.lvmama.tnt.biz.order.service.ExportCodeOrderService;
import com.lvmama.tnt.biz.order.vo.OrderErrorCode;
import com.lvmama.tnt.biz.order.vo.OrderRequest;
import com.lvmama.tnt.biz.order.vo.OrderResponse;
import com.lvmama.tnt.biz.service.TntBatchOrderService;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.dal.domain.TntBatchOrderPO;
import com.lvmama.tnt.export.code.api.constant.ExportCodeType;
import com.lvmama.tnt.export.code.api.constant.OrderChannel;
import com.lvmama.tnt.export.code.api.constant.PasscodeStatus;
import com.lvmama.tnt.export.code.api.constant.PaymentStatus;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.TntOrderBaseVo;
import com.lvmama.tnt.reference.service.IScenicOrderRefService;
import com.lvmama.tnt.reference.service.impl.TntOrderServiceRemote;
import com.lvmama.tnt.reference.service.impl.VstCommOrderQueryServiceRemote;
import com.lvmama.vst.api.vo.order.OrderItemBaseVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 囤码下单实现
 * 1.创建主站订单
 * 2.创建分销订单
 * 3.保存批次订单表
 *
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
@Service("exportCodeOrderServiceImpl")
public class ExportCodeOrderServiceImpl implements ExportCodeOrderService {

    private static Logger logger = LoggerFactory.getLogger(ExportCodeOrderServiceImpl.class);

    @Autowired
    private IScenicOrderRefService scenicOrderRefService;

    @Autowired
    private VstCommOrderQueryServiceRemote vstCommOrderQueryServiceRemote;

    @Autowired
    private TntOrderServiceRemote tntOrderServiceRemote;

    @Autowired
    private TntBatchOrderService tntBatchOrderService;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        ScenicOrderInfoBaseVo baseVo = buildScenicOrderInfo(request);
        logger.info("exportCode create order, ScenicOrderInfoBaseVo :"+baseVo.toString());
        try {
            //间隔一秒钟下单
            Thread.sleep(1000L);

            ResultHandleT<OrderBaseVo> scenicOrderResult =  scenicOrderRefService.createOrder(baseVo);
            if(scenicOrderResult == null || scenicOrderResult.getReturnContent() == null
                    || scenicOrderResult.getReturnContent().getOrderId() == null){
                logger.error("vst order create error : userId:"+request.getUserId()+
                        ",goodsId:"+request.getGoodsId()+",visitDate:"+baseVo.getVisitTime()+
                        ", message:"+scenicOrderResult.getMsg());
                return new OrderResponse(null, Status.FAIL,
                        OrderErrorCode.SCENIC_ORDER_CREATE_ERROR.getCode(),
                        OrderErrorCode.SCENIC_ORDER_CREATE_ERROR.getMessage()+":"+scenicOrderResult.getMsg());

            }

            Long orderId = scenicOrderResult.getReturnContent().getOrderId();
            Long userId = request.getUserId();
            com.lvmama.vst.api.vo.ResultHandleT<com.lvmama.vst.api.vo.order.OrderBaseVo> resultHandleT =  vstCommOrderQueryServiceRemote.findOrderDetail(userId, orderId);
            if (resultHandleT.isFail() || resultHandleT.getReturnContent() == null){
                logger.info("findOrderDetail is null, order id:" + orderId);
                return new OrderResponse(null, Status.FAIL,null,"findOrderDetail is null");
            }

            com.lvmama.vst.api.vo.order.OrderBaseVo scenicOrder = resultHandleT.getReturnContent();
            TntOrderBaseVo tntOrder = new TntOrderBaseVo();
            tntOrder.setOrder(scenicOrder);
            tntOrder.setCategoryId(scenicOrder.getCategoryId());
            tntOrder.setUserId(userId);
            //订单渠道
            tntOrder.setChannelWay(OrderChannel.DIST_EXPORT_CODE.name());
            tntOrder.setCommissionAmount(null);
            tntOrder.setPartnerOrderId(null);
            tntOrder.setCreateOperator(request.getUserName());
            tntOrder.setOrderRefundType(request.getOrderRefundType());
            //订单金额
            tntOrder.setTotalDistSalePrice(request.getOrderSellAmount());
            tntOrder.setTotalDistSettlePrice(request.getOrderSettleAmount());
            tntOrder.setDiminishAmount(null);


            ResultGod<TntOrder> tntOrderResult =  tntOrderServiceRemote.createOrder(tntOrder);
            //保存批次订单表
            if ( tntOrderResult != null && tntOrderResult.isSuccess() && tntOrderResult.getResult() != null){
                saveBatchOrder(scenicOrder,tntOrderResult.getResult().getTntOrderId(), request.getBatchNo());
            }

            return  new OrderResponse(scenicOrderResult.getReturnContent().getOrderId(), Status.SUCCESS, null, null)
                    .buildOughtAmount(scenicOrder.getOughtAmount())
                    .buildOrderStatus(TntConstant.ORDER_STATUS.NORMAL.name())
                    .buildPaymentStatus(TntConstant.PAYMENT_STATUS.UNPAY.name())
                    .buildPaymentTarget(TntConstant.PAY_TARGET.PREPAID.name());


        }catch (Exception e){
            logger.error("create vst order error ", e);
            return new OrderResponse(null, Status.FAIL,
                    OrderErrorCode.EXPORT_CODE_CREATE_ORDER_ERROR.getCode(),
                    OrderErrorCode.EXPORT_CODE_CREATE_ORDER_ERROR.getMessage());
        }


    }



    /**
     * 保存批次订单
     * @param scenicOrder
     * @param tntOrderId
     * @param batchNo
     */
    private void saveBatchOrder(com.lvmama.vst.api.vo.order.OrderBaseVo scenicOrder, Long tntOrderId, String batchNo){
        if (scenicOrder !=null && CollectionUtils.isNotEmpty(scenicOrder.getOrderItemList())){
            for (OrderItemBaseVo itemOrder : scenicOrder.getOrderItemList()){

                TntBatchOrderPO tntBatchOrderPO = new TntBatchOrderPO();
                tntBatchOrderPO.setTntOrderId(tntOrderId);
                tntBatchOrderPO.setBatchNo(batchNo);
                tntBatchOrderPO.setOrderId(scenicOrder.getOrderId());
                tntBatchOrderPO.setOrderItemId(itemOrder.getOrderItemId());
//                tntBatchOrderPO.setPasscodeApplyStatus(PasscodeStatus.APPLY_PASSCODE.name());
                tntBatchOrderPO.setPaymentStatus(PaymentStatus.UNPAY.name());
                tntBatchOrderPO.setCreateTime(new Date());
                tntBatchOrderPO.setUpdateTime(new Date());
                tntBatchOrderService.saveBatchOrder(tntBatchOrderPO);

            }

        }



    }



    /**
     * 构建景乐订单信息
     * @return
     */
    private ScenicOrderInfoBaseVo buildScenicOrderInfo(OrderRequest request){

        ScenicOrderInfoBaseVo baseVo = new ScenicOrderInfoBaseVo();
        baseVo.setProductId(request.getProductId());
        baseVo.setIsUseForbidBuy("N");
        baseVo.setNeedGuarantee("UNGUARANTEE");
        String visitDate = DateFormatUtils.format(request.getVisitTime(), "yyyy-MM-dd");
        baseVo.setVisitTime(visitDate);

        if(ExportCodeType.HOARD.name().equals(request.getCodeType())){
            //囤码设置批次号
            baseVo.setBatchNo(request.getBatchNo());
        }
        //设置订单金额
//        baseVo.setOughtAmount(request.getLvmamaOrderAmount());
        baseVo.setOughtAmount(request.getOrderSettleAmount());
        baseVo.setDiscountAmount(request.getDiscountOrderAmount());
        baseVo.setCategoryId(request.getCategoryId());
        //固定ip
        baseVo.setIp(request.getUserIp());
        baseVo.setCustomerServiceTel(request.getCustomerServiceTel());
        baseVo.setDistributionSuffix(request.getDistributionSuffix());
        //会员信息
        baseVo.setUserId(request.getMemberNo());
        baseVo.setUserNo(request.getMemberId());
        baseVo.setDistributorCode("DISTRIBUTOR_B2B");
        baseVo.setDistributionChannel(request.getUserId());
        //realName与 companyName
        baseVo.setDistributorName(request.getUserName());

        //下单人
        OrdPersonBaseVo booker = new OrdPersonBaseVo();
        booker.setMobile(request.getDistributorMobile());
        booker.setFullName(request.getUserName());
        booker.setPersonType(OrdPersonBaseVo.ORDER_PERSON_TYPE.BOOKER.name());
        baseVo.setBooker(booker);

        //联系人
        OrdPersonBaseVo contact = new OrdPersonBaseVo();
        contact.setFullName(request.getContactName());
        contact.setMobile(request.getContactMobile());
        contact.setEmail(request.getContactEmail());
        contact.setFirstName(request.getEnFirstName());
        contact.setLastName(request.getEnLastName());
        contact.setPersonType(OrdPersonBaseVo.ORDER_PERSON_TYPE.CONTACT.name());
        baseVo.setContact(contact);

        //游玩人
        List<OrdPersonBaseVo> travellers = new ArrayList();
        OrdPersonBaseVo traveller = new OrdPersonBaseVo();
        traveller.setMobile(request.getContactMobile());
        traveller.setFullName(request.getContactName());
        traveller.setEmail(request.getContactEmail());
        traveller.setFirstName(request.getEnFirstName());
        traveller.setLastName(request.getEnLastName());
        //人群
        traveller.setPeopleType(request.getPeopleType());
        traveller.setPersonType(OrdPersonBaseVo.ORDER_PERSON_TYPE.TRAVELLER.name());
        travellers.add(traveller);
        baseVo.setTravellers(travellers);



        //组装item
        List<ScenicOrderInfoBaseVo.ItemInfo> itemInfos = new ArrayList<>();
        ScenicOrderInfoBaseVo.ItemInfo itemInfo = new ScenicOrderInfoBaseVo.ItemInfo();
        itemInfo.setGoodsId(request.getGoodsId());
        itemInfo.setMainItem("true");
        //1
        itemInfo.setQuantity(request.getQuantity());
        //null
        itemInfo.setAdultQuantity(request.getAdultQuantity());
        //null
        itemInfo.setChildQuantity(request.getChildQuantity());
        itemInfo.setVisitTime(visitDate);
        //导码订单不发短信
        itemInfo.setIsSendSmsDistribution("N");
        itemInfos.add(itemInfo);

        baseVo.setItemList(itemInfos);
        return baseVo;
    }



}
