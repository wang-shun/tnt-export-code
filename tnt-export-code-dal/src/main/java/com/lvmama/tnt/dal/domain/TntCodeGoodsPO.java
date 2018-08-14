package com.lvmama.tnt.dal.domain;


/**
 *
 */
public class TntCodeGoodsPO extends TntCodeBasePO{
    private Long tntProductId;
    private Long goodsId;
    private String goodsName;
    private Long productId;
    private Long categoryId;
    private String productName;
    private String isExport;

    public Long getTntProductId() {
        return tntProductId;
    }

    public void setTntProductId(Long tntProductId) {
        this.tntProductId = tntProductId;
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

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
