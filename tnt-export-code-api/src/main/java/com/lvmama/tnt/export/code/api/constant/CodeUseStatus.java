package com.lvmama.tnt.export.code.api.constant;

/**
 * 二维码使用状态
 *
 * 参考 lvmama_comm里的 Constant.PASSCODE_USE_STATUS
 * @author songjunbao
 * @createdate 2018/1/28
 */
public enum CodeUseStatus {
    NOUSE("未履行"), USE("已履行"), DESTROYED("已废除");

    private String cnName;
    private CodeUseStatus(String cnName){
        this.cnName = cnName;
    }

    public String getCnName(){
        return this.cnName;
    }


}
