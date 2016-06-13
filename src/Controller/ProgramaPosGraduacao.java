package Controller;


import Acessórios.CurriculoMining;
import Acessórios.DescompactadorUnzip;
import Acessórios.Download;
import Acessórios.ElementXML;
import Acessórios.XMLUtils;
import Model.Curriculo;
import Model.LinhaPesquisa;
import Model.Professor;
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
    
    private List<Element> linhaPesqElements;
    private List<Element> profElements;
    
//    Date ultimaAval;
//    Date primAval;
    
    public ProgramaPosGraduacao(String programaPos) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        this.nomePrograma = programaPos;
        
        professores = new ArrayList<>();
        pesquisas = new ArrayList<>();
        
        linhaPesqElements = new ArrayList<>();
        profElements = new ArrayList<>();
        
        carregaContents(nomePrograma);
        
        checkCurriculos(nomePrograma);
        
        File fileQualis = new File("qualis.xml");
        if(!fileQualis.exists())
            new Download(new URL(URL_QUALIS));
        
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
    
    private void carregaContents(String programaPos) throws ParserConfigurationException, IOException, SAXException{
        
        File fileContents = new File("contents.xml");
        if(!fileContents.exists()){
            URL oURL = new URL(URL_CONTENTS.replace("[nome_do_programa]", programaPos));
            new Download(oURL);
        }
        
        linhaPesqElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "linha");
        profElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "professor");
        
        for(Element el : linhaPesqElements){
            LinhaPesquisa pesquisa = new LinhaPesquisa(el.getAttribute("nome"));
            System.out.println(el.getAttribute("nome"));
            pesquisas.add(pesquisa);
            
            Element professorElement = XMLUtils.getSingleElement(el, "professor");
        }
        
        for(Element el : profElements){
            Professor prof = new Professor(el.getAttribute("nome"), el.getAttribute("codigo"));
            System.out.println(prof.getCod());
            professores.add(prof);
        }
        //fileContents.deleteOnExit();
    }
    
    private void checkCurriculos(String programaPos) throws IOException{
        
        File file;
        DescompactadorUnzip unzip;
        
        for(Professor prof : professores){
            file = new File("Curriculos" + File.separator + prof.getNome() + File.separator + "curriculo.xml");
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                System.out.println(prof.getCod());
                file = new File(prof.getCod()+ ".zip");
                if(!file.exists())
                    new Download(programaPos, prof.getCod());

                unzip = new DescompactadorUnzip(prof.getCod()+".zip", prof.getNome());
            }
        }   
    }
}
