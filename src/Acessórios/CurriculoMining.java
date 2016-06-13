/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;

import Model.Artigo;
import Model.Curriculo;
import Model.LinhaPesquisa;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.lang.model.util.Elements;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Rafael
 */
public class CurriculoMining {
    
    private List<Artigo> artigos = new ArrayList<>();
    private List<Element> bancasDoutorado = new ArrayList<>();
    private List<Element> bancasMestrado = new ArrayList<>();
    private List<Element> bancasPFGraduacao = new ArrayList<>();
    private List<Element> orientaDoutorado = new ArrayList<>();
    private List<Element> orientaMestrado = new ArrayList<>();
    private List<Element> orientaPFGraduacao = new ArrayList<>();
    private List<Element> orientaDrAndamento = new ArrayList<>();
    private List<Element> orientaMestrAndamento = new ArrayList<>();
    private List<Element> orientaPFGraduAndamento = new ArrayList<>();
    
    public void startMining(String data) throws SAXException, IOException, ParserConfigurationException{
        
        artigos = getArtigos(data, "2005", "2016");
        bancasDoutorado = getBancasDoutorado(data, "2006", "2016");
        bancasMestrado = getBancasMestrado(data, "2006", "2016");
        bancasPFGraduacao = getBancasPFGraduacao(data, "2006", "2016");
        orientaDoutorado = getOrientaDoutorado(data, "2006", "2016");
        orientaMestrado = getOrientaMestrado(data, "2006", "2016");
        orientaPFGraduacao = getOrientaPFGraduacao(data, "2006", "2016");
        orientaDrAndamento = getOrientaDrAndamento(data, "2006", "2016");
        orientaMestrAndamento = getOrientaMestrAndamento(data, "2006", "2016");
        orientaPFGraduAndamento = getOrientaPFGraduAndamento(data, "2006", "2016");
        
    }
    
    public static List<Element> getOrientaPFGraduAndamento(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-EM-ANDAMENTO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-EM-ANDAMENTO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                orientacoes.remove(orientacao);
                break;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                orientacoes.remove(orientacao);
                break;
            }
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getOrientaMestrAndamento(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                orientacoes.remove(orientacao);
                break;
            }
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getOrientaDrAndamento(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                orientacoes.remove(orientacao);
                break;
            }
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getOrientaPFGraduacao(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-CONCLUIDAS");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                orientacoes.remove(orientacao);
                break;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                orientacoes.remove(orientacao);
                break;
            }
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getOrientaMestrado(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                orientacoes.remove(orientacao);          
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getOrientaDoutorado(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
                                                                            // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                orientacoes.remove(orientacao);          
        }
                 
        return orientacoes;
    }
    
    public static List<Element> getBancasPFGraduacao(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                bancas.remove(banca);          
        }
                 
        return bancas;
    }
    
    public static List<Element> getBancasMestrado(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-MESTRADO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                bancas.remove(banca);           
        }
                 
        return bancas;
    }
    
    public static List<Element> getBancasDoutorado(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                bancas.remove(banca);
                
        }
                 
        return bancas;
    }
    
    public static List<Artigo> getArtigos(String data, String anoInicial, String anoFinal) throws IOException, SAXException, ParserConfigurationException{
        int anoIni = Integer.parseInt(anoInicial);
        int anoFim = Integer.parseInt(anoFinal);
        
        List<Artigo> artigos = new ArrayList<>();
        
        List<Element> artigosPublicados = ElementXML.getElementXML(data, "ARTIGO-PUBLICADO");
        System.out.println(artigosPublicados.size());
        for(Element umArtigo : artigosPublicados){
            
            Element dadosBasicos = XMLUtils.getSingleElement(umArtigo, "DADOS-BASICOS-DO-ARTIGO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO-DO-ARTIGO"));
            System.out.println(anoPublic);
            
            if(! (anoPublic >= anoIni && anoPublic <= anoFim)){
                
//                artigosPublicados.remove(umArtigo);
//                continue;
            }
            
            Element detalhamento = XMLUtils.getSingleElement(umArtigo, "DETALHAMENTO-DO-ARTIGO");
            String publicacao = XMLUtils.getStringAttribute(detalhamento, "TITULO-DO-PERIODICO-OU-REVISTA");
            System.out.println(publicacao);
            
            Artigo artigo = new Artigo(anoPublic);
            classificaArtigo(publicacao, artigo);
            
            artigos.add(artigo);
        }
        
        return artigos;
    }
    
    private static void classificaArtigo(String classificacao, Artigo artigo) throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> allEntry = ElementXML.getElementXML("qualis.xml", "entry");
        
        for(Element entry : allEntry){
            System.out.println(entry.getAttribute("regex"));
            System.out.println(entry.getAttribute(classificacao));
        
            if(entry.getAttribute("regex").equals(classificacao)){
                artigo.setClassificacao(entry.getAttribute("class"));
                if(entry.getAttribute("type").equals("Conferência"))
                    artigo.setConferencia(true);
                else
                    artigo.setRevista(true);
            
                break;
            }
        }
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public List<Element> getBancasDoutorado() {
        return bancasDoutorado;
    }

    public List<Element> getBancasMestrado() {
        return bancasMestrado;
    }

    public List<Element> getBancasPFGraduacao() {
        return bancasPFGraduacao;
    }

    public List<Element> getOrientaDoutorado() {
        return orientaDoutorado;
    }

    public List<Element> getOrientaMestrado() {
        return orientaMestrado;
    }

    public List<Element> getOrientaPFGraduacao() {
        return orientaPFGraduacao;
    }

    public List<Element> getOrientaDrAndamento() {
        return orientaDrAndamento;
    }

    public List<Element> getOrientaMestrAndamento() {
        return orientaMestrAndamento;
    }

    public List<Element> getOrientaPFGraduAndamento() {
        return orientaPFGraduAndamento;
    }

}
