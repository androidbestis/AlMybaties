package com.almybaties.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接器
 */
public class Connector {

    private volatile static Connection instance;

    private Connector(){}

     public static Connection getConnection(ConcurrentHashMap<String,String> map){
         try {
             if(null != instance){
             }else{
                 synchronized (Connector.class){
                     if(instance == null){
                         //1.加载驱动程序
                         Class.forName(map.get("driver"));
                         //2.获得数据库链接
                         instance = DriverManager.getConnection(map.get("url"), map.get("username"), map.get("password"));
                     }
                 }
             }
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SQLException e) {
             e.printStackTrace();
         }
        return instance;
     }

}
