package com.lvmama.tnt.test;

import org.apache.poi.ss.formula.functions.T;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author songjunbao
 * @createdate 2018/7/5
 */
public class Main {

    public static void main(String[] args) {

        //String tmp = System.getProperty("java.io.tmpdir");

//        System.out.println(tmp);

//        List<String> stringList = Arrays.asList("A","B","C","D","A","D","E","F","E");
//        System.out.println(removeDuplicates(stringList));
//

//        LocalDateTime now = of(new Date());
//        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        System.out.println("date start:" + getDayStart("2018-07-24"));
//        System.out.println("date end:" + getDayEnd(new Date()));


        System.out.println("isIpmatch:"+ isIpMatch("10.112.5.43", "10.112.5.*"));

//        System.out.println("isIp:"+ isIp("192.168.0.1"));
//            System.out.println("isHas:"+ isHas());

    }


    /**
     * 去重
     * @param list
     * @return
     */
   public static <T> List<T> removeDuplicates(List<T> list){

        return new ArrayList<>(new LinkedHashSet<>(list));

   }

   public static LocalDateTime of(Date date){
       return  LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
   }


   public static Date getDayStart(String dateStr){
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//       LocalDateTime dateTime = LocalDateTime.parse(dateStr);  会报异常，因为LocalDateTime解析格式化字符串时，字符串需要包含时分秒
       LocalDateTime dateTime = LocalDate.parse(dateStr, formatter).atTime(0, 0, 0); //时分秒
       return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
   }

    public static Date getDayEnd(Date date){
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isIpMatch(String source, String pattern){
        //192.168.*.1
        String regex = "([0-9]|([1-9]\\\\d)|(1\\\\d\\\\d)|(2([0-4]\\\\d|5[0-5])))";
        regex = pattern.replaceAll("\\*", regex);
        System.out.println("---------regex:" + regex);
        return source.matches(regex);

    }

    public static boolean isIp(String source){
        String ipRegex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]))(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]|\\*)){2}";
        System.out.println("---------regex:"+ipRegex);

        return source.matches(ipRegex);

    }

    public static boolean isIpOk(String source, String pattern){
        //192.168.*.1
        String regex = "192.168.0.*";
        System.out.println("---------regex:" + regex);
        return source.matches(regex);

    }

    public static boolean isHas(){
        String s = "10.112.5.*";
        return s.indexOf("*")>0;

    }

}
