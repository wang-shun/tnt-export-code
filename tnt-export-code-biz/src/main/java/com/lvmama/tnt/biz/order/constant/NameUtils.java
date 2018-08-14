package com.lvmama.tnt.biz.order.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 姓名相关服务:
 * 随机生成游玩人姓名
 * @author songjunbao
 * @createdate 2018/3/20
 */
public class NameUtils {
    private NameUtils(){}

    public static String getRandomName(){
        String name = createFrist()+createGBK()+createGBK();
        return name ;
    }
    private static String createFrist(){
            String [] firstNameArray = {
                    "李","张","宋",
                    "孙","周","高",
                    "朱","郑","郝",
                    "曹","韩","汤",
                    "姜","杨","程",
                    "吕","冯","陈",
                    "马","赵","祖", "袁"};
            List<String> list = Arrays.asList(firstNameArray);
            int n = new Random().nextInt(21);
            return list.get(n);
    }

    private static String createGBK(){
        try {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            str = new String(b, "GBk");//转成中文
            return str;
        } catch (Exception e) {
            return"";
        }
    }

}
