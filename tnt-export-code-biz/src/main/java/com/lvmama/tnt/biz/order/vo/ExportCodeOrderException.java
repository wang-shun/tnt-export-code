package com.lvmama.tnt.biz.order.vo;

/**
 * 分销导码下单异常
 *
 * @author songjunbao
 * @createdate 2018/2/5
 */
public class ExportCodeOrderException extends RuntimeException{

    private String code;

    public ExportCodeOrderException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
