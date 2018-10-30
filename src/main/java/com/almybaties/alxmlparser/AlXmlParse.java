package com.almybaties.alxmlparser;

import com.almybaties.entity.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class AlXmlParse {

    private static Logger logger =  LoggerFactory.getLogger(AlXmlParse.class);
    private Element root;  //Element 接口表示 XML 文档中的一个元素，根节点
    Configuration config = new Configuration();
    ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();

    public Configuration doParse(){
        // 创建DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //通过documentBuilder对象 的parser方法加载books。xml文件到当前项目下
            Document document = db.parse("C:\\Users\\Administrator\\Desktop\\Mybaties-resource\\AlMybaties\\src\\main\\resources\\mybaties-config.xml");
            //Root Element
            root = document.getDocumentElement();

            NodeList childlist = root.getChildNodes();
            if (childlist != null) {
                //获得所有节点的名称与值
                for (int i = 0; i < childlist.getLength(); i++) {
                    Node node = childlist.item(i);
//                    short nodeType = node.getNodeType();
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String key = node.getNodeName();
                        //取得节点的属性值
                        if("alenvironments".equals(key)){
                            String id = node.getAttributes().getNamedItem("id").getNodeValue();
                            config.setAlenvironmentsId(id);
                        }else if("almappers".equals(key)){


                        }



                        //获取aldataSource
                        NodeList childNodes = node.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node aldataSource_node = childNodes.item(j);
                            if (aldataSource_node.getNodeType() == Node.ELEMENT_NODE) {
                                String keys = aldataSource_node.getNodeName();
                                //注意，节点的属性也是它的子节点。它的节点类型也是Node.ELEMENT_NODE
                                //轮循子节点

                                //获取alproperty节点
                                NodeList alproperty = aldataSource_node.getChildNodes();
                                for(int k =0 ;k < alproperty.getLength();k++){
                                    Node alproperty_item = alproperty.item(k);
                                    if(alproperty_item.getNodeType() == Node.ELEMENT_NODE){
                                        NamedNodeMap attributes = alproperty_item.getAttributes();
                                        //获取name值
                                        String name = attributes.getNamedItem("name").getNodeValue();
                                        String value = attributes.getNamedItem("value").getNodeValue();
                                        //System.out.println("name: " + name + "---value: " + value);
                                        logger.info("name:",name,">>>>",value);
                                        map.put(name,value);
                                    }
                                }
                                config.setJdbcConnectInfo(map);
                            }
                        }
                      }
                   }
                }

            } catch(ParserConfigurationException e){
                e.printStackTrace();
            } catch(SAXException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
            return config;
    }

}
