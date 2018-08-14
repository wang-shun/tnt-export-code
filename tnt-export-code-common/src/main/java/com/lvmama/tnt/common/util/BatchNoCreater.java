package com.lvmama.tnt.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 批次号生成器
 *
 * @author songjunbao
 * @createdate 2018/1/30
 */
public class BatchNoCreater {

    public static final int SERIAL_RANDOM_LENGTH = 6;
    public static final String NUMBER = "0123456789";
    public static final String DATE_FMT = "yyyyMMdd";

    private BatchNoCreater() {
    }

    /**
     * 导码订单批次号
     * DM20180305+6位随机
     * @return
     */
    public static synchronized String batchNoGenerator() {
        Random random = new Random();
        //导码前缀
        StringBuilder sb = new StringBuilder("DM");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
        sb.append(sdf.format(dt));

        for(int i = 0; i < SERIAL_RANDOM_LENGTH; ++i) {
            sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        }

        return sb.toString();
    }
}
