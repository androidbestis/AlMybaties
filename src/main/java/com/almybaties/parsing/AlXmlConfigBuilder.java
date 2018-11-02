package com.almybaties.parsing;

import com.almybaties.builder.AlBaseBuilder;
import com.almybaties.builder.AlBuilderException;
import com.almybaties.builder.xml.AlXMLMapperBuilder;
import com.almybaties.datasource.AlDataSourceFactory;
import com.almybaties.entity.AlNode;
import com.almybaties.entity.Configuration;
import com.almybaties.io.AlResources;
import com.almybaties.mapping.AlEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
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

    //parse mybaties xml
    public Configuration parse()  {
      if(parsed){
          throw new AlBuilderException("Each XMLConfigBuilder can only be used once.");
      }
      parsed = true;
      //judge the node by node name
      AlNode alconfig = alXPathParser.evalNode("/alconfig");
      //解析XML
      parseConfiguration(alconfig);
      return configuration;
    }

    /**
     * 主要解析Mybaties核心配置方法
     * @param root
     */
    private void parseConfiguration(AlNode root){
        try {
            //解析environments节点
            environmentsElement(root.evalNode("alenvironments"));
            //解析mappers节点
            mapperElement(root.evalNode("almappers"));
        } catch (Exception e) {
            throw new AlBuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    //parse environments 解析alenvironments节点<alenvironments></alenvironments>
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
                 //解析transactionManager节点配置信息
                 transactionManagerElement(altransactionManager);

                 //2.DataSource Handle Parse
                AlNode aldataSource = child.evalNode("aldataSource");
                AlDataSourceFactory dsFactory = dataSourceElement(aldataSource);
                DataSource dataSource = dsFactory.getDataSource();
                //这里使用了<委派设计模式>
                AlEnvironment.Builder builder = new AlEnvironment.Builder(id).dataSource(dataSource);
                configuration.setEnvironment(builder.build());
             }
         }
      }
    }

    //parse altransactionManager element 解析altransactionManager节点<altransactionManager></altransactionManager>
    private void transactionManagerElement(AlNode altransactionManager) {
       if(altransactionManager != null){
           String type = altransactionManager.getStringAttribute("type");
           Properties props = altransactionManager.getChildrenAsProperties();
           //TODO unfinished

       }
      throw new AlBuilderException("Environment declaration requires a TransactionFactory.");
    }

    //configuration datasource element handles  解析aldataSource节点<aldataSource></aldataSource>
    private AlDataSourceFactory dataSourceElement(AlNode aldataSource) {
       if(aldataSource != null){
           String type = aldataSource.getStringAttribute("type");
           //obtain aldataSource children properties
           Properties props = aldataSource.getChildrenAsProperties();
           // TODO unfinished
       }
       throw new AlBuilderException("Environment declaration requires a DataSourceFactory.");
    }


    //parse mappers  解析节点<almappers></almappers>
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
        }
   }

    //validate Environment id  验证alenvironment的id
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
}
