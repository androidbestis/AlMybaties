package com.almybaties.builder.xml;

import com.almybaties.builder.AlBaseBuilder;
import com.almybaties.entity.AlNode;
import com.almybaties.entity.Configuration;
import com.almybaties.parsing.AlXPathParser;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * XML Mapper config file parse
 * @author adonai
 */
public class AlXMLMapperBuilder extends AlBaseBuilder{

    private final AlXPathParser parser;
    private final String resource;
    private final Map<String,AlNode> sqlFragments;

    public AlXMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, AlNode> sqlFragments, String namespace) {
        this(inputStream, configuration, resource, sqlFragments);
    }

    public AlXMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, AlNode> sqlFragments) {
        this(configuration,new AlXPathParser(inputStream, true, configuration.getVariables()), resource, sqlFragments);
    }

    public AlXMLMapperBuilder(Configuration configuration,AlXPathParser parser,String resource,Map<String,AlNode> sqlFragments) {
        super(configuration);
        this.parser = parser;
        this.resource = resource;
        this.sqlFragments = sqlFragments;
    }

    public void parse(){
      if(!configuration.isResourceLoaded(resource)){
          configurationElement(parser.evalNode("/mapper"));
      }

    }

    private void configurationElement(AlNode context) {
      //parse select|insert|update|delete
      buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
    }


    private void buildStatementFromContext(List<AlNode> list) {


    }
}
