package com.almybaties.parsing;

import com.almybaties.entity.AlNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * XmlParse Entity
 * XPathParser 是用来解析xml文件的 包括检测xml文件格式
 * @author adonai
 */
public class AlXPathParser {

    private Document document = null;       //use for parse XML file
    private boolean validation;             //validate
    private Properties variables;
    private XPath xpath;                    //将元素转换成为节点信息
    private EntityResolver entityResolver;  //通过key查找dtd文件

    public AlXPathParser(String xml, boolean validation, Properties variables) {
        commonConstructor(validation, variables, null);
        this.document = createDocument(xml);
    }

    public AlXPathParser(InputStream inputstream, boolean validation, Properties variables){
        commonConstructor(validation, variables, null);
        this.document = createDocument(new InputSource(inputstream));
    }

    //evaluate one node by node name
    public AlNode evalNode(String expression){
       return evalNode(document,expression);
    }

    //evaluate one node by node name
    public AlNode evalNode(Object root, String expression) {
        Node node = (Node) evaluate(expression, root, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new AlNode(this,node,variables);
    }

    //evaluate multiple nodes by node name
    public List<AlNode> evalNodes(String expression){
        return evalNodes(document,expression);
    }

    //evaluate multiple nodes by node name
    public List<AlNode> evalNodes(Object root, String expression) {
        List<AlNode> xnodes = new ArrayList<AlNode>();
        NodeList nodes = (NodeList) evaluate(expression, root, XPathConstants.NODESET);
        for(int i = 0;i < nodes.getLength(); i++){
            xnodes.add(new AlNode(this,nodes.item(i),variables));
        }
        return xnodes;
    }

    private Object evaluate(String expression, Object root, QName returnType) {
        Object evaluate = null;
        try {
            evaluate = xpath.evaluate(expression, root, returnType);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return evaluate;
    }


    private Document createDocument(InputSource inputsource){
        DocumentBuilder builder = null;

        // Create DocumentBuilderFactory object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //set DocumentBuilderFactory various
//            factory.setValidating(validation);     // 指定由此代码生成的解析器将验证被解析的文档
//            factory.setNamespaceAware(false);      // 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
//            factory.setIgnoringComments(true);     // 指定由此代码生成的解析器将忽略注释。
//            factory.setIgnoringElementContentWhitespace(false);   // 指定由此工厂创建的解析器在解析XML,文档时，必须删除元素内容中的空格
//            factory.setCoalescing(false);              // 指定由此代码生成的解析器将把 CDATA 节点转换为 Text
//            factory.setExpandEntityReferences(true);  // 指定由此代码产生的解析器将扩展实体引用节点

        //create DocumentBuilder object
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        builder.setErrorHandler(new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {

            }

            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }
        });

        try {
            return builder.parse(inputsource);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将xml数据源解析成Document对象
     */
    private Document createDocument(String xml){
        DocumentBuilder builder = null;

        // Create DocumentBuilderFactory object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //set DocumentBuilderFactory various
//            factory.setValidating(validation);     // 指定由此代码生成的解析器将验证被解析的文档
//            factory.setNamespaceAware(false);      // 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
//            factory.setIgnoringComments(true);     // 指定由此代码生成的解析器将忽略注释。
//            factory.setIgnoringElementContentWhitespace(false);   // 指定由此工厂创建的解析器在解析XML,文档时，必须删除元素内容中的空格
//            factory.setCoalescing(false);              // 指定由此代码生成的解析器将把 CDATA 节点转换为 Text
//            factory.setExpandEntityReferences(true);  // 指定由此代码产生的解析器将扩展实体引用节点

        //create DocumentBuilder object
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        builder.setErrorHandler(new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {

            }

            public void error(SAXParseException exception) throws SAXException {
              throw exception;
            }

            public void fatalError(SAXParseException exception) throws SAXException {
              throw exception;
            }
        });

        try {
            return builder.parse(xml);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver) {
        this.validation = validation;
        this.entityResolver = entityResolver;
        this.variables = variables;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

}
