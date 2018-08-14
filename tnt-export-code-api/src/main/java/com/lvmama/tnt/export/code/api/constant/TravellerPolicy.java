package com.lvmama.tnt.export.code.api.constant;

/**
 * 游玩人信息生成策略
 *
 * @author songjunbao
 * @createdate 2018/1/25
 */
public enum TravellerPolicy {
    CONTACTER("使用联系人信息"), RANDOM("随机生成");

    private String mean;
    private TravellerPolicy(String mean){
        this.mean = mean;
    }


}
