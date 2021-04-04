package cn.jetelevs.elvesserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @Author 精灵
 * @Version 1.0
 * @Since 1.0
 * @create 2021-04-04-16
 */
public class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss SSS");
    private DateUtil(){

    }

    /**
     * 获取当前时间
     * @return :
     * String yyyy-MM-dd  HH:mm:ss SSS
     * */
    public static String getCurrentTime(){
        return dateFormat.format(new Date());
    }
}
