package com.almybaties.entity;

import com.almybaties.parsing.AlPropertyParser;
import com.almybaties.parsing.AlXPathParser;
import com.sun.org.apache.xalan.internal.xsltc.compiler.XPathParser;
import lombok.Data;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author adonai
 * encapsolation Node
 */
@Data
public class AlNode {

    private final Node node;               //node
    private final String name;             //node name
//    private final String body;
    private final Properties attributes;   //node attributes
    private final Properties variables;
    private final AlXPathParser xpathParser;

    public AlNode(AlXPathParser xpathParser, Node node, Properties variables) {
        this.xpathParser = xpathParser;
        this.node = node;
        this.name = node.getNodeName();
        this.variables = variables;
        this.attributes = parseAttributes(node);
//        this.body = parseBody(node);
    }

    //parse Node attributes
    private Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap attributeNodes = node.getAttributes();
        if(null != attributeNodes){
           for(int i = 0;i < attributeNodes.getLength(); i++){
               Node attribute = attributeNodes.item(i);
               String value = AlPropertyParser.parse(attribute.getNodeValue(), variables);
               attributes.put(attribute.getNodeName(),value);
           }
        }
        return attributes;
    }

    //evaluate node by node name
    public AlNode evalNode(String expression) {
        return xpathParser.evalNode(node, expression);
    }

    //evaluate multiple nodes by node name
    public List<AlNode> evalNodes(String expression){
       return xpathParser.evalNodes(node,expression);
    }


    //obtain the children node
    public List<AlNode> getChildren() {
      List<AlNode> children = new ArrayList<AlNode>();
        NodeList nodeList = node.getChildNodes();
        if(nodeList != null){
            for(int i = 0,n = nodeList.getLength(); i < n; i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    children.add(new AlNode(xpathParser,node,variables));
                }
            }
        }
        return children;
    }

    //get String attribute value
    public String getStringAttribute(String name) {
        return getStringAttribute(name, null);
    }

    //get String attribute value
    public String getStringAttribute(String name, String def) {
        String value = attributes.getProperty(name);
        if (value == null) {
            return def;
        } else {
            return value;
        }
    }


    //gain the properties of children attribute
    public Properties getChildrenAsProperties() {
       Properties properties = new Properties();
       for(AlNode child : getChildren()){
          //gain the attribute of [name]
           String name = child.getStringAttribute("name");
           //gain the attribute of [value]
           String value = child.getStringAttribute("value");
           if(name != null && value != null){
               properties.setProperty(name,value);
           }
       }
       return properties;
    }

}
