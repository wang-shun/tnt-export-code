package com.lvmama.tnt.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lvmama.channel.api.email.service.ChannelEmailService;
import com.lvmama.channel.api.email.vo.ChannelEmailBatchVo;
import com.lvmama.channel.api.sms.service.ChannelSmsService;
import com.lvmama.channel.api.sms.vo.ChannelSmsVo;
import com.lvmama.channel.common.constant.CHANNEL_PROVIDER;
import com.lvmama.channel.common.constant.CHANNEL_TASK_DATA_MODE;
import com.lvmama.channel.common.constant.CHANNEL_TASK_TYPE;
import com.lvmama.tnt.reference.service.IChannelRefService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songjunbao
 * @createdate 2018/1/31
 */
@Service("channelRefServiceImpl")
public class ChannelRefServiceImpl implements IChannelRefService {
    private static Logger logger = LoggerFactory.getLogger(ChannelRefServiceImpl.class);

    @Reference
    private ChannelSmsService channelSmsService;

    @Reference
    private ChannelEmailService channelEmailService;

    @Override
    public void sendSMSMessage(String content,  List<String> phoneNos) {
        if (CollectionUtils.isEmpty(phoneNos)){
            return;
        }

        try{
            for (String phoneNo : phoneNos){
                ChannelSmsVo smsVo = new ChannelSmsVo();
                smsVo.setContent(content);
                smsVo.setMobile(phoneNo);
                channelSmsService.sendSms(smsVo, CHANNEL_TASK_TYPE.BUSINESS, CHANNEL_TASK_DATA_MODE.ONE_TO_ONE,"tnt_export_code",3l);
            }

        } catch (Exception e){
            logger.error("sms send error", e);
            //TODO
        }

    }


    @Override
    public void sendEmailMessage(String theme, String content, List<String> emailAddressList) {
        ChannelEmailBatchVo channelEmailBatchVo = new ChannelEmailBatchVo();
        channelEmailBatchVo.setToAddressList(emailAddressList);
        //主题
        channelEmailBatchVo.setSubject(theme);
        //内容
        channelEmailBatchVo.setContentText(content);
        CHANNEL_PROVIDER provider = CHANNEL_PROVIDER.PROVIDER_LVMAMA_EMAIL;
        CHANNEL_TASK_TYPE task_type = CHANNEL_TASK_TYPE.BUSINESS;
        try {
            channelEmailService.sendEmailBatch(channelEmailBatchVo, provider, task_type, channelEmailBatchVo.getContentText(), channelEmailBatchVo.getSubject());
        }catch (Exception e){
            //TODO
        }


    }
}
