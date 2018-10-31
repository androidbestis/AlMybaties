package com.almybaties.entity;

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
 * encapsulation Node
 */
@Data
public class AlNode {

    private final Node node;
    private final String name;
//    private final String body;
    private final Properties attributes;
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

    //parse Node`s attributes
    private Properties parseAttributes(Node node) {
        Properties properties = new Properties();
        NamedNodeMap attributeNodes = node.getAttributes();
        if(null != attributeNodes){
           for(int i = 0;i < attributeNodes.getLength(); i++){
               Node attribute = attributeNodes.item(i);

           }

        }
    }

    public AlNode evalNode(String expression) {
        return xpathParser.evalNode(node, expression);
    }

    //get children nodes
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


}
