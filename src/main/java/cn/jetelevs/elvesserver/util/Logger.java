package cn.jetelevs.elvesserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志记录器
 * @Author 精灵
 * @version 1.0
 * @since 1.0
 * @create 2021-04-04-16
 */
public class Logger {
    private Logger(){

    }

    /**
     * 日志记录器
     * */
    public static void log(String msg){
        System.out.println("[INFO]" + DateUtil.getCurrentTime() + "  " + msg);
    }
}
