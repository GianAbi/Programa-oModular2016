/*
 * 
 */
package acessórios;


import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *  Classe acessório para resgatar informações de atributos e elementos no xml.
 * @author Rafael
 */
public class XMLUtils {
    
    /**
	Retorna uma lista de elementos com um determinado nome no xml
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
    
    // retorna uma elemento imediato de um elemento pai.
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
    * Retorna um atributo em String de um elemento.
     */
    public static String getStringAttribute(Element element, String name){
            
        String value = element.getAttribute(name);
        return (value != null) ? value : "";
    }
    
    
//    retorna um atributo em Inteiro de uma elemento.
    public static int getIntAttribute(Element element, String name){
            
        String value = element.getAttribute(name);

        if (value == null)
                return 0;

        if (value.length() == 0)
                return 0;

        return Integer.parseInt(value);
    }
}
