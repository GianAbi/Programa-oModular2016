/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Rafael
 */
public class XMLUtils {
    
    /**
	 * Returns a list of elements having a given tag
     * @param element
     * @param tagName
     * @return 
	 */
    public static List<Element> getElements(Element element, String tagName){
        
        Node node = element.getFirstChild();
        List<Element> elements = new ArrayList<Element>();

        while (node != null)
        {
                if (node.getNodeType() == Node.ELEMENT_NODE
                                && node.getNodeName().compareTo(tagName) == 0)
                        elements.add((Element) node);

                node = node.getNextSibling();
        }

        return elements;
    }
    
    public static Element getSingleElement(Element element, String tagName){
            
        Node node = element.getFirstChild();

        while (node != null)
        {
                if (node.getNodeType() == Node.ELEMENT_NODE
                                && node.getNodeName().compareTo(tagName) == 0)
                        return (Element) node;

                node = node.getNextSibling();
        }

        return null;
    }

    /**
     * Loads an optional string attribute from a XML element
     */
    public static String getStringAttribute(Element element, String name){
            
        String value = element.getAttribute(name);
        return (value != null) ? value : "";
    }
    
    public static int getIntAttribute(Element element, String name){
            
        String value = element.getAttribute(name);

        if (value == null)
                return 0;

        if (value.length() == 0)
                return 0;

        return Integer.parseInt(value);
    }

    /**
     * Loads an optional integer attribute from a XML element
     */
    public static long getLongAttribute(Element element, String name){
            
        String value = element.getAttribute(name);

        if (value == null)
                return 0;

        if (value.length() == 0)
                return 0;

        return Long.parseLong(value, 10);
    }
    
    /**
     * Loads an optional Boolean element value from a XML element
     */
    public static boolean getBooleanNode(Element element, String name, boolean _default){
            
        Element child = getSingleElement(element, name);

        if (child == null)
                return _default;

        String value = child.getTextContent();

        if (value == null)
                return _default;

        if (value.compareTo("S") == 0)
                return true;

        if (value.compareTo("N") == 0)
                return false;

        return _default;
    }
}
