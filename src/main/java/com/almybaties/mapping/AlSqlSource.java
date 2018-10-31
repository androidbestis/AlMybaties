package com.almybaties.mapping;

/**
 * @author adonai
 * Represents the content of mapped statement read from  an xml file or an annotation
 * It creates the SQL that will be passed to the database out of the input parameter received from the user.
 */
public interface AlSqlSource {

    AlBoundSql getBoundSql(Object parameterObject);

}
