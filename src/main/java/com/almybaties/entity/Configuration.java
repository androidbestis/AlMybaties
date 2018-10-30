package com.almybaties.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * xml 配置信息
 */
@Data
public class Configuration {

      private String alenvironmentsId;
      private ConcurrentHashMap<String,String> jdbcConnectInfo;
      private String mapperResource;
      private HashMap<String,String> mapperInterface;

      public String getAlenvironmentsId() {
            return alenvironmentsId;
      }

      public void setAlenvironmentsId(String alenvironmentsId) {
            this.alenvironmentsId = alenvironmentsId;
      }

      public ConcurrentHashMap<String, String> getJdbcConnectInfo() {
            return jdbcConnectInfo;
      }

      public void setJdbcConnectInfo(ConcurrentHashMap<String, String> jdbcConnectInfo) {
            this.jdbcConnectInfo = jdbcConnectInfo;
      }

      public String getMapperResource() {
            return mapperResource;
      }

      public void setMapperResource(String mapperResource) {
            this.mapperResource = mapperResource;
      }

      public HashMap<String, String> getMapperInterface() {
            return mapperInterface;
      }

      public void setMapperInterface(HashMap<String, String> mapperInterface) {
            this.mapperInterface = mapperInterface;
      }
}
