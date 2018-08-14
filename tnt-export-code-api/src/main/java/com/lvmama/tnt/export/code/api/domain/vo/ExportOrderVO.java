package com.lvmama.tnt.export.code.api.domain.vo;

import com.lvmama.tnt.common.domain.OrderInfoDTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class ExportOrderVO implements Serializable {
    private static final long serialVersionUID = 8475620643826100189L;
    private int pageNo;
    private int pageSize;
    private Long userId;
    private Long orderId;
    private String orderStatus;

    private Long productId;
    private String productName;
    private Long goodsId;
    private String goodsName;
    private String goodsNameTitle;
    private String batchNo;
    private Integer codeNum;
    private String batchStatus;
    private Integer orderSuccessNum;
    private Integer codeSuccessNum;
    private String remark;
    private String statusMessage;
    private String performStatus;

    List<OrderInfoDTO> batchOrderDTOS;

    public String getPerformStatus() {
        return performStatus;
    }

    public void setPerformStatus(String performStatus) {
        this.performStatus = performStatus;
    }

    public String getGoodsNameTitle() {
        return goodsNameTitle;
    }

    public void setGoodsNameTitle(String goodsNameTitle) {
        this.goodsNameTitle = goodsNameTitle;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(Integer codeNum) {
        this.codeNum = codeNum;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Integer getOrderSuccessNum() {
        return orderSuccessNum;
    }

    public void setOrderSuccessNum(Integer orderSuccessNum) {
        this.orderSuccessNum = orderSuccessNum;
    }

    public Integer getCodeSuccessNum() {
        return codeSuccessNum;
    }

    public void setCodeSuccessNum(Integer codeSuccessNum) {
        this.codeSuccessNum = codeSuccessNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderInfoDTO> getBatchOrderDTOS() {
        return batchOrderDTOS;
    }

    public void setBatchOrderDTOS(List<OrderInfoDTO> batchOrderDTOS) {
        this.batchOrderDTOS = batchOrderDTOS;
    }
}
