package com.almybaties.mapping;

import lombok.Data;

/**
 * @author adonai
 * an Actual Sql String
 */
@Data
public class AlBoundSql {

   private final String sql;

   public AlBoundSql(String sql){
       this.sql = sql;
   }

}
