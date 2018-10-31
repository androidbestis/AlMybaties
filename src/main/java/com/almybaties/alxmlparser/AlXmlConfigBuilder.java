package com.almybaties.alxmlparser;

import com.almybaties.entity.AlNode;
import com.almybaties.entity.Configuration;
import com.almybaties.entity.MapperEntity;
import com.almybaties.parsing.AlXPathParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * parse mybatis config
 */
public class AlXmlConfigBuilder {

    private boolean parsed;
    private final AlXPathParser alXPathParser;
    private String environment;

    private static Logger logger =  LoggerFactory.getLogger(AlXmlConfigBuilder.class);
    private Element root;  //Element 接口表示 XML 文档中的一个元素，根节点
    Configuration configuration = new Configuration();
    ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
    HashMap<String,MapperEntity> mapedmap = new HashMap<String, MapperEntity>();

    public AlXmlConfigBuilder(String xml) {
        this(xml,null);
    }

    public AlXmlConfigBuilder(String xml, Properties properties) {
        this(new AlXPathParser(xml, true, properties), null, properties);
    }

    public AlXmlConfigBuilder(String xml, String environment, Properties properties){
        this(new AlXPathParser(xml, true, properties), environment, properties);
    }

    private AlXmlConfigBuilder(AlXPathParser parser, String environment, Properties properties) {
        this.parsed = false;
        this.alXPathParser = parser;
        this.environment = environment;
    }


    //parse xml
    public Configuration parse()  {
      if(parsed){
          try {
              throw new Exception("Each XMLConfigBuilder can only be used once.");
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      parsed = true;
      //解析XML
      AlNode alconfig = alXPathParser.evalNode("alconfig");
      parseConfiguration(alconfig);
      return configuration;
    }

    /**
     * 主要解析Mybaties核心配置方法
     * @param root  相当于根节点<configuration></configuration>
     */
    private void parseConfiguration(AlNode root){
        try {
            //issue #117 read properties first
            /*propertiesElement(root.evalNode("properties"));
            Properties settings = settingsAsProperties(root.evalNode("settings"));
            loadCustomVfs(settings);
            typeAliasesElement(root.evalNode("typeAliases"));
            pluginElement(root.evalNode("plugins"));
            objectFactoryElement(root.evalNode("objectFactory"));
            objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
            reflectorFactoryElement(root.evalNode("reflectorFactory"));
            settingsElement(settings);*/
            // read it after objectFactory and objectWrapperFactory issue #631
            environmentsElement(root.evalNode("alenvironments"));
            /*databaseIdProviderElement(root.evalNode("databaseIdProvider"));
            typeHandlerElement(root.evalNode("typeHandlers"));*/
            mapperElement(root.evalNode("almappers"));
        } catch (Exception e) {
            try {
                throw new Exception("Error parsing SQL Mapper Configuration. Cause: " + e, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    //parse environments
    public Configuration environmentsElement(AlNode environments){
        Node node = environments.getNode();
        NodeList env_childNodes = node.getChildNodes();
        System.out.println("env_childNodes: " + env_childNodes.getLength());
        if (env_childNodes != null) {
            //获得所有节点的名称与值
            for (int i = 0; i < env_childNodes.getLength(); i++) {
                Node child_node = env_childNodes.item(i);
                //short nodeType = node.getNodeType();
                if (child_node.getNodeType() == Node.ELEMENT_NODE) {
                    String key = node.getNodeName();
                    //取得节点的属性值
                    if("alenvironments".equals(key)){
                        String id = node.getAttributes().getNamedItem("id").getNodeValue();
                        configuration.setAlenvironmentsId(id);
                        //获取aldataSource
                        NodeList aldataSource_childNodes = node.getChildNodes();
                        for (int j = 0; j < aldataSource_childNodes.getLength(); j++) {
                            Node aldataSource_node = aldataSource_childNodes.item(j);
                            if (aldataSource_node.getNodeType() == Node.ELEMENT_NODE) {
                                String keys = aldataSource_node.getNodeName();
                                //注意，节点的属性也是它的子节点。它的节点类型也是Node.ELEMENT_NODE
                                //轮循子节点

                                //获取alproperty节点
                                NodeList alproperty_childNodes = aldataSource_node.getChildNodes();
                                for(int k =0 ;k < alproperty_childNodes.getLength();k++){
                                    Node alproperty_item = alproperty_childNodes.item(k);
                                    if(alproperty_item.getNodeType() == Node.ELEMENT_NODE){
                                        NamedNodeMap attributes = alproperty_item.getAttributes();
                                        //获取name值
                                        String name = attributes.getNamedItem("name").getNodeValue();
                                        String value = attributes.getNamedItem("value").getNodeValue();
                                        System.out.println("name: " + name + "---value: " + value);
                                       // logger.info("name:",name,">>>>",value);
                                        map.put(name,value);
                                    }
                                }
                                configuration.setJdbcConnectInfo(map);
                            }
                        }
                    }
                }
            }
        }
         return configuration;
    }

    //parse mappers
    public void mapperElement(AlNode mappers){
        if(mappers != null){
            for(AlNode child : mappers.getChildren()){
               if("package".equals(child.getName())){

               }

            }




            //获取almapper
            NodeList childNodes = mappers.getNode().getChildNodes();
            for(int s = 0;s < childNodes.getLength();s++){
                Node almapper_node = childNodes.item(s);
                if (almapper_node.getNodeType() == Node.ELEMENT_NODE) {
                    //获取resource值
                    NamedNodeMap attributes = almapper_node.getAttributes();
                    String resource = almapper_node.getAttributes().getNamedItem("resource").getNodeValue();
                    System.out.println("almapper_node_resource >>> " + resource);
                    configuration.setMapperResource(resource);
                    //解析mapper.xml
                    // AlXmlConfigBuilder parse = new AlXmlConfigBuilder();
                    //parse.doParseMapper(resource);
                }
            }


            // 创建DocumentBuilderFactory的对象
        /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //通过documentBuilder对象 的parser方法加载books。xml文件到当前项目下
            //C:\Users\xudongli\Desktop\Mybatis_Al\AlMybaties\src\main\resources\mybaties-config.xml
            Document document = db.parse(resource);
            //Root Element
            root = document.getDocumentElement();
            //获取namespace mapper接口
            String namespace = root.getAttribute("namespace");
            String substring = namespace.substring(namespace.lastIndexOf(".")+1, namespace.length());
            System.out.print(substring);
            HashMap maper = new HashMap();
            maper.put(substring,namespace);
            configuration.setMapperInterface(maper);

            //获取子节点
            NodeList childNodes = root.getChildNodes();
            for(int i = 0;i < childNodes.getLength();i++){
                Node item = childNodes.item(i);
                if(item.getNodeType() == Node.ELEMENT_NODE){
                    if(item.getNodeName().equals("select")){
                        NamedNodeMap attributes = item.getAttributes();
                        //Id property
                        String id = attributes.getNamedItem("id").getNodeValue();
                        String parameterType = attributes.getNamedItem("parameterType").getNodeValue();
                        String resultMap = attributes.getNamedItem("resultMap").getNodeValue();
                        String textContent = item.getTextContent();
                        System.out.println("id : " + id + "  parameterType : " + parameterType + "  resultMap : " + resultMap);
                        System.out.println("textContent :" + textContent);
                        MapperEntity mapperEntity = new MapperEntity();
                        mapperEntity.setMapperId(id);
                        mapperEntity.setParameterType(parameterType);
                        mapperEntity.setResultMap(resultMap);
                        mapperEntity.setSqlStr(textContent);
                        mapedmap.put(id,mapperEntity);
                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }*/


        }
   }


}
