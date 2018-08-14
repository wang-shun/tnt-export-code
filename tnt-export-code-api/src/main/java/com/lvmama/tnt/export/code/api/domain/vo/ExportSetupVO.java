package com.lvmama.tnt.export.code.api.domain.vo;

import com.lvmama.tnt.common.domain.TntMerchantDTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class ExportSetupVO implements Serializable {
    private static final long serialVersionUID = -3099232091802143713L;

    private Long goodsId;
    private Long productId;
    private String isHoard;
    private String policy;
    private String codeType;
    private Integer maxNum;

    private DistributorSetupVO distributorSetupVO;
    private List<DistributorSetupVO> distributorSetupVOS;
    List<TntMerchantDTO> tntMerchantDTOS;
    private Long totalCount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<TntMerchantDTO> getTntMerchantDTOS() {
        return tntMerchantDTOS;
    }

    public void setTntMerchantDTOS(List<TntMerchantDTO> tntMerchantDTOS) {
        this.tntMerchantDTOS = tntMerchantDTOS;
    }

    public DistributorSetupVO getDistributorSetupVO() {
        return distributorSetupVO;
    }

    public void setDistributorSetupVO(DistributorSetupVO distributorSetupVO) {
        this.distributorSetupVO = distributorSetupVO;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

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

    public List<DistributorSetupVO> getDistributorSetupVOS() {
        return distributorSetupVOS;
    }

    public void setDistributorSetupVOS(List<DistributorSetupVO> distributorSetupVOS) {
        this.distributorSetupVOS = distributorSetupVOS;
    }
}
