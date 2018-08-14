package com.lvmama.tnt.biz.order.vo;

/**
 * 订单返回错误code
 * @author songjunbao
 * @createdate 2018/2/5
 */
public enum OrderErrorCode {

    MEMBER_ACCOUNT_NOT_FOUND("export-code-order-error-0001", "找不到会员信息"),

    TNT_GOODS_NOT_FOUND("export-code-order-error-0002", "查询分销商品错误"),

    TNT_GOODS_INVALID("export-code-order-error-0003", "分销商品无效不可售"),

    TNT_GOODS_BLACK_FOR_USER("export-code-order-error-0004", "分销商品对分销商不分销"),
    TNT_GOODS_NOT_EXPORT("export-code-order-error-0005", "分销商品不可导"),
    TNT_GOODS_NOT_EXPORT_FOR_USER("export-code-order-error-00011", "分销商品对分销商不可导"),

    TNT_TIME_PRICE_NO_SET("export-code-order-error-0006", "无可售的时间价格"),

    TNT_TIME_PRICE_NOT_MATCH("export-code-order-error-0007", "分销时间价格与批次不匹配"),
    SCENIC_ORDER_CREATE_ERROR("export-code-order-error-0008", "主站创建订单失败"),
    CREATE_ORDER_MUST_CRED("export-code-order-error-0009", "下单设置证件类型必填"),

    SCENIC_SUPPLY_PRICE_ERROR("export-code-order-error-0010", "供应商价格计算异常"),

    EXPORT_CODE_CREATE_ORDER_ERROR("export-code-order-error-0011", "导码下单异常");


    private String code;
    private String message;

    private OrderErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }


}
