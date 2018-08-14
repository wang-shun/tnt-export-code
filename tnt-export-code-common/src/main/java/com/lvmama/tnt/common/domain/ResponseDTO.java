package com.lvmama.tnt.common.domain;

import java.io.Serializable;

/**
 *
 */
public class ResponseDTO<T> implements Serializable{
    private boolean success;
    private String respCode;
    private String respMsg;
    private T result;

    public ResponseDTO() {
        this.success = true;
    }

    public ResponseDTO(boolean success, String respCode, String respMsg) {
        this.success = success;
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
