package com.lvmama.tnt.dal.domain;

/**
 * 导码商品分销商设置
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public class TntCodeUserSetPO extends TntCodeBasePO {

    private static final long serialVersionUID = 5179126911247559052L;
    private Long goodsId;
    private String companyName;
    private Long userId;
    private String userName;
    private String operator;

    /**
     * 导码方式：实时导 | 码库取码
     */
    private String codeType;
    private Integer maxNum;

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
