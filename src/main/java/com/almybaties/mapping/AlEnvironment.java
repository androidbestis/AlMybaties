package com.almybaties.mapping;

import javax.sql.DataSource;

/**
 * @author adonai
 * Element environment entity ptoperties
 */
public final class AlEnvironment {

    private final String id;      //environment`s id
//  private final TransactionFactory transactionFactory;
    private final DataSource dataSource;   //environment`s datasource properties

    public AlEnvironment(String id , DataSource dataSource){
       if(id == null){
           throw new IllegalArgumentException("Parameter 'id' must not be null");
       }
       this.id = id;
       if(dataSource == null){
           throw new IllegalArgumentException("Parameter 'dataSource' must not be null");
       }
       this.dataSource = dataSource;
    }




}
