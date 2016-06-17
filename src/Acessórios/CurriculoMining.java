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
    
    List<Regex> regexs;
    int anoIni;
    int anoFim;
    String data;
    
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
        
        Element trashOrientacao = null;
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-EM-ANDAMENTO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-EM-ANDAMENTO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao = orientacao;
                continue;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                trashOrientacao = orientacao;
            }
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaMestrAndamentoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashOrientacao = null;
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao = orientacao;
            }
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaDrAndamentoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashOrientacao = null;
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao = orientacao;
            }
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaPFGraduacaoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashOrientacao = null;
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "OUTRAS-ORIENTACOES-CONCLUIDAS");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim)){
                trashOrientacao = orientacao;
                continue;
            }
            
            String natureza = XMLUtils.getStringAttribute(dadosBasicos, "NATUREZA");
            
            if(!natureza.equals("TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO")){
                trashOrientacao = orientacao;
            }
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaMestradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashOrientacao = null;
                                                                            
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){ 
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashOrientacao = orientacao;          
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getOrientaDoutoradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashOrientacao = null;
                                                                            // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
        List<Element> orientacoes = ElementXML.getElementXML(data, "ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO");
        System.out.println(orientacoes.size());
        
        for(Element orientacao : orientacoes){                                                // VERIFICAR STRING NUM CURRICULO QUE TENHO ORIENTACAO PARA DOUTORADO
            if(!(trashOrientacao == null)){
                orientacoes.remove(trashOrientacao);
                trashOrientacao = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(orientacao, "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashOrientacao = orientacao;          
        }
        if(!(trashOrientacao == null)){
            orientacoes.remove(trashOrientacao);
            trashOrientacao = null;
        }
                 
        return orientacoes;
    }
    
    private List<Element> getBancasPFGraduacaoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashBanca = null;
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            if(!(trashBanca == null)){
                bancas.remove(trashBanca);
                trashBanca = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-GRADUACAO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca = banca;          
        }
        if(!(trashBanca == null)){
            bancas.remove(trashBanca);
            trashBanca = null;
        }
                 
        return bancas;
    }
    
    private List<Element> getBancasMestradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashBanca = null;
        
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-MESTRADO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            if(!(trashBanca == null)){
                bancas.remove(trashBanca);
                trashBanca = null;
            }
            
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-MESTRADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca = banca;           
        }
        if(!(trashBanca == null)){
            bancas.remove(trashBanca);
            trashBanca = null;
        }
                 
        return bancas;
    }
    
    private List<Element> getBancasDoutoradoXML() throws IOException, SAXException, ParserConfigurationException{
        
        Element trashBanca = null;
        List<Element> bancas = ElementXML.getElementXML(data, "PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
        System.out.println(bancas.size());
        
        for(Element banca : bancas){
            if(!(trashBanca == null)){
                bancas.remove(trashBanca);
                trashBanca = null;
            }    
            Element dadosBasicos = XMLUtils.getSingleElement(banca, "DADOS-BASICOS-DA-PARTICIPACAO-EM-BANCA-DE-DOUTORADO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO"));
            System.out.println(anoPublic);
            
            if(!(anoPublic >= anoIni && anoPublic <= anoFim))
                trashBanca = banca;
                
        }
        if(!(trashBanca == null)){
            bancas.remove(trashBanca);
            trashBanca = null;
        }
        
             
        return bancas;
    }
    
    private List<Artigo> getArtigosXML() throws IOException, SAXException, ParserConfigurationException{
                
        List<Artigo> artigos = new ArrayList<>();
        
        List<Element> artigosPublicados = ElementXML.getElementXML(data, "ARTIGO-PUBLICADO");
        System.out.println(artigosPublicados.size());
        for(Element umArtigo : artigosPublicados){
            
            Element dadosBasicos = XMLUtils.getSingleElement(umArtigo, "DADOS-BASICOS-DO-ARTIGO");
            int anoPublic = Integer.parseInt(XMLUtils.getStringAttribute(dadosBasicos, "ANO-DO-ARTIGO"));
            System.out.println(anoPublic);
            
            if(! (anoPublic >= anoIni && anoPublic <= anoFim)){
                
//                artigosPublicados.remove(umArtigo);
                continue;
            }
            
            Element detalhamento = XMLUtils.getSingleElement(umArtigo, "DETALHAMENTO-DO-ARTIGO");
            String publicacao = XMLUtils.getStringAttribute(detalhamento, "TITULO-DO-PERIODICO-OU-REVISTA");
            System.out.println(publicacao);
            
            Artigo artigo = new Artigo(anoPublic);
            System.out.println(artigo.getAno()+ " " +artigo.getClassificacao()+ " " +artigo.getConferencia()+ " " +artigo.getRevista() );
            classificaArtigo(publicacao, artigo);
            System.out.println(artigo.getAno()+ " " +artigo.getClassificacao()+ " " +artigo.getConferencia()+ " " +artigo.getRevista() );
            
            artigos.add(artigo);
        }
        
        return artigos;
    }
    
    private void classificaArtigo(String classificacao, Artigo artigo) throws IOException, SAXException, ParserConfigurationException{
        
        //List<Element> allEntry = ElementXML.getElementXML("qualis.xml", "entry");
        
        Pattern pattern;
        
        for(Regex regex : regexs){
            pattern = Pattern.compile(".*"+classificacao+".*");
            Matcher matcher = pattern.matcher(regex.getRegex());
            
            if(matcher.matches()){
                System.out.println(regex.getRegex());
                System.out.println(regex.getClassificacao());
                artigo.setClassificacao(regex.getClassificacao());
                if(regex.getType().equals("Conferência"))
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
