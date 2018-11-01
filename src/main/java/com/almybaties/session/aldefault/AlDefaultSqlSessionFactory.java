package com.almybaties.session.aldefault;

import com.almybaties.entity.Configuration;
import com.almybaties.mapping.AlEnvironment;
import com.almybaties.session.AlSqlSession;
import com.almybaties.session.AlSqlSessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default SqlSessionFactory
 * @author adonai
 */
public class AlDefaultSqlSessionFactory implements AlSqlSessionFactory {

    private final Configuration config;

    public AlDefaultSqlSessionFactory(Configuration config){
        this.config = config;
    }

    /*public AlSqlSession openSession() {
        return openSessionFromDataSource(config.getJdbcConnectInfo());
    }*/

    public AlSqlSession openSession(Connection connection) {
        return openSessionFromDataSource(connection);
    }

    public Configuration getConfiguration() {
        return config;
    }

    //openSession from config dataSource
    private AlSqlSession openSessionFromDataSource(Connection connection) {
        boolean autoCommit;
        try {
            autoCommit = connection.getAutoCommit();
        } catch (SQLException e) {
            // Failover to true, as most poor drivers
            // or databases won't support transactions
            autoCommit = true;
        }
        final AlEnvironment environment = config.getEnvironment();




        return new AlDefaultSqlSession(config);
    }

}
