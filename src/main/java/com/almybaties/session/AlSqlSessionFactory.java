package com.almybaties.session;

import com.almybaties.entity.Configuration;

import java.sql.Connection;

/**
 * SqlSessionFactory is Prepared for SqlSession
 * @Author adonai
 */
public interface AlSqlSessionFactory {

    //AlSqlSession openSession();
    AlSqlSession openSession(Connection connection);
    Configuration getConfiguration();
}
