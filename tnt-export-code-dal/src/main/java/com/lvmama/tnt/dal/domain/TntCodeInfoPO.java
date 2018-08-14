package com.lvmama.tnt.dal.domain;

/**
 * 码信息
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public class TntCodeInfoPO extends TntCodeBasePO {
    private static final long serialVersionUID = 8254321560438173648L;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 驴妈妈订单号
     */
    private Long orderId;

    /**
     * 子订单号
     */
    private Long orderItemId;

    /**
     * 码id
     */
    private Long passCodeId;

    /**
     * 二维码地址
     */
    private String codeUrl;

    /**
     * 辅助码
     */
    private String addCode;

    /**
     * PDF地址
     */
    private String pdf;

    /**
     * 使用状态
     */
    private String useStatus;

    /**
     * 文件Id
     */
    private Long fileId;


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getAddCode() {
        return addCode;
    }

    public void setAddCode(String addCode) {
        this.addCode = addCode;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getPassCodeId() {
        return passCodeId;
    }

    public void setPassCodeId(Long passCodeId) {
        this.passCodeId = passCodeId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
