package com.lvmama.tnt.export.code.api.domain.vo;

import java.io.Serializable;

/**
 *
 */
public class DistributorSetupVO implements Serializable {
    private static final long serialVersionUID = -877780146612861176L;

    private Long tntUserSetID;
    private Long goodsId;
    private String goodsName;
    private Long productId;
    private Long tntProductId;
    private String productName;

    private String companyName;
    private Long userId;
    private String userName;
    private String operator;
    private String codeType;
    private Integer maxNum;
    private String isHoard;

    private Integer pageNo;
    private Integer pageSize;

    public String getIsHoard() {
        return isHoard;
    }

    public void setIsHoard(String isHoard) {
        this.isHoard = isHoard;
    }

    public Long getTntUserSetID() {
        return tntUserSetID;
    }

    public void setTntUserSetID(Long tntUserSetID) {
        this.tntUserSetID = tntUserSetID;
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

    public Integer getPageNo() {
        return pageNo == null ? 1 : pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize == null ? 5000 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }
}
