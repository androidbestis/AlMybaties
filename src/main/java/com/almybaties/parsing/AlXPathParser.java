package com.almybaties.parsing;

import com.almybaties.entity.AlNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.io.StringReader;
import java.util.Properties;

/**
 * XmlParse Entity
 * @author adonai
 */
public class AlXPathParser {

    private Document document = null;
    private boolean validation;
    private Properties variables;
    private XPath xpath;
    private EntityResolver entityResolver;

    public AlXPathParser(String xml, boolean validation, Properties variables) {
        commonConstructor(validation, variables, null);
        this.document = createDocument(xml);
    }


    public AlNode evalNode(String expression){
       return evalNode(document,expression);
    }

    public AlNode evalNode(Object root, String expression) {
        Node node = (Node) evaluate(expression, root, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new AlNode(this,node,variables);
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

    private Document createDocument(String xml){
        DocumentBuilder builder = null;

        // Create DocumentBuilderFactory object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //set DocumentBuilderFactory various
//            factory.setValidating(validation);
//            factory.setNamespaceAware(false);
//            factory.setIgnoringComments(true);
//            factory.setIgnoringElementContentWhitespace(false);
//            factory.setCoalescing(false);
//            factory.setExpandEntityReferences(true);

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
