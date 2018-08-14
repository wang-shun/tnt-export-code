package com.lvmama.tnt.biz.order.vo;

import java.util.Date;

/**
 * 订单请求参数
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public class OrderRequest {
    private Long productId;

    private Long userId;

    private String userName;

    private String contactName;

    /**
     * 联系人电话
     */
    private String contactMobile;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    private Long goodsId;

    /**
     * 导码方式
     */
    private String codeType;

    /**
     * 主站会员账号(过滤中填充)
     */
    private Long memberId;
    /**
     * (过滤中填充)
     */
    private String memberNo;
    private String batchNo;

    /**
     * 游玩日期
     */
    private Date visitTime;

    private int quantity;
    private int  adultQuantity;
    private int  childQuantity;

    //同一ip是否下单限制？
    private String userIp;


    /**
     * 客服电话
     */
    private String customerServiceTel;

    /**
     * 分销后缀
     */
    private String distributionSuffix;

    /**
     * 分销商名称
     */
    private String distributorName;

    /**
     * 分销商手机号码
     */
    private String distributorMobile;


    /**
     * 品类id：为了校验商品的有效性
     */
    private Long categoryId;

    /**
     * 渠道id
     */
    private String channelId;

    /**
     * 分销订单结算总金额
     */
    private Long orderSettleAmount;

    /**
     * 分销订单销售总金额
     */
    private Long orderSellAmount;

    /**
     * 订单优惠金额
     */
    private Long discountOrderAmount;

    /**
     * 驴妈妈订单金额
     */
    private Long lvmamaOrderAmount;

    /**
     * 批次价格
     */
    private Long batchPrice;

    private String enLastName;
    private String enFirstName;
    /**
     * 退款价格类型
     */
    private String orderRefundType;

    /**
     * 人群
     */
    private String peopleType;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAdultQuantity() {
        return adultQuantity;
    }

    public void setAdultQuantity(int adultQuantity) {
        this.adultQuantity = adultQuantity;
    }

    public int getChildQuantity() {
        return childQuantity;
    }

    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getCustomerServiceTel() {
        return customerServiceTel;
    }

    public void setCustomerServiceTel(String customerServiceTel) {
        this.customerServiceTel = customerServiceTel;
    }

    public String getDistributionSuffix() {
        return distributionSuffix;
    }

    public void setDistributionSuffix(String distributionSuffix) {
        this.distributionSuffix = distributionSuffix;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Long getOrderSettleAmount() {
        return orderSettleAmount;
    }

    public void setOrderSettleAmount(Long orderSettleAmount) {
        this.orderSettleAmount = orderSettleAmount;
    }

    public Long getOrderSellAmount() {
        return orderSellAmount;
    }

    public void setOrderSellAmount(Long orderSellAmount) {
        this.orderSellAmount = orderSellAmount;
    }

    public Long getBatchPrice() {
        return batchPrice;
    }

    public void setBatchPrice(Long batchPrice) {
        this.batchPrice = batchPrice;
    }

    public Long getDiscountOrderAmount() {
        return discountOrderAmount;
    }

    public void setDiscountOrderAmount(Long discountOrderAmount) {
        this.discountOrderAmount = discountOrderAmount;
    }

    public Long getLvmamaOrderAmount() {
        return lvmamaOrderAmount;
    }

    public void setLvmamaOrderAmount(Long lvmamaOrderAmount) {
        this.lvmamaOrderAmount = lvmamaOrderAmount;
    }

    public String getDistributorMobile() {
        return distributorMobile;
    }

    public void setDistributorMobile(String distributorMobile) {
        this.distributorMobile = distributorMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public String getEnLastName() {
        return enLastName;
    }

    public void setEnLastName(String enLastName) {
        this.enLastName = enLastName;
    }

    public String getEnFirstName() {
        return enFirstName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getOrderRefundType() {
        return orderRefundType;
    }

    public void setOrderRefundType(String orderRefundType) {
        this.orderRefundType = orderRefundType;
    }
}
