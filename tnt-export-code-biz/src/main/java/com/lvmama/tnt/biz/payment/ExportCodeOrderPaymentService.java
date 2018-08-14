package com.lvmama.tnt.biz.payment;

/**
 * 导码订单支付服务
 *
 * @author songjunbao
 * @createdate 2018/2/26
 */
public interface ExportCodeOrderPaymentService {

    OrderPaymentResponse pay(OrderPaymentRequest request);
}
