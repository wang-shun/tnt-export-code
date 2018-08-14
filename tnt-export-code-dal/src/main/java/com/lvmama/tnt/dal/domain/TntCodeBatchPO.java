package com.lvmama.tnt.dal.domain;

import java.util.Date;

/**
 * 导码批次PO
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public class TntCodeBatchPO extends TntCodeBasePO {
    private static final long serialVersionUID = 2290458629900550459L;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 游玩日期
     */
    private Date visitTime;

    /**
     * 分销结算价
     */
    private Long price;

    /**
     * 导码份数
     */
    private Integer codeNum;

    /**
     * 产品id
     */
    private Long productId;
    private Long goodsId;
    /**
     * 品类id
     */
    private Long categoryId;
    private Long merchantId;
    private String companyName;
    private Long userId;
    private String userName;
    private String contactName;
    private String enFirstName;
    private String enLastName;
    private String contactEmail;
    private String contactMobile;
    /**
     * 支付方式
     */
    private String paymentType;
    private String payPassword;
    /**
     * 批次状态
     */
    private String batchStatus;

    /**
     * 状态信息
     */
    private String statusMessage;

    /**
     * 导码方式
     */
    private String codeType;
    /**
     * 订单成功数
     */
    private Integer orderSuccessNum;

    /**
     * 申码成功数
     */
    private Integer codeSuccessNum;
    private String remark;



    /**
     * 游玩人信息策略（固定 or 动态）
     */
    private String  travellerPolicy;
    /**
     * 人群
     */
    private String peopleType;

    /**
     * 重试次数 （默认0）
     */
    private Integer retryCount;

    /**
     * 查询参数 endTime
     */
    private Date createTimeB;

    /**
     * 文件id
     */
    private Long fileId;

    public Date getCreateTimeB() {
        return createTimeB;
    }

    public void setCreateTimeB(Date createTimeB) {
        this.createTimeB = createTimeB;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
