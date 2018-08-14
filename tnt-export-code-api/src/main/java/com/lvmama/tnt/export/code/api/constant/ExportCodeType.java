package com.lvmama.tnt.export.code.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 导码方式
 *
 *   REALTIME 实时导码
 *   HOARD    码库取码
 * @author songjunbao
 * @createdate 2018/1/25
 */
public enum  ExportCodeType {
    REALTIME("实时导码"), HOARD("码库取码");

    private String value;

    private ExportCodeType(String value) {
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
        for (ExportCodeType t : values()) {
            map.put(t.name(), t.getValue());
        }
        return map;
    }

}
