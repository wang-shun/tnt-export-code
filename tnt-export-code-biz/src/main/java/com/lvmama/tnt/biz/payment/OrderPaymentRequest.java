package com.lvmama.tnt.biz.payment;

/**
 * 导码订单支付请求
 *
 * @author songjunbao
 * @createdate 2018/2/26
 */
public class OrderPaymentRequest {
    private Long orderId;

    /**
     * 结算方式
     */
    private String accountType;

    private  Long orderAmount;

    private Long userId;

    /**
     * 交易流水号
     */
    private String tradeNo;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderPaymentRequest{").append("orderId:").append(orderId)
                .append(",accountType:").append(accountType)
                .append(",orderAmount:").append(orderAmount)
                .append(",userId:").append(userId)
                .append(",tradeNo:").append(tradeNo)
                .append("}");
        return builder.toString();
    }
}
