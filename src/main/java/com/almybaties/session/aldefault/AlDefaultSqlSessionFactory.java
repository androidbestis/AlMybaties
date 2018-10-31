package com.almybaties.session.aldefault;

import com.almybaties.entity.Configuration;
import com.almybaties.session.AlSqlSession;
import com.almybaties.session.AlSqlSessionFactory;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default SqlSessionFactory
 * @author adonai
 */
public class AlDefaultSqlSessionFactory implements AlSqlSessionFactory {

    private final Configuration config;

    public AlDefaultSqlSessionFactory(Configuration config){
        this.config = config;
        System.out.println("AlDefaultSqlSessionFactory>>>>>>");
    }

    public AlSqlSession openSession() {
        return openSessionFromDataSource(config.getJdbcConnectInfo());
    }

    public AlSqlSession openSession(Connection connection) {
        return null;
    }

    public Configuration getConfiguration() {
        return config;
    }

    //openSession from config dataSource
    private AlSqlSession openSessionFromDataSource(ConcurrentHashMap<String,String> jdbcConnectInfo) {
        return new AlDefaultSqlSession(config);
    }

}
