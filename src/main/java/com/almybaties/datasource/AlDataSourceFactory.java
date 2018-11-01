package com.almybaties.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author adonai
 * DataSource Encapsolution
 */
public interface AlDataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();

}
