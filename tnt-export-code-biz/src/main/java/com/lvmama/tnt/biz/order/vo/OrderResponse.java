package com.lvmama.tnt.biz.order.vo;

import com.lvmama.common.util.facade.AbstractResult;
import com.lvmama.common.util.facade.Status;

/**
 * 订单创建返回结果
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public class OrderResponse extends AbstractResult{
    private Long orderId;
    private Long tntOrderId;
    private String batchNo;
    private Long productId;
    private Long goodsId;
    private Long userId;
    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 支付对象
     */
    private String paymentTarget;

    /**
     * 支付状态
     */
    private String paymentStatus;

    private Long oughtAmount;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTntOrderId() {
        return tntOrderId;
    }

    public void setTntOrderId(Long tntOrderId) {
        this.tntOrderId = tntOrderId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public OrderResponse(){
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOughtAmount() {
        return oughtAmount;
    }

    public OrderResponse buildOughtAmount(Long oughtAmount) {
        this.oughtAmount = oughtAmount;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public OrderResponse buildOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getPaymentTarget() {
        return paymentTarget;
    }

    public OrderResponse buildPaymentTarget(String paymentTarget) {
        this.paymentTarget = paymentTarget;
        return this;

    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public OrderResponse buildPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public OrderResponse(Long orderId, Status status, String code, String message){
        this.orderId = orderId;
        this.setStatus(status);
        this.setCode(code);
        this.setMessage(message);
    }

}
