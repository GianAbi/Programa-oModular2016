/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acessórios;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  Classe acessório para leitura de uma .xml, inicializando um Document, normalizando e retornando uma lista de elements identificados
 * pelo tag, contido no arquivo .xml
 * @author Rafael
 */
public class ElementXML {
    
    public static List<Element> getElementXML(String pathArquivo, String tag) throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> elementos = new ArrayList<>();
        
        File file = new File(pathArquivo);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName(tag);

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                
                Element eElement = (Element) nNode;
                elementos.add(eElement);
            }
        }
        
        return elementos;
    }
}
