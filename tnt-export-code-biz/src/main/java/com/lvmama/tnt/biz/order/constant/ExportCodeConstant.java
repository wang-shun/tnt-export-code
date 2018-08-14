package com.lvmama.tnt.biz.order.constant;

/**
 * 分销导码涉及常量
 *
 * @author songjunbao
 * @createdate 2018/2/6
 */
public interface ExportCodeConstant {

    String LVMAMA_SERVICE_TEL = "400-6040-616";

    String DEFAULT_IP = "192.168.0.1";

    /**
     * 默认下单商品个数
     */
    int DEFAULT_QUANTITY = 1;


    String[] NOTICE_EMAIL_ADDRESS = {"lijian@lvmama.com", "songjunbao@lvmama.com"};

    String STOP_FAIL_MESSAGE = "余额不足，并且超过30分钟未及时充值，系统自动终止";

    /**
     * 短信模板占位符
     */
    enum REPLACE_FLAG{
        BATCH_NO("导码批次号", "${batchNo}"),
        DATE_START("开始时间", "${dateStart}"),
        DATE_END("截止时间",   "${dateEnd}"),
        BALANCE("余额",        "${balance}"),
        VISIT_DATE("游玩日期", "${visitDate}" ),
        GOODS_ID("商品id",     "${goodsId}"),
        EXCEPTION("异常信息",  "${exception}");
        private String mean;
        private String separator;
        private REPLACE_FLAG(String mean, String separator){
            this.mean = mean;
            this.separator = separator;

        }

        public String separator(){
            return this.separator;
        }

        public String mean(){
            return this.mean;
        }
    }

    /**
     * 短信内容，邮件内容模板
     */
    enum NOTICE_CONTENT_TEMPLATE{
        FINISHED_SMS_TEMPLATE("批次任务完成短信模板",
                "【驴妈妈】尊敬的商家，您的导码任务【"+REPLACE_FLAG.BATCH_NO.separator()+"】已完成，请登录分销平台查看，谢谢!"),

        TERMINATE_SMS_TEMPLATE("批次终止短信模板",
                "【驴妈妈】尊敬的商家，您的导码任务【"+REPLACE_FLAG.BATCH_NO.separator()+"】发生异常已终止，请登录分销平台查看原因并处理，谢谢!"),

        STOP_SMS_TEMPLATE("批次暂停短信模板","【驴妈妈】尊敬的商家，您的导码任务【"+REPLACE_FLAG.BATCH_NO.separator()+
                "】，由于预存款账户余额不足暂停，暂停时间："+REPLACE_FLAG.DATE_START.separator()+
                "，请在"+REPLACE_FLAG.DATE_END.separator()+"之前，充值保持余额充足（余额需≥ "+REPLACE_FLAG.BALANCE.separator()+"元）"),

        EXCEPTION_EMAIL_TEMPLATE("批次校验异常邮件内容模板","批次数据校验异常：导码批次号："+REPLACE_FLAG.BATCH_NO.separator() +
                "，商品ID为:" + REPLACE_FLAG.GOODS_ID.separator() +
                "，出行时间是" + REPLACE_FLAG.VISIT_DATE.separator() +
                "，请及时排查业务数据和接口问题，尽快恢复服务！返回异常原因: " + REPLACE_FLAG.EXCEPTION.separator()),

        BIZ_EXCEPTION_THEME("批次校验异常邮件主题模板", "分销导码-批次数据校验异常"),
        BIZ_RELEASE_FAIL_THEME("囤码释放库存失败主题模板", "囤码释放库存失败"),

        STOP_FAIL_SMS("批次暂停超时终止模板","【驴妈妈】尊敬的商家，您的导码任务【"+REPLACE_FLAG.BATCH_NO.separator()+"】"+STOP_FAIL_MESSAGE);

        private String mean;
        private String content;
        private NOTICE_CONTENT_TEMPLATE(String mean,String content){
            this.mean = mean;
            this.content = content;

        }
        public String content(){
            return this.content;
        }

        public String mean(){
            return this.mean;
        }

    }



     String[] activeMqResource = new String[] { "ActiveMQ.RESOURCE.job", "ActiveMQ.RESOURCE.back","ActiveMQ.RESOURCE.tntMessage",
            "ActiveMQ.RESOURCE.payment"/*,"ActiveMQ.RESOURCE.client_api"*/};


}
