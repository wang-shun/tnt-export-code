package com.lvmama.tnt.biz.channel;

import java.util.List;

/**
 * 通知请求
 *
 * @author songjunbao
 * @createdate 2018/3/20
 */
public class NoticeRequest {

    /**
     * 主题
     */
    private String theme;

    /**
     * 内容
     */
    private String content;

    /**
     * 地址集合， 电话号码或者邮箱地址
     */
    private List<String> addressList;

    /**
     * 渠道类型： 短信，邮件
     */
    private NoticeChannel channel;


    public String getContent() {
        return content;
    }

    public List<String> getAddressList() {
        return addressList;
    }


    public NoticeChannel getChannel() {
        return channel;
    }


    public String getTheme() {
        return theme;
    }

    public static NoticeRequest.Builder newBuilder(){
        return new NoticeRequest.Builder();
    }

    public static class Builder extends NoticeRequest{

        public Builder theme(String theme){
            super.theme = theme;
            return this;
        }

        public Builder content(String content){
            super.content = content;
            return this;
        }

        public Builder channel(NoticeChannel channel){
            super.channel = channel;
            return this;
        }

        public Builder addressList(List<String> addressList){
            super.addressList = addressList;
            return this;
        }

        public NoticeRequest build(){
            return this;
        }

    }
}
