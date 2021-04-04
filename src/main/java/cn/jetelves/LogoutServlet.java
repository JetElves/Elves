package cn.jetelves;

import javax.servlet.Servlet;

/**
 * @Author 精灵
 * @Version
 * @Since
 * @create 2021-04-04-23
 */
public class LogoutServlet implements Servlet {
    public void logout(){
        System.out.println("退出成功");
    }

    @Override
    public void service() {
        logout();
    }
}
