package javax.servlet;

/**
 * 由自己定义的Servlet接口规范,该接口由服务器开发人员调用,由App开发人员重写
 * @Author 精灵
 * @Version 1.0
 * @Since 1.0
 * @create 2021-04-05-00
 */
public interface Servlet {

    void service();
}
