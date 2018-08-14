package com.lvmama.tnt.dal.domain;

/**
 * 导码商品通用设置
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public class TntCodeCommonSetPO extends TntCodeBasePO {
    private static final long serialVersionUID = 8881688430450442527L;


    private Long goodsId;
    private String isHoard;
    /**
     * 通用设置策略
     */
    private String policy;

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

    public String getIsHoard() {
        return isHoard;
    }

    public void setIsHoard(String isHoard) {
        this.isHoard = isHoard;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
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
