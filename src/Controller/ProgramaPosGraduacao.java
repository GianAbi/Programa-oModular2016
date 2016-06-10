package Controller;


import Acessórios.DescompactadorUnzip;
import Acessórios.Download;
import Acessórios.ElementXML;
import Model.Artigo;
import Model.Pesquisa;
import Model.Professor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;



/**
 *  Classe que detém informações de Programa de Gradução.
 * @author Rafael
 */
public class ProgramaPosGraduacao {
    
    List<Professor> professores;
    List<Pesquisa> pesquisas;
    
    List<Element> linhaPesqElements;
    List<Element> profElements;
    
    ElementXML captura;
//    Date ultimaAval;
//    Date primAval;
    
    public ProgramaPosGraduacao(String programaPos) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        professores = new ArrayList<>();
        pesquisas = new ArrayList<>();
        linhaPesqElements = new ArrayList<>();
        profElements = new ArrayList<>();
        
        captura = new ElementXML();
        
        File file = new File("contents.xml");
        if(!file.exists()){
            
            URL oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/contents.xml");
            Download downloadContents = new Download(oURL);
        }
        
        linhaPesqElements = captura.getElementXML(file.getAbsolutePath(), "linha");
        profElements = captura.getElementXML(file.getAbsolutePath(), "professor");
        
        for(Element el : linhaPesqElements){
            Pesquisa pesquisa = new Pesquisa(el.getAttribute("nome"));
            System.out.println(el.getAttribute("nome"));
            pesquisas.add(pesquisa);    
        }
        
        for(Element el : profElements){
            Professor prof = new Professor(el.getAttribute("nome"), el.getAttribute("codigo"));
            System.out.println(prof.getCod());
            professores.add(prof);
        }
        
        file.deleteOnExit();
        
        checkCurriculos(programaPos);
        
        
    }
    
    public void checkCurriculos(String programaPos) throws IOException{
        
        Download download;
        File file;
        DescompactadorUnzip unzip;
        
        for(Professor prof : professores){
            file = new File("Curriculos" + File.separator + prof.getNome() + File.separator + "curriculo.xml");
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                System.out.println(prof.getCod());
                file = new File(prof.getCod()+ ".zip");
                if(!file.exists())
                    download = new Download(programaPos, prof.getCod());

                unzip = new DescompactadorUnzip(prof.getCod()+".zip", prof.getNome());
            }
        }   
    }
}
