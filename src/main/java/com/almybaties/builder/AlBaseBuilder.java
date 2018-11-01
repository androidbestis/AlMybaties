package com.almybaties.builder;

import com.almybaties.entity.Configuration;

/**
 * @author adonai
 */
public abstract class AlBaseBuilder {

    protected final Configuration configuration;

    public AlBaseBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }



}
