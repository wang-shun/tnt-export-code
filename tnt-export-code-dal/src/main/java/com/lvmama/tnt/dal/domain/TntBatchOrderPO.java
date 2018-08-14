package com.lvmama.tnt.dal.domain;

/**
 * 批次订单
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public class TntBatchOrderPO extends TntCodeBasePO {
    private static final long serialVersionUID = -5046639028279937393L;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 驴妈妈订单号
     */
    private Long orderId;

    /**
     * 子订单号
     */
    private Long orderItemId;

    /**
     * 分销订单号
     */
    private Long tntOrderId;

    private String paymentStatus;

    /**
     * 申码状态
     */
    private String passcodeApplyStatus;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

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

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPasscodeApplyStatus() {
        return passcodeApplyStatus;
    }

    public void setPasscodeApplyStatus(String passcodeApplyStatus) {
        this.passcodeApplyStatus = passcodeApplyStatus;
    }
}
