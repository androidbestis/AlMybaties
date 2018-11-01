package com.almybaties.session;

import com.almybaties.parsing.AlXmlConfigBuilder;
import com.almybaties.entity.Configuration;
import com.almybaties.session.aldefault.AlDefaultSqlSessionFactory;
import java.util.Properties;

/**
 * builds SqlSessionFactory create {@link AlSqlSessionFactory} by various conditions
 * Builder Design Model
 * @author adonai
 */
public class AlSqlSessionFactoryBuilder {

    public AlSqlSessionFactory build(String xml) {
        return build(xml,null, null);
    }

    public AlSqlSessionFactory build(String xml, String environment) {
        return build(xml, environment, null);
    }

    public AlSqlSessionFactory build(String xml,Properties properties) {
        return build(xml,null, properties);
    }

    /**
     * Main Entrance Of Analysis
     * @param xml            //Mybatis-config Path
     * @param environment    //环境参数  当前的环境id. alenvironments id  environment是当前环境，在配置文件节点environments中定义
     * @param properties     //Properties用于指定属性配置信息
     * @return
     */
    public AlSqlSessionFactory build(String xml, String environment, Properties properties) {
        //Xml Main entrance of analysis
        AlXmlConfigBuilder parser = new AlXmlConfigBuilder(xml, environment, properties);
        return build(parser.parse());  //parse()方法得到Configuration
    }

    //build default SqlSessionFactory
    public AlSqlSessionFactory build(Configuration config){
        return new AlDefaultSqlSessionFactory(config);
    }

}
