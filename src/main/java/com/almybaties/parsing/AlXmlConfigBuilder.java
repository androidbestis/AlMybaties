package com.almybaties.parsing;

import com.almybaties.builder.AlBaseBuilder;
import com.almybaties.builder.AlBuilderException;
import com.almybaties.builder.xml.AlXMLMapperBuilder;
import com.almybaties.entity.AlNode;
import com.almybaties.entity.Configuration;
import com.almybaties.io.AlResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Parse Mybatis Config
 */
public class AlXmlConfigBuilder extends AlBaseBuilder{

    private static final Logger logger =  LoggerFactory.getLogger(AlXmlConfigBuilder.class);

    /**
     * whether has been parsed ,the config of mybaties only can be parsed once.
     * 解析标识,因为Configuration是全局变量，只需要解析创建一次即可，
     * true表示已经解析创建过,false则表示没有
     */
    private boolean parsed;
    /**
     * 解析xml文件:包括检测xml文件格式
     */
    private final AlXPathParser alXPathParser;
    private String environment;



    /*ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
    HashMap<String,MapperEntity> mapedmap = new HashMap<String, MapperEntity>();*/

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
        super(new Configuration());
        this.parsed = false;
        this.configuration.setVariables(properties);
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
      //judge the node by node name
      AlNode alconfig = alXPathParser.evalNode("alconfig");
      //解析XML
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
    private void environmentsElement(AlNode environments){
      if(environments != null){
         if(environment == null){
             environment =  environments.getStringAttribute("default");
         }
         for(AlNode child : environments.getChildren()){
             String id = child.getStringAttribute("id");
             if(isSpecifiedEnvironment(id)){
                //1.Transaction Handle Parse
                 AlNode altransactionManager = child.evalNode("altransactionManager");


                 //2.DataSource Handle Parse
                AlNode aldataSource = child.evalNode("aldataSource");
                dataSourceElement(aldataSource);
             }
         }

      }




    }

    //configuration datasource element handles
    private void dataSourceElement(AlNode aldataSource) {
       if(aldataSource != null){
           String type = aldataSource.getStringAttribute("type");
           //obtain aldataSource children properties
           Properties props = aldataSource.getChildrenAsProperties();


       }
       throw new AlBuilderException("");
    }

    //validate Environment id
    private boolean isSpecifiedEnvironment(String id) {
       if(environment == null){
         throw new AlBuilderException("No environment specified.");
       }
       else if(id == null){
         throw  new AlBuilderException("Environment requires an id attribute.");
       }
       //alenvironments default="development"  ==  alenvironment id="development"
       else if(environment.equals(id)){
          return true;
       }
       return false;
    }


    //parse environments
   /* public Configuration environmentsElement(AlNode environments){
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
*/
    //parse mappers
    public void mapperElement(AlNode mappers) throws IOException {
        if(mappers != null){
            for(AlNode child : mappers.getChildren()){
               if("alpackage".equals(child.getName())){
                   String mapperPackage = child.getStringAttribute("name");

               }else{
                   String resource = child.getStringAttribute("resource");
                   String mapperClass = child.getStringAttribute("class");
                   String url = child.getStringAttribute("url");
                   if(resource != null && mapperClass == null && url == null){
                       InputStream inputstream = AlResources.getResourcesAsStream(resource);
                       AlXMLMapperBuilder mapperParser = new AlXMLMapperBuilder(inputstream, configuration, resource, configuration.getSqlFragments());
                       mapperParser.parse();
                   }else if(resource == null && mapperClass != null && url == null){


                   }else if(resource == null && mapperClass == null && url != null){


                   }else{
                       throw new AlBuilderException("A mapper element may only specify a url, resource or class, but not more than one.");
                   }
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
