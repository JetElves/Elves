package cn.jetelevs.elvesserver.core;

import cn.jetelevs.elvesserver.util.Logger;


import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * 处理客户端请求
 *
 * @Author 精灵
 * @Version 1.0
 * @Since 1.0
 * @create 2021-04-04-17
 */
public class HandleRequest implements Runnable {
    private Socket socket;

    public HandleRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bf = null;
        PrintWriter pw = null;
        Logger.log(Thread.currentThread().getName() + " 接受到消息,正在打印 ");
        //接受消息
        try {
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            String temp = bf.readLine();
            String URI = temp.split(" ")[1];
            if (URI.endsWith(".html")) {
                responseStaticPage(URI, pw);
            } else {
                String servletPath = URI;
                if (servletPath.contains("?")) {
                    servletPath = servletPath.split("[?]")[0];
                }
                //获取应用名称
                String webAppName = URI.split("/")[1];
                Map<String, Map<String, String>> servletMaps = WebParser.getServletMaps();
                //这个map的key是url value是ServletClass
                Map<String, String> servletMap = servletMaps.get(webAppName);
                //获取url
                String urlPatten = URI.substring(1 + webAppName.length());
                //获取servletName
                Class<?> aClass = Class.forName(servletMap.get(urlPatten));
                Object o = aClass.newInstance();
                //让APP开发人员的业务类实现Servlet方法,让他们重写service方法,我们在这里调用
                Servlet servlet = (Servlet) o;
                servlet.service();

            }
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void responseStaticPage(String uri, PrintWriter pw) {
        StringBuffer htmlPath = new StringBuffer("src/main/java/cn/jetelevs/elvesserver/" + uri.substring(1));
        String location = new String(htmlPath);
        BufferedReader br = null;
        StringBuffer html = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(location));
            html.append("HTTP/1.1 200 OK\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            String temp = null;
            while ((temp = br.readLine()) != null) {
                html.append(temp);
            }
        } catch (FileNotFoundException e) {
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<title> 404-NotFound </title>");
            html.append("<meta content='text/html;charset=utf-8'/>");
            html.append("</head>");
            html.append("<body>");
            html.append("<center><h1> 404 NOT FOUND </h1></center>");
            html.append("</body>");
            html.append("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.print(html);

    }
}

