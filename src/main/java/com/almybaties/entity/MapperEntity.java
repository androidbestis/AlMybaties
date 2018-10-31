package com.almybaties.entity;

import lombok.Data;

/**
 * Mapper Statement Entity
 */
@Data
public class MapperEntity {

     private String mapperId;
     private Object parameterType;
     private String resultMap;
     private String sqlStr;

}
