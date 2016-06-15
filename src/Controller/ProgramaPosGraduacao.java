package Controller;


import Acessórios.CurriculoMining;
import Acessórios.DescompactadorUnzip;
import Acessórios.Download;
import Acessórios.ElementXML;
import Acessórios.XMLUtils;
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
    
    String nomePrograma;
    
    private final String URL_QUALIS = "https://s3.amazonaws.com/posgraduacao/qualis.xml";
    private final String URL_CONTENTS = "https://s3.amazonaws.com/posgraduacao/[nome_do_programa]/contents.xml";
    
    private List<Professor> professores;
    private List<LinhaPesquisa> pesquisas;
    private List<Regex> regexs;
    
    List<Element> linhaPesqElements;
//    List<Element> profElements;
    
//      Date ultimaAval;
//    Date primAval;
    
    public ProgramaPosGraduacao(String programaPos) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        this.nomePrograma = programaPos;
        
        professores = new ArrayList<>();
//        profElements = new ArrayList<>();
        pesquisas = new ArrayList<>();
        regexs = new ArrayList<>();
        
        carregaContents();
        
        checkCurriculos();
        
        checkQualis();
        
        carregaCurriculos();
        
//        File fileQualis = new File("qualis.xml");
//        if(!fileQualis.exists())
//            new Download(new URL(URL_QUALIS));
        
        
        
        
        
    }
    
    private void carregaContents() throws ParserConfigurationException, IOException, SAXException{
        
        linhaPesqElements = new ArrayList();
        
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
            System.out.println(regex.getRegex() + regex.getClassificacao() + regex.getType());
        }
    }

    private void carregaCurriculos() throws SAXException, IOException, ParserConfigurationException{
        
        for(Professor prof : professores){
            CurriculoMining cm = new CurriculoMining();
            cm.startMining("Curriculos\\"+ prof.getNome() +"\\curriculo.xml");
            
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
}
