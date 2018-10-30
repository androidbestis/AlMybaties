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
}
