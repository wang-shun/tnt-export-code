package com.lvmama.tnt.biz.channel;

import com.lvmama.tnt.biz.order.constant.ExportCodeConstant;
import com.lvmama.tnt.common.util.PriceUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * 通知请求工厂
 *
 * @author songjunbao
 * @createdate 2018/4/8
 */
public class NoticeRequestFactory {

    private NoticeRequestFactory(){}


    /**
     * 当批次信息，下单校验异常的时，发邮件通知开发人员和产品经理
     * @param batchNo
     * @param goodsId
     * @param visitTime
     * @param errorMessage
     * @return
     */
    public static NoticeRequest newExceptionNoticeRequest(String batchNo, Long goodsId, Date visitTime, String errorMessage){
        //Email通知宋俊保和李健
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.EMAIL)
                .content(buildExceptionEmailContent(batchNo, goodsId, visitTime, errorMessage))
                .theme(ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.BIZ_EXCEPTION_THEME.content())
                .addressList(Arrays.asList(ExportCodeConstant.NOTICE_EMAIL_ADDRESS));
        return noticeRequest;
    }


    public static NoticeRequest newReleaseFailNoticeRequest(String batchNo){
        //Email通知宋俊保和李健
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.EMAIL)
                .content(buildReleaseFailContent(batchNo))
                .theme(ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.BIZ_RELEASE_FAIL_THEME.content())
                .addressList(Arrays.asList(ExportCodeConstant.NOTICE_EMAIL_ADDRESS));
        return noticeRequest;
    }

    private static  String buildReleaseFailContent(String batchNo){
        return "邮件内容是：【批次号："+batchNo+"】导码库存解锁异常，请及时排查处理。 技术这边在排查具体原因处理。";
    }


    private static String buildExceptionEmailContent(String batchNo, Long goodsId, Date visitDate, String exceptionMessage ){
        return ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.EXCEPTION_EMAIL_TEMPLATE.content()
                .replace(ExportCodeConstant.REPLACE_FLAG.BATCH_NO.separator(), batchNo)
                .replace(ExportCodeConstant.REPLACE_FLAG.GOODS_ID.separator(), goodsId + "")
                .replace(ExportCodeConstant.REPLACE_FLAG.VISIT_DATE.separator(), DateFormatUtils.format(visitDate, "yyyy-MM-dd"))
                .replace(ExportCodeConstant.REPLACE_FLAG.EXCEPTION.separator(), exceptionMessage);
    }


    /**
     * 批次完成时，发送短信通知分销商
     * @param batchNo
     * @param phoneNo
     * @return
     */
    public static NoticeRequest newFinishedNoticeRequest(String batchNo, String phoneNo){
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.SMS)
                .content(buildBatchFinishedSms(batchNo))
                .addressList(Arrays.asList(phoneNo));
        return noticeRequest;
    }


    /**
     * 创建批次完成短信内容
     * @param batchNo
     * @return
     */
    private static String buildBatchFinishedSms(String batchNo){
        return ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.FINISHED_SMS_TEMPLATE.content()
                .replace(ExportCodeConstant.REPLACE_FLAG.BATCH_NO.separator(), batchNo);
    }


    /**
     * 批次执行时，支付余额不足，通知分销商及时充钱
     * @param batchNo
     * @param needBalance
     * @param phoneNo
     * @return
     */
    public static NoticeRequest newStopNoticeRequest(String batchNo, Long needBalance,  String phoneNo){
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.SMS)
                .content(buildNoBalanceSms(batchNo, needBalance))
                .addressList(Arrays.asList(phoneNo));
        return noticeRequest;
    }

    private static String buildNoBalanceSms(String batchNo, Long needBalance){
        return ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.STOP_SMS_TEMPLATE.content()
                .replace(ExportCodeConstant.REPLACE_FLAG.BATCH_NO.separator(), batchNo)
                .replace(ExportCodeConstant.REPLACE_FLAG.DATE_START.separator(), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .replace(ExportCodeConstant.REPLACE_FLAG.DATE_END.separator(),DateFormatUtils.format(DateUtils.addMinutes(new Date(),30), "yyyy-MM-dd HH:mm:ss"))
                .replace(ExportCodeConstant.REPLACE_FLAG.BALANCE.separator(), String.valueOf(PriceUtil.convertToYuan(needBalance)));
    }


    /**
     * 批次终止时，发短信告知分销商
     * @param batchNo
     * @param phoneNo
     * @return
     */
    public static NoticeRequest newTerminateNoticeRequest(String batchNo, String phoneNo){
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.SMS)
                .content(buildBatchTerminateSms(batchNo))
                .addressList(Arrays.asList(phoneNo));
        return noticeRequest;
    }

    /**
     * 创建支付异常终止内容
     * @param batchNo
     * @return
     */
    private static String buildBatchTerminateSms(String batchNo){
        return ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.TERMINATE_SMS_TEMPLATE.content()
                .replace(ExportCodeConstant.REPLACE_FLAG.BATCH_NO.separator(), batchNo);
    }


    /**
     * 余额不足，暂停时间超过30分钟
     * @param batchNo
     * @param phoneNo
     * @return
     */
    public static NoticeRequest newStopFailNoticeRequest(String batchNo, String phoneNo){
        NoticeRequest noticeRequest = NoticeRequest.newBuilder()
                .channel(NoticeChannel.SMS)
                .content(buildStopFailSms(batchNo))
                .addressList(Arrays.asList(phoneNo));
        return noticeRequest;
    }

    /**
     * 余额不足，暂停超时消息
     * @param batchNo
     * @return
     */
    private static String buildStopFailSms(String batchNo){
        return ExportCodeConstant.NOTICE_CONTENT_TEMPLATE.STOP_FAIL_SMS.content()
                .replace(ExportCodeConstant.REPLACE_FLAG.BATCH_NO.separator(),batchNo);

    }
}
