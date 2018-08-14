package com.lvmama.tnt.export.code.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 导码分销商设置策略
 *
 * ALL    全部分销商可导
 * ALLNOT 全部分销商不可导
 * USER   部分分销商可导
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public enum CommonPolicy {
    ALL("全部分销商可导"), ALLNOT("全部分销商不可导"), USER("部分分销商可导");
    private String value;

    private CommonPolicy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getCnName(String code) {
        for (CommonPolicy item : CommonPolicy.values()) {
            if (item.name().equals(code)) {
                return item.getValue();
            }
        }
        return code;
    }

    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (CommonPolicy t : values()) {
            map.put(t.name(), t.getValue());
        }
        return map;
    }
}
