package com.lvmama.tnt.dal.domain;

/**
 *
 */
public class TntCodeUserSetJoinGoodsPO extends TntCodeUserSetPO {
    private static final long serialVersionUID = -297824499947418225L;

    private String goodsName;
    private Long productId;
    private Long tntProductId;
    private String productName;

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

    public Long getTntProductId() {
        return tntProductId;
    }

    public void setTntProductId(Long tntProductId) {
        this.tntProductId = tntProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
