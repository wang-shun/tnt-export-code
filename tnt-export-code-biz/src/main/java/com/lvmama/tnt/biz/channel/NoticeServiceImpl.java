package com.lvmama.tnt.biz.channel;

import com.lvmama.tnt.reference.service.IChannelRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author songjunbao
 * @createdate 2018/3/20
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private IChannelRefService channelRefService;


    @Override
    public void notice(NoticeRequest noticeRequest) {
        if (NoticeChannel.SMS.equals(noticeRequest.getChannel())){
            channelRefService.sendSMSMessage(noticeRequest.getContent(), noticeRequest.getAddressList());
        } else if (NoticeChannel.EMAIL.equals(noticeRequest.getChannel())){
            channelRefService.sendEmailMessage(noticeRequest.getTheme(), noticeRequest.getContent(), noticeRequest.getAddressList());
        } else {
            throw new IllegalArgumentException("exportcode  notice channel is not valid");
        }

    }
}
