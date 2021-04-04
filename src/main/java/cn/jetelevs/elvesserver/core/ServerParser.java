package cn.jetelevs.elvesserver.core;

import org.dom4j.*;
import org.dom4j.io.SAXReader;


/**
 * 解析 ServerConfig.xml 配置文件
 * @Author 精灵
 * @Version 1.0
 * @Since 1.0
 * @create 2021-04-04-17
 */
public class ServerParser {

    /**
     * 获取端口号
     * @return port
     * */
    public static int getPort() {
        int port = 8080;
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read("src/main/resources/ServerConfig.xml");
            Element portElement = (Element) document.selectSingleNode("//connector");
            port = Integer.parseInt(portElement.attributeValue("port"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return port;
    }
}
