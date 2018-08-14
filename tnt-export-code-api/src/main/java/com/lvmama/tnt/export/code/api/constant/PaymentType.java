package com.lvmama.tnt.export.code.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 导码批次支付方式
 *
 * CASH   预存款
 * CREDIT 授信
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public enum PaymentType {
    CASH("预存款支付"),
    CREDIT("授信支付"),
    ONLINE("在线支付"),
    POS("POS机支付"),
    DOWN("定金支付"),
    VIRTUAL("虚拟支付");

    private String value;

    private PaymentType(String value) {
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
        for (PaymentType t : values()) {
            map.put(t.name(), t.getValue());
        }
        return map;
    }
}
