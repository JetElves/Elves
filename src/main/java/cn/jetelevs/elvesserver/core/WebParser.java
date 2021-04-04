package cn.jetelevs.elvesserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author 精灵
 * @Version 1.0
 * @Since 1.0
 * @create 2021-04-04-23
 */
public class WebParser {
    private static Map<String,Map<String,String>> servletMaps = new HashMap<>();


    public static Map<String, Map<String, String>> getServletMaps() {
        return servletMaps;
    }

    /**
     * 解析服务器所有应用的名称
     * */
    public static void  parser(String[] webAppNames) throws DocumentException {
        for(String webAppName: webAppNames){
            Map<String, String> parser = parser(webAppName);
            servletMaps.put(webAppName,parser);
        }
    }

    /**
     * @param webAppName: 应用名称
     * @return servletMap<String, String>
     */
    private static Map<String, String> parser(String webAppName) throws DocumentException {
        //获取web.xml路径
        String path = webAppName + "/WEB-INF/web.xml";
        //创建解析器
        SAXReader saxReader = new SAXReader();
        //通过read方法将目标文件读入
        Document doc = saxReader.read(new File(path));
        //获取每个Servlet的值
        List<Element> servletNodes = doc.selectNodes("/web-app/servlet");
        Map<String, String> servletInfoMap = new HashMap<>();
        //便利元素
        for (Element element : servletNodes) {
            //获取servlet-name节点对象
            Element serName = (Element) element.selectSingleNode("servlet-name");
            //获取node节点对象元素的值
            String servletName = serName.getStringValue();
            Element serClass = (Element) element.selectSingleNode("servlet-class");
            String servletClass = serClass.getStringValue();
            //把他们放入map之中
            servletInfoMap.put(servletName,servletClass);
        }
        List<Element> servletMappingNodes = doc.selectNodes("/web-app/servlet-mapping");
        Map<String, String> servletMappingInfoMap = new HashMap<>();
        for (Element element : servletMappingNodes) {
            //获取servlet-name节点对象
            Element serName = (Element) element.selectSingleNode("servlet-name");
            //获取node节点对象元素的值
            String servletName = serName.getStringValue();
            Element serClass = (Element) element.selectSingleNode("url-pattern");
            String servletUrl = serClass.getStringValue();
            //把他们放入map之中
            servletMappingInfoMap.put(servletName,servletUrl);
        }
        //得到一个keySet来便利我们的集合
        Set<String> servletNames = servletInfoMap.keySet();
        //创建一个map将上两个Map的中key对应的value放入一个新map
        Map<String,String> servletMap = new HashMap<>();
        //开始遍历
        for (String servletName :
                servletNames) {//下面是url                         下面是className
            servletMap.put(servletMappingInfoMap.get(servletName),servletInfoMap.get(servletName));
        }
        return servletMap;
    }


}
