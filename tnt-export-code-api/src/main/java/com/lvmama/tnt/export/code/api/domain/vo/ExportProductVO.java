package com.lvmama.tnt.export.code.api.domain.vo;

import java.io.Serializable;

/**
 *
 */
public class ExportProductVO implements Serializable{
    private static final long serialVersionUID = 4202795996287999864L;

    private Long productId;
    private String productName;
    private Long goodsId;
    private String goodsName;
    private String goodsType;
    private String payTarget;
    private String status;
    private String valid;
    private String isExport;

    private Integer pageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPayTarget() {
        return payTarget;
    }

    public void setPayTarget(String payTarget) {
        this.payTarget = payTarget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    @Override
    public String toString() {
        return "ExportProductVO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", payTarget='" + payTarget + '\'' +
                ", status='" + status + '\'' +
                ", valid='" + valid + '\'' +
                ", isExport='" + isExport + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
