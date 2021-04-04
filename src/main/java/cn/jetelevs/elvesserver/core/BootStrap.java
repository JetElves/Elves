package cn.jetelevs.elvesserver.core;

import cn.jetelevs.elvesserver.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Elves服务器主入口
 *
 * @version 1.0
 * @Author 精灵
 * @create 2021-04-04-16
 * @since 1.0
 */
public class BootStrap {
    public static void main(String[] args) {
        //程序入口
        start();
    }
    /**
     * 主程序入口
     */
    private static void start() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            Logger.log("Elves服务器尝试开启");
            //获取当前时间
            long start = System.currentTimeMillis();
            //解析web.xml配置文件
            WebParser.parser(new String[]{"oa"});
            int port = ServerParser.getPort();
            Logger.log("端口号为: " + port);
            //绑定套接字的端口号
            serverSocket = new ServerSocket(port);
            long end = System.currentTimeMillis();
            Logger.log("Elves开启耗时: " + (end - start) + "ms");
            Logger.log("Elves服务器开启成功");
            //监听网络
            while(true){
                socket = serverSocket.accept();
                new Thread(new HandleRequest(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
