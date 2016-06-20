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
    
    public ProgramaPosGraduacao(String programaPos, String anoInicio, String anoFim) throws MalformedURLException, IOException, SAXException, ParserConfigurationException {
        
        this.nomePrograma = programaPos;
        this.anoInicio = Integer.valueOf(anoInicio);
        this.anoFim = Integer.valueOf(anoFim);
        
        professores = new ArrayList<>();
        pesquisas = new ArrayList<>();
        regexs = new ArrayList<>();
        
        carregaContents();
        
        checkCurriculos();
        
        checkQualis();
        
        carregaCurriculos();
        
//        geraRelatorio();
        
    }
    
    private void carregaContents() throws ParserConfigurationException, IOException, SAXException{
        
        List<Element> linhaPesqElements = new ArrayList();
        
        File fileContents = new File("contents.xml");
        if(!fileContents.exists()){
            URL oURL = new URL(URL_CONTENTS.replace("[nome_do_programa]", nomePrograma));
            Download download = new Download(oURL);
        }
        
        linhaPesqElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "linha");
        
        for(Element el : linhaPesqElements){
            LinhaPesquisa pesquisa = new LinhaPesquisa(el.getAttribute("nome"));
            pesquisas.add(pesquisa);
            
            List<Element> professorElements = XMLUtils.getElements(el, "professor");
            
            for(Element professor : professorElements){
                Professor prof = new Professor(XMLUtils.getStringAttribute(professor, "nome"), XMLUtils.getStringAttribute(professor, "codigo"));
                prof.setLinhaPesquisa(pesquisa.getNome());
                
                if(professores.contains(prof))
                    professores.get(professores.indexOf(prof)).setLinhaPesquisa(pesquisa.getNome());
                else
                    professores.add(prof);
            }
        }
        fileContents.deleteOnExit();
    }
    
    private void checkCurriculos() throws IOException{
        
        Download downZipCurriculos;
        DescompactadorUnzip unzip;
        
        for(Professor prof : professores){
            File fileCurriculo = new File("Curriculos" + File.separator + prof.getNome() + File.separator + "curriculo.xml");
            
            if(!fileCurriculo.exists()){
                fileCurriculo = new File(prof.getCod()+ ".zip");
                if(!fileCurriculo.exists())
                    downZipCurriculos = new Download(nomePrograma, prof.getCod());

                unzip = new DescompactadorUnzip(prof.getCod()+".zip", prof.getNome());
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
            
            Regex regex = new Regex(XMLUtils.getStringAttribute(entry, "regex").trim(), XMLUtils.getStringAttribute(entry, "class"), XMLUtils.getStringAttribute(entry, "type"));

            regexs.add(regex);
            
        }
        
        fileQualis.deleteOnExit();
    }

    private void carregaCurriculos() throws SAXException, IOException, ParserConfigurationException{
        
        for(Professor prof : professores){
            System.out.println(prof.getNome().toUpperCase());
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
    
    public void geraRelatorio() throws IOException{
        
        String breakLine = System.getProperty("line.separator");
        
        int[] ttLinhaPesquisa = new int[27];
        
        int[] ttProgramaPos = new int[27];
        
        Relatorio relatorio = new Relatorio();
        
        for(LinhaPesquisa lnPesquisa : pesquisas){
            for(Professor prof : professores){
                if(lnPesquisa.getNome().toLowerCase().equals(prof.getLinhaPesquisa().toLowerCase())){
                    relatorio.escreve(prof.getNome()+"\t"+prof.getCurriculo().getTotalClassifRevista(0)+"\t"+prof.getCurriculo().getTotalClassifRevista(1)+"\t"+prof.getCurriculo().getTotalClassifRevista(2)+"\t"+prof.getCurriculo().getTotalClassifRevista(3)+"\t"+prof.getCurriculo().getTotalClassifRevista(4)+"\t"+prof.getCurriculo().getTotalClassifRevista(5)+"\t"+prof.getCurriculo().getTotalClassifRevista(6)+"\t"+prof.getCurriculo().getTotalClassifRevista(7)+"\t"+prof.getCurriculo().getTotalClassifRevista(8)
                                  +"\t"+prof.getCurriculo().getTotalClassifEvento(0)+"\t"+prof.getCurriculo().getTotalClassifEvento(1)+"\t"+prof.getCurriculo().getTotalClassifEvento(2)+"\t"+prof.getCurriculo().getTotalClassifEvento(3)+"\t"+prof.getCurriculo().getTotalClassifEvento(4)+"\t"+prof.getCurriculo().getTotalClassifEvento(5)+"\t"+prof.getCurriculo().getTotalClassifEvento(6)+"\t"+prof.getCurriculo().getTotalClassifEvento(7)+"\t"+prof.getCurriculo().getTotalClassifEvento(8)
                                  +"\t"+prof.getCurriculo().getBancasDoutorado()+"\t"+prof.getCurriculo().getBancasMestrado()+"\t"+prof.getCurriculo().getBancasPFGraduacao()
                                  +"\t"+prof.getCurriculo().getOrientaDoutorado()+"\t"+prof.getCurriculo().getOrientaMestrado()+"\t"+prof.getCurriculo().getOrientaPFGraduacao()
                                  +"\t"+prof.getCurriculo().getOrientaDrAndamento()+"\t"+prof.getCurriculo().getOrientaMestrAndamento()+"\t"+prof.getCurriculo().getOrientaPFGraduAndamento()+breakLine);

                    for(int i = 0; i < 18; i++){
                        if(i < 9)
                            ttLinhaPesquisa[i] += prof.getCurriculo().getTotalClassifRevista(i);
                        else
                            ttLinhaPesquisa[i] += prof.getCurriculo().getTotalClassifEvento(i-9);
                    }
                        
                    ttLinhaPesquisa[18] += prof.getCurriculo().getBancasDoutorado();
                    ttLinhaPesquisa[19] += prof.getCurriculo().getBancasMestrado();
                    ttLinhaPesquisa[20] += prof.getCurriculo().getBancasPFGraduacao();
                    ttLinhaPesquisa[21] += prof.getCurriculo().getOrientaDoutorado();
                    ttLinhaPesquisa[22] += prof.getCurriculo().getOrientaMestrado();
                    ttLinhaPesquisa[23] += prof.getCurriculo().getOrientaPFGraduacao();
                    ttLinhaPesquisa[24] += prof.getCurriculo().getOrientaDrAndamento();
                    ttLinhaPesquisa[25] += prof.getCurriculo().getOrientaMestrAndamento();
                    ttLinhaPesquisa[26] += prof.getCurriculo().getOrientaPFGraduAndamento();
                    
                }
            }
            
            relatorio.escreve("Total da linha de pesquisa '"+lnPesquisa.getNome()+"'\t");
            for(int i = 0; i < ttLinhaPesquisa.length; i++){
                
                relatorio.escreve(ttLinhaPesquisa[i]+"\t");
                ttProgramaPos[i] += ttLinhaPesquisa[i];
            }
            relatorio.escreve(breakLine);
            
            
            for(int i=0; i < ttLinhaPesquisa.length; i++)
                ttLinhaPesquisa[i] = 0;
        }
        
        relatorio.escreve("Total Programa PosGraduação '"+nomePrograma+"'\t");
        for(int i = 0; i < ttProgramaPos.length; i++)
            relatorio.escreve(ttProgramaPos[i]+"\t");
        
        
        relatorio.closeRelatorio();
    }
}
