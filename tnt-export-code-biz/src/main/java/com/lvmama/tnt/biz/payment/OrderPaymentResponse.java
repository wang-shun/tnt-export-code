package com.lvmama.tnt.biz.payment;

import com.lvmama.common.util.facade.AbstractResult;

/**
 * 订单支付返回结果
 *
 * @author songjunbao
 * @createdate 2018/2/26
 */
public class OrderPaymentResponse extends AbstractResult{
    private Long paymentId;

    /**
     * 交易流水号
     */
    private String tradeNo;
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
