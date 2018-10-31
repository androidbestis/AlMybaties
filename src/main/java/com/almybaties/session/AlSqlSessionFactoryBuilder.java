package com.almybaties.session;

import com.almybaties.alxmlparser.AlXmlConfigBuilder;
import com.almybaties.entity.Configuration;
import com.almybaties.session.aldefault.AlDefaultSqlSessionFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * builds SqlSessionFactory create @link (SqlSessionFactory) by various conditions
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

    public AlSqlSessionFactory build(String xml, String environment, Properties properties) {
        AlXmlConfigBuilder parser = null;
        parser = new AlXmlConfigBuilder(xml, environment, properties);
        return build(parser.parse());
    }

    //build default SqlSessionFactory
    public AlSqlSessionFactory build(Configuration config){
        return new AlDefaultSqlSessionFactory(config);
    }

}
