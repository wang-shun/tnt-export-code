package com.lvmama.tnt.dal.domain;

/**
 *
 */
public class TntCodeBatchJoinCodeGoodsPO extends TntCodeBatchPO {
    private static final long serialVersionUID = -3334021309699760465L;

    private Long productId;
    private String productName;
    private String goodsName;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
