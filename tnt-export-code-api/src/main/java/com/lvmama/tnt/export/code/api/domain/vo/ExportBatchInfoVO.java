package com.lvmama.tnt.export.code.api.domain.vo;

import com.lvmama.tnt.common.util.PriceUtil;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class ExportBatchInfoVO implements Serializable {
    private static final long serialVersionUID = 8754528409837479763L;

    private Integer pageNo;
    private Integer pageSize;
    /**
     * 批次号
     */
    private String batchNo;
    private Long productId;
    private String productName;
    private Long goodsId;
    private String goodsName;
    private String goodsNameTitle;

    /**
     * 游玩日期
     */
    private Date visitTime;

    /**
     * 分销结算价
     */
    private Long price;
    private float priceYuan;
    /** 批次所有订单总金额 */
    private Long totalOrderAmount;
    private float totalOrderAmountYuan;

    /**
     * 导码份数
     */
    private Integer codeNum;
    /**
     * 导码方式
     */
    private String codeType;
    /**
     * 支付方式
     */
    private String paymentType;
    private String payPassword;

    private Long merchantId;
    private String companyName;
    private Long userId;
    private String userName;

    /**
     * 批次状态
     */
    private String batchStatus;
    private String statusDesc;
    private String statusTitle;


    /**
     * 订单成功数
     */
    private Integer orderSuccessNum;

    /**
     * 申码成功数
     */
    private Integer codeSuccessNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 批次创建时间
     */
    private Date createTimeStart;
    private Date createTimeEnd;
    private String createTimeStartStr;
    private String createTimeEndStr;
    /**
     * 批次结束时间
     */
    private Date batchEndTime;

    //联系人信息
    private String contactName;
    private String enFirstName;
    private String enLastName;
    private String contactEmail;
    private String contactMobile;


    /**
     * 游玩人信息策略（固定 or 动态）
     */
    private String  travellerPolicy;
    /**
     * 人群
     */
    private String peopleType;

    private Long categoryId;

    private Long fileId;

    public String getCreateTimeStartStr() {
        return createTimeStartStr;
    }

    public void setCreateTimeStartStr(String createTimeStartStr) {
        this.createTimeStartStr = createTimeStartStr;
    }

    public String getCreateTimeEndStr() {
        return createTimeEndStr;
    }

    public void setCreateTimeEndStr(String createTimeEndStr) {
        this.createTimeEndStr = createTimeEndStr;
    }

    public String getGoodsNameTitle() {
        return goodsNameTitle;
    }

    public void setGoodsNameTitle(String goodsNameTitle) {
        this.goodsNameTitle = goodsNameTitle;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(Long totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getEnLastName() {
        return enLastName;
    }

    public void setEnLastName(String enLastName) {
        this.enLastName = enLastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(Integer codeNum) {
        this.codeNum = codeNum;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getBatchEndTime() {
        return batchEndTime;
    }

    public void setBatchEndTime(Date batchEndTime) {
        this.batchEndTime = batchEndTime;
    }

    public String getTravellerPolicy() {
        return travellerPolicy;
    }

    public void setTravellerPolicy(String travellerPolicy) {
        this.travellerPolicy = travellerPolicy;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "ExportBatchInfoVO{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", batchNo='" + batchNo + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", visitTime=" + visitTime +
                ", price=" + price +
                ", totalOrderAmount=" + totalOrderAmount +
                ", codeNum=" + codeNum +
                ", codeType='" + codeType + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", merchantId=" + merchantId +
                ", companyName='" + companyName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", batchStatus='" + batchStatus + '\'' +
                ", orderSuccessNum=" + orderSuccessNum +
                ", codeSuccessNum=" + codeSuccessNum +
                ", remark='" + remark + '\'' +
                ", createTimeStart=" + createTimeStart +
                ", createTimeEnd=" + createTimeEnd +
                ", batchEndTime=" + batchEndTime +
                ", contactName='" + contactName + '\'' +
                ", enFirstName='" + enFirstName + '\'' +
                ", enLastName='" + enLastName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                '}';
    }

    public void setPriceYuan() {
        this.priceYuan = getPriceYuan();
    }

    public void setTotalOrderAmountYuan() {
        this.totalOrderAmountYuan = getTotalOrderAmountYuan();
    }

    public float getPriceYuan() {
        if (price == null || price <= 0L) {
            return 0L;
        } else {
            return PriceUtil.convertToYuan(price);
        }
    }

    public float getTotalOrderAmountYuan() {
        if (totalOrderAmount == null || totalOrderAmount <= 0L) {
            return 0L;
        } else {
            return PriceUtil.convertToYuan(totalOrderAmount);
        }
    }
}
