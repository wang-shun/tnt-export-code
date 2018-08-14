package com.lvmama.tnt.common.domain;



import com.lvmama.tnt.common.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class OrderInfoDTO implements Serializable{

    private static final long serialVersionUID = -6734441872747066750L;
    private String batchNo;
    private Long tntOrderId;
    private Long orderId;
    private Long categoryId;
    private Long productId;
    private String productName;
    private Long goodsId;
    private String goodsName;
    private Long orderAmount;
    private Date createTime;
    private String paymentStatus;
    private String orderStatus;
    private String contactName;
    private String contactMoblie;
    private String paymentTarget;
    private String paymentType;
    private String passCodeStatus;
    private String quantity;
    private String performStatus;
    private Date visitTime;
    private Date lastCancelTime; //最晚取消时间
    private String isSuiShiTui;//是否可以随时退

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getLastCancelTime() {
        return lastCancelTime;
    }

    public void setLastCancelTime(Date lastCancelTime) {
        this.lastCancelTime = lastCancelTime;
    }

    public String getIsSuiShiTui() {
        return isSuiShiTui;
    }

    public void setIsSuiShiTui(String isSuiShiTui) {
        this.isSuiShiTui = isSuiShiTui;
    }

    public String getPerformStatus() {
        return performStatus;
    }

    public void setPerformStatus(String performStatus) {
        this.performStatus = performStatus;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getTntOrderId() {
        return tntOrderId;
    }

    public void setTntOrderId(Long tntOrderId) {
        this.tntOrderId = tntOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMoblie() {
        return contactMoblie;
    }

    public void setContactMoblie(String contactMoblie) {
        this.contactMoblie = contactMoblie;
    }

    public String getPaymentTarget() {
        return paymentTarget;
    }

    public void setPaymentTarget(String paymentTarget) {
        this.paymentTarget = paymentTarget;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPassCodeStatus() {
        return passCodeStatus;
    }

    public void setPassCodeStatus(String passCodeStatus) {
        this.passCodeStatus = passCodeStatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getOrderAmountYuan() {
        if (orderAmount == null || orderAmount <= 0L) {
            return 0L;
        } else {
            return PriceUtil.convertToYuan(orderAmount);
        }
    }
}
