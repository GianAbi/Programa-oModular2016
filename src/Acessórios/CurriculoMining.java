/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;

import Model.Artigo;
import Model.Regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Rafael
 */
public class CurriculoMining {
    
    private List<Regex> regexs;
    private int anoIni;
    private int anoFim;
    private String data;
    
    private List<Artigo> artigos;
    private List<Element> bancasDoutorado;
    private List<Element> bancasMestrado;
    private List<Element> bancasPFGraduacao;
    private List<Element> orientaDoutorado;
    private List<Element> orientaMestrado;
    private List<Element> orientaPFGraduacao;
    private List<Element> orientaDrAndamento;
    private List<Element> orientaMestrAndamento;
    private List<Element> orientaPFGraduAndamento;
    
    public CurriculoMining(String data, int anoInicio, int anoFim,  List<Regex> regexs) throws SAXException, IOException, ParserConfigurationException{
        
        this.regexs = regexs;
        this.anoIni = anoInicio;
        this.anoFim = anoFim;
        this.data = data;
        
        artigos = new ArrayList<>();
        bancasDoutorado = new ArrayList<>();
        bancasMestrado = new ArrayList<>();
        bancasPFGraduacao = new ArrayList<>();
        orientaDoutorado = new ArrayList<>();
        orientaMestrado = new ArrayList<>();
        orientaPFGraduacao = new ArrayList<>();
        orientaDrAndamento = new ArrayList<>();
        orientaMestrAndamento = new ArrayList<>();
        orientaPFGraduAndamento = new ArrayList<>();
        
        artigos = getArtigosXML();
        bancasDoutorado = getBancasDoutoradoXML();
        bancasMestrado = getBancasMestradoXML();
        bancasPFGraduacao = getBancasPFGraduacaoXML();
        orientaDoutorado = getOrientaDoutoradoXML();
        orientaMestrado = getOrientaMestradoXML();
        orientaPFGraduacao = getOrientaPFGraduacaoXML();
        orientaDrAndamento = getOrientaDrAndamentoXML();
        orientaMestrAndamento = getOrientaMestrAndamentoXML();
        orientaPFGraduAndamento = getOrientaPFGraduAndamentoXML();
        
    }
    
    private List<Element> getOrientaPFGraduAndamentoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-EM-ANDAMENTO");
        
        for(Element orientacao : orientacoes){
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-EM-ANDAMENTO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao.add(orientacao);
                continue;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                trashOrientacao.add(orientacao);
            }
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaMestrAndamentoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
        
        for(Element orientacao : orientacoes){
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao.add(orientacao);
            }
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaDrAndamentoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
        
        for(Element orientacao : orientacoes){
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao.add(orientacao);
            }
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaPFGraduacaoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-CONCLUIDAS");
        
        for(Element orientacao : orientacoes){
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao.add(orientacao);
                continue;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                trashOrientacao.add(orientacao);
            }
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaMestradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
        
        for(Element orientacao : orientacoes){
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashOrientacao.add(orientacao);          
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaDoutoradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashOrientacao = new ArrayList<>();
                                                                            // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO");
        
        for(Element orientacao : orientacoes){                                                // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashOrientacao.add(orientacao);          
        }
        for(Element trash : trashOrientacao)
            orientacoes.remove(trash);
                 
        return orientacoes;
    }
    
    private List<Element> getBancasPFGraduacaoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashBanca = new ArrayList<>();
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
        
        for(Element banca : bancas){
            
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca.add(banca);          
        }
        for(Element trash : trashBanca)
            bancas.remove(trash);
                 
        return bancas;
    }
    
    private List<Element> getBancasMestradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashBanca = new ArrayList<>();
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-MESTRADO");
        
        for(Element banca : bancas){
            
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-MESTRADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca.add(banca);
        }
        for(Element trash : trashBanca)
            bancas.remove(trash);
                 
        return bancas;
    }
    
    private List<Element> getBancasDoutoradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        List<Element> trashBanca = new ArrayList<>();
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
        
        for(Element banca : bancas){
            
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO");
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca.add(banca);
                
        }
        for(Element trash : trashBanca)
            bancas.remove(trash);
        
             
        return bancas;
    }
    
    private List<Artigo> getArtigosXML() throws IOException, SAXException, ParserConfigurationException{
                
        List<Artigo> artigosXML = new ArrayList<>();
        
        List<Element> artigosPublicados = ElementXML.getElementXML(data, "ARTIGO-PUBLICADO");
        
        for(Element umArtigo : artigosPublicados){
            
            Element dadosBasicos = XMLUtils.getSingleElement(umArtigo, "DADOS-BASICOS-DO-ARTIGO");
            int anoPublic = XMLUtils.getIntAttribute(dadosBasicos, "ANO-DO-ARTIGO");
            
            if(! (anoPublic >= anoIni && anoPublic <= anoFim)){
                continue;
            }
            
            Element detalhamento = XMLUtils.getSingleElement(umArtigo, "DETALHAMENTO-DO-ARTIGO");
            String publicacao = XMLUtils.getStringAttribute(detalhamento, "TITULO-DO-PERIODICO-OU-REVISTA");
            
            Artigo artigo = new Artigo(anoPublic);
            
            classificaArtigo(publicacao, artigo);
            
            if(artigo.getClassificacao().equals(""))
                System.out.println(("Titulo de periodico/revista não encontrado: " + publicacao).toUpperCase());
            
            artigosXML.add(artigo);
        }
        
        return artigosXML;
    }
    
    private void classificaArtigo(String publicacao, Artigo artigo) throws IOException, SAXException, ParserConfigurationException{
        
        for(Regex regex : regexs){
            
            if(publicacao.trim().toLowerCase().matches(".*"+regex.getRegex().toLowerCase()+".*")){
                artigo.setClassificacao(regex.getClassificacao());
                if(regex.getType().equals("Conferência"))
                    artigo.setConferencia(true);
                else
                    artigo.setRevista(true);
            
                break;
            }else{ 
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
