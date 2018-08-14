package com.lvmama.tnt.export.code.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 批次状态
 *
 * WAITING    排队中
 * PROCESSING 进行中
 * STOP       暂停（余额不足）
 * OVER       完成
 * TERMINATE  终止
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public enum BatchStatus {
    WAITING("排队中"), PROCESSING("进行中"), STOP("暂停（余额不足）"),UNFINISHED("未完成"), FINISHED("完成"), TERMINATE("终止"), EXCEPTION("异常");

    private String value;

    private BatchStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getCnName(String code) {
        for (BatchStatus item : BatchStatus.values()) {
            if (item.name().equals(code)) {
                return item.getValue();
            }
        }
        return code;
    }

    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (BatchStatus t : values()) {
            map.put(t.name(), t.getValue());
        }
        return map;
    }
}
