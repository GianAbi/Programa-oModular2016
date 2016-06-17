package Controller;


import Acessórios.CurriculoMining;
import Acessórios.DescompactadorUnzip;
import Acessórios.Download;
import Acessórios.ElementXML;
import Acessórios.Relatorio;
import Acessórios.XMLUtils;
import Model.Artigo;
import Model.Curriculo;
import Model.LinhaPesquisa;
import Model.Professor;
import Model.Regex;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;



/**
 *  Classe que detém informações de Programa de Gradução.
 * @author Rafael
 */
public class ProgramaPosGraduacao {
    
    private String nomePrograma;
    private int anoInicio;
    private int anoFim;
    
    private final String URL_QUALIS = "https://s3.amazonaws.com/posgraduacao/qualis.xml";
    private final String URL_CONTENTS = "https://s3.amazonaws.com/posgraduacao/[nome_do_programa]/contents.xml";
    
    private List<Professor> professores;
    private List<LinhaPesquisa> pesquisas;
    private List<Regex> regexs;
    
    
//    List<Element> profElements;
    
//      Date ultimaAval;
//    Date primAval;
    
    public ProgramaPosGraduacao(String programaPos, String anoInicio, String anoFim) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        this.nomePrograma = programaPos;
        this.anoInicio = Integer.valueOf(anoInicio);
        this.anoFim = Integer.valueOf(anoFim);
        
        professores = new ArrayList<>();
//        profElements = new ArrayList<>();
        pesquisas = new ArrayList<>();
        regexs = new ArrayList<>();
        
        carregaContents();
        
        checkCurriculos();
        
        checkQualis();
        
//        carregaCurriculos();
        
        geraRelatorio();
        
//        File fileQualis = new File("qualis.xml");
//        if(!fileQualis.exists())
//            new Download(new URL(URL_QUALIS));
        
        
        
        
        
    }
    
    private void carregaContents() throws ParserConfigurationException, IOException, SAXException{
        
        List<Element> linhaPesqElements = new ArrayList();
        
        File fileContents = new File("contents.xml");
        if(!fileContents.exists()){
            URL oURL = new URL(URL_CONTENTS.replace("[nome_do_programa]", nomePrograma));
            Download download = new Download(oURL);
        }
        
        linhaPesqElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "linha");
//        profElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "professor");
        
        for(Element el : linhaPesqElements){
            LinhaPesquisa pesquisa = new LinhaPesquisa(el.getAttribute("nome"));
            System.out.println(el.getAttribute("nome"));
            pesquisas.add(pesquisa);
            
            List<Element> professorElements = XMLUtils.getElements(el, "professor");
            for(Element professor : professorElements){
                Professor prof = new Professor(XMLUtils.getStringAttribute(professor, "nome"), XMLUtils.getLongAttribute(professor, "codigo"));
                prof.addLinhaPesquisa(pesquisa.getNome());
                
                System.out.println(prof.getNome() + prof.getCod() + prof.getUmaLinhaPesquisa(0));
                if(professores.contains(prof))
                    professores.get(professores.indexOf(prof)).addLinhaPesquisa(pesquisa.getNome());
                else
                    professores.add(prof);
            }
        }
        
//        for(Element el : profElements){
//            Professor prof = new Professor(el.getAttribute("nome"), el.getAttribute("codigo"));
//            System.out.println(prof.getCod());
//            professores.add(prof);
//        }
        //fileContents.deleteOnExit();
    }
    
    private void checkCurriculos() throws IOException{
        
        Download downZipCurriculos;
        DescompactadorUnzip unzip;
        
        for(Professor prof : professores){
            File file = new File("Curriculos" + File.separator + prof.getNome() + File.separator + "curriculo.xml");
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                System.out.println((prof.getCodEmString()));
                file = new File(prof.getCodEmString()+ ".zip");
                if(!file.exists())
                    downZipCurriculos = new Download(nomePrograma, prof.getCodEmString());

                unzip = new DescompactadorUnzip(prof.getCodEmString()+".zip", prof.getNome());
            }
        }   
    }
    
    private void checkQualis() throws MalformedURLException, IOException, SAXException, ParserConfigurationException{
        
        Download downQualis;
                
        File fileQualis = new File("qualis.xml");
        if(!fileQualis.exists())
            downQualis = new Download(new URL(URL_QUALIS));
        
        List<Element> regexElements = ElementXML.getElementXML(fileQualis.getAbsolutePath(), "entry");
        
        for(Element entry : regexElements){
            
            Regex regex = new Regex(XMLUtils.getStringAttribute(entry, "regex"), XMLUtils.getStringAttribute(entry, "class"), XMLUtils.getStringAttribute(entry, "type"));
            regexs.add(regex);
            
        }
    }

    private void carregaCurriculos() throws SAXException, IOException, ParserConfigurationException{
        
        for(Professor prof : professores){
            CurriculoMining cm = new CurriculoMining("Curriculos\\"+ prof.getNome() +"\\curriculo.xml", anoInicio, anoFim, regexs);
            //cm.startMining("Curriculos\\"+ prof.getNome() +"\\curriculo.xml", regexs);
            
            Curriculo curriculo = new Curriculo();
            curriculo.setArtigos(cm.getArtigos());
            curriculo.setBancasDoutorado(cm.getBancasDoutorado().size());
            curriculo.setBancasMestrado(cm.getBancasMestrado().size());
            curriculo.setBancasPFGraduacao(cm.getBancasPFGraduacao().size());
            curriculo.setOrientaDoutorado(cm.getOrientaDoutorado().size());
            curriculo.setOrientaMestrado(cm.getOrientaMestrado().size());
            curriculo.setOrientaPFGraduacao(cm.getOrientaPFGraduacao().size());
            curriculo.setOrientaDrAndamento(cm.getOrientaDrAndamento().size());
            curriculo.setOrientaMestrAndamento(cm.getOrientaMestrAndamento().size());
            curriculo.setOrientaPFGraduAndamento(cm.getOrientaPFGraduAndamento().size());
            
            prof.setCurriculo(curriculo);
        }
    }
    
    private void geraRelatorio() throws IOException{
        
        Relatorio relatorio = new Relatorio();
        
        for(LinhaPesquisa lnPesquisa : pesquisas){
            for(Professor prof : professores){
                int ttArtA1 = 0;
                int ttArtA2 = 0;
                int ttArtB1 = 0;
                int ttArtB2 = 0;
                int ttArtB3 = 0;
                int ttArtB4 = 0;
                int ttArtC = 0;
                int ttArtNC = 0;
//                for(Artigo artigo : prof.getCurriculo().getArtigos()){
//                    
//                    if(artigo.getClassificacao().equals("A1"))
//                        ++ttArtA1;
//                    if(artigo.getClassificacao().equals("A2"))
//                        ++ttArtA2;
//                    if(artigo.getClassificacao().equals("B1"))
//                        ++ttArtB1;
//                    if(artigo.getClassificacao().equals("B2"))
//                        ++ttArtB2;
//                    if(artigo.getClassificacao().equals("B3"))
//                        ++ttArtB3;
//                    if(artigo.getClassificacao().equals("B4"))
//                        ++ttArtB4;
//                    if(artigo.getClassificacao().equals("C"))
//                        ++ttArtC;
//                    if(artigo.getClassificacao().equals("NC"))
//                        ++ttArtNC;
//                }
                relatorio.escreve(prof.getNome()+"\t"+ttArtA1+"\t"+ttArtA2+"\t"+ttArtB1+"\t"+ttArtB2+"\t"+ttArtB3+"\t"+ttArtB4+"\t"+ttArtC+"\t"+ttArtNC);
                
            }
            
            relatorio.escreve("Total da linha de pesquisa '"+lnPesquisa.getNome()+"'");
        }
        
        relatorio.closeRelatorio();
    }
}
