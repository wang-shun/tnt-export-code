package com.lvmama.tnt.reference.service;

import java.util.List;

/**
 * 渠道服务
 *
 * @author songjunbao
 * @createdate 2018/1/30
 */
public interface IChannelRefService {

    /**
     * 发送短信(余额不足，导码批次任务完成)
     *
     * @param content
     * @param phoneNos
     */
    void sendSMSMessage(String content, List<String> phoneNos);

    void sendEmailMessage(String theme, String content, List<String> emailAddressList);

}
