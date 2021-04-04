package cn.jetelves;

import javax.servlet.Servlet;

/**
 * @Author 精灵
 * @Version
 * @Since
 * @create 2021-04-04-20
 */
public class LoginServlet implements Servlet {
    private void login(){
        System.out.println("登录中");
    }

    @Override
    public void service() {
        login();
    }
}
