package com.lvmama.tnt.biz.payment;

import com.lvmama.boot.starter.jms.JmsUtils;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.common.util.facade.Status;
import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.biz.order.service.impl.ExportCodeOrderServiceImpl;
import com.lvmama.tnt.enums.FinAccountTypeEnum;
import com.lvmama.tnt.enums.TradeTypeEnum;
import com.lvmama.tnt.reference.service.impl.PayPaymentServiceRemote;
import com.lvmama.tnt.reference.service.impl.TntBizPayServiceRemote;
import com.lvmama.tnt.result.TradePayResult;
import com.lvmama.tnt.util.SerialUtil;
import com.lvmama.tnt.vo.pay.TntBizPayTradeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author songjunbao
 * @createdate 2018/2/26
 */
@Service
public class ExportCodeOrderPaymentServiceImpl implements ExportCodeOrderPaymentService {
    private static Logger logger = LoggerFactory.getLogger(ExportCodeOrderPaymentServiceImpl.class);

    @Autowired
    private TntBizPayServiceRemote tntBizPayServiceRemote;

    @Autowired
    private PayPaymentServiceRemote paymentServiceRemote;

    @Override
    public OrderPaymentResponse pay(OrderPaymentRequest request)  {
        logger.info("-----------------pay start , request:"+request);
        OrderPaymentResponse orderPaymentResponse =  new OrderPaymentResponse();

        TntBizPayTradeVO tntBizPayTradeVO = new TntBizPayTradeVO();
        tntBizPayTradeVO.setAccountType(FinAccountTypeEnum.valueOf(request.getAccountType()));
        //不校验支付密码
        tntBizPayTradeVO.setCheckPassword(false);
        tntBizPayTradeVO.setOrderAmount(request.getOrderAmount());
        tntBizPayTradeVO.setAmount(request.getOrderAmount());
        tntBizPayTradeVO.setOrderId(request.getOrderId());
        tntBizPayTradeVO.setUserId(request.getUserId());
        tntBizPayTradeVO.setTradeType(TradeTypeEnum.PAY);
        //交易流水号
        String  tradeNo = SerialUtil.serialGenerator();
        tntBizPayTradeVO.setTradeNo(tradeNo);

        try {
            TradePayResult payResult = tntBizPayServiceRemote.pay(tntBizPayTradeVO);
            if (!payResult.isSuccess()){
                logger.info(">>>pay fail, payResult:"+ payResult.getMessage());
                orderPaymentResponse.setCode(payResult.getCode());
                orderPaymentResponse.setMessage(payResult.getMessage());
                orderPaymentResponse.setStatus(payResult.getStatus());
                return orderPaymentResponse;
            }
        } catch (Exception e) {
            logger.error(">>>pay error, payResult", e);
            orderPaymentResponse.setMessage(e.getMessage());
            orderPaymentResponse.setStatus(Status.FAIL);
            return orderPaymentResponse;

        }


        Long paymentId = initPayPayment(request.getAccountType(), request.getOrderId(), tradeNo, request.getOrderAmount());
        //给主站发送支付成功消息(此处换成lvmama_boot_mq 提供的方式)       mq需要考证
        JmsUtils.sendTopicMessage(MessageFactory.newPaymentSuccessCallMessage(paymentId), ExportCodeConstant.activeMqResource);

        orderPaymentResponse.setStatus(Status.SUCCESS);
        orderPaymentResponse.setPaymentId(paymentId);
        orderPaymentResponse.setTradeNo(tradeNo);
        logger.info("-----------------pay end ");
        return orderPaymentResponse;
    }


    private  Long initPayPayment(String accountType,Long orderId,String tradeNo, Long amount){
        PayPayment payPayment = new PayPayment();
        String paymentGateway = null;//支付网关
        if(FinAccountTypeEnum.CASH.name().equals(accountType)){
            paymentGateway = Constant.PAYMENT_GATEWAY_DIST_MANUAL.DISTRIBUTOR_B2B.name();
        }else if(FinAccountTypeEnum.CREDIT.name().equals(accountType)){
            paymentGateway = Constant.PAYMENT_GATEWAY_DIST_MANUAL.CREDIT_B2B.name();
        }

        payPayment.setObjectId(orderId);
        payPayment.setSerial(payPayment.geneSerialNo());
        Date clllbackTime = new Date();
        payPayment.setCallbackInfo("分销导码支付");
        payPayment.setGatewayTradeNo(tradeNo);
        payPayment.setPaymentTradeNo(com.lvmama.comm.utils.SerialUtil.generate24ByteSerialAttaObjectId(orderId));
        payPayment.setCallbackTime(clllbackTime);
        payPayment.setCreateTime(clllbackTime);
        payPayment.setPaymentGateway(paymentGateway);
        payPayment.setAmount(amount);
        payPayment.setOperator(null);
        payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.getCode());
        //分销商b2b支付 还是订单支付
        payPayment.setObjectType(Constant.OBJECT_TYPE.DISTRIBUTION_B2B.name());
        payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
        payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());

        Long paymentId =paymentServiceRemote.savePayment(payPayment);
        return paymentId;
    }
}
