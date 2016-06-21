package controller;


import acessórios.CurriculoMining;
import acessórios.DescompactadorUnzip;
import acessórios.Download;
import acessórios.ElementXML;
import acessórios.Relatorio;
import acessórios.XMLUtils;
import model.Artigo;
import model.Curriculo;
import model.LinhaPesquisa;
import model.Professor;
import model.Regex;
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
 *  Controller responsável por carregar todas as informações necessárias,
 *  gerenciar existência de arquivos e seus downloads se necessário.
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
        
    }
    
//    Resgata as informações do arquivo contents.xml para os objetos Linha de pesquisa e Professor. 
//    Caso o arquivo não exista, solicita download.
    private void carregaContents() throws ParserConfigurationException, IOException, SAXException{
        
        List<Element> linhaPesqElements = new ArrayList();
        
        File fileContents = new File("contents.xml");
        if(!fileContents.exists()){
            URL oURL = new URL(URL_CONTENTS.replace("[nome_do_programa]", nomePrograma));
            Download download = new Download(oURL);  // solicita download
        }
        
        linhaPesqElements = ElementXML.getElementXML(fileContents.getAbsolutePath(), "linha"); // pega os elementos linhas no .xml
        
        for(Element el : linhaPesqElements){
            LinhaPesquisa pesquisa = new LinhaPesquisa(el.getAttribute("nome"));
            pesquisas.add(pesquisa);
            
            List<Element> professorElements = XMLUtils.getElements(el, "professor"); // pega os elementos professor no .xml
            
            for(Element professor : professorElements){                             // pega os atributos do elemento professor. nome e cod
                Professor prof = new Professor(XMLUtils.getStringAttribute(professor, "nome"), XMLUtils.getStringAttribute(professor, "codigo"));
                prof.setLinhaPesquisa(pesquisa.getNome());
                
                if(professores.contains(prof))
                    professores.get(professores.indexOf(prof)).setLinhaPesquisa(pesquisa.getNome());
                else
                    professores.add(prof);
            }
        }
        fileContents.deleteOnExit(); //programa exclusão do arquivo ao encerrar o programa.
    }
    
//    verifica se os arquivos .xml dos curriculos dos professores estão disponíveis.
//    caso não estejam nem mesmo compactador em .zip, solitica seus respectivos downloads.
    private void checkCurriculos() throws IOException{
        
        Download downZipCurriculos;
        DescompactadorUnzip unzip;
        
        for(Professor prof : professores){
            File fileCurriculo = new File("Curriculos" + File.separator + prof.getNome() + File.separator + "curriculo.xml");
            
            if(!fileCurriculo.exists()){
                fileCurriculo = new File(prof.getCod()+ ".zip");
                if(!fileCurriculo.exists())
                    downZipCurriculos = new Download(nomePrograma, prof.getCod());  //solicita download do arquivo .zip

                unzip = new DescompactadorUnzip(prof.getCod()+".zip", prof.getNome());  //descompacta o arquivo .zip baixado
            }
        }   
    }
    
//    verifica a existência local do arquivo qualis.xml, que contém as informações de regexs, classificacao e tipo de revistas ou eventos.
//    caso não esteja disponível, solicita download.
    private void checkQualis() throws MalformedURLException, IOException, SAXException, ParserConfigurationException{
        
        Download downQualis;
                
        File fileQualis = new File("qualis.xml");
        if(!fileQualis.exists())
            downQualis = new Download(new URL(URL_QUALIS)); //solicita o download do arquivo qualis.xml
        
        List<Element> regexElements = ElementXML.getElementXML(fileQualis.getAbsolutePath(), "entry");  //pega os elementos regex no qualis.xml
        
        for(Element entry : regexElements){
                                                                        // inicia o objeto REGEX com a regex do arquivo .xml, classificacao e tipo.
            Regex regex = new Regex(XMLUtils.getStringAttribute(entry, "regex"), XMLUtils.getStringAttribute(entry, "class"), XMLUtils.getStringAttribute(entry, "type"));
            
            regexs.add(regex);
            
        }
        
        fileQualis.deleteOnExit(); // programa exclusao do arquivo ao encerrar o programa
    }

//  executa a ação de carregar as informações dos curriculos de cada professor relevantes para a geração do relatório.
//  seta essas informações para o objeto curriculo, se posteriormente para o objeto Professor que conterá esse curriculo
    private void carregaCurriculos() throws SAXException, IOException, ParserConfigurationException{
        
        for(Professor prof : professores){
            System.out.println(prof.getNome().toUpperCase());
            CurriculoMining cm = new CurriculoMining("Curriculos\\"+ prof.getNome() +"\\curriculo.xml", anoInicio, anoFim, regexs);
            
            // instancia curriculo e o carrega com as informações mineradas no .xml no objeto 'cm' acima.
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
            
            prof.setCurriculo(curriculo); // seta o curriculo instanciado para o respectivo professor.
        }
    }
    
//  responsável por gerar o relatório formatado em .txt
//  realiza a soma dos valores apresentados no relatório.
    public void geraRelatorio() throws IOException{
        
        String breakLine = System.getProperty("line.separator");   //define quebra de linha corrente do sistema.
        
        int[] ttLinhaPesquisa = new int[27];    // array para os valores somados de cada coluna no relatário
        
        int[] ttProgramaPos = new int[27];      // array para os valores somados de cada linha de pesquisa no relatário. Soma total do Programa.
        
        Relatorio relatorio = new Relatorio();
        
        for(LinhaPesquisa lnPesquisa : pesquisas){
            for(Professor prof : professores){                      //formatação tabulada para o relatório
                if(lnPesquisa.getNome().toLowerCase().equals(prof.getLinhaPesquisa().toLowerCase())){
                    relatorio.escreve(prof.getNome()+"\t"+prof.getCurriculo().getTotalClassifRevista(0)+"\t"+prof.getCurriculo().getTotalClassifRevista(1)+"\t"+prof.getCurriculo().getTotalClassifRevista(2)+"\t"+prof.getCurriculo().getTotalClassifRevista(3)+"\t"+prof.getCurriculo().getTotalClassifRevista(4)+"\t"+prof.getCurriculo().getTotalClassifRevista(5)+"\t"+prof.getCurriculo().getTotalClassifRevista(6)+"\t"+prof.getCurriculo().getTotalClassifRevista(7)+"\t"+prof.getCurriculo().getTotalClassifRevista(8)
                                  +"\t"+prof.getCurriculo().getTotalClassifEvento(0)+"\t"+prof.getCurriculo().getTotalClassifEvento(1)+"\t"+prof.getCurriculo().getTotalClassifEvento(2)+"\t"+prof.getCurriculo().getTotalClassifEvento(3)+"\t"+prof.getCurriculo().getTotalClassifEvento(4)+"\t"+prof.getCurriculo().getTotalClassifEvento(5)+"\t"+prof.getCurriculo().getTotalClassifEvento(6)+"\t"+prof.getCurriculo().getTotalClassifEvento(7)+"\t"+prof.getCurriculo().getTotalClassifEvento(8)
                                  +"\t"+prof.getCurriculo().getBancasDoutorado()+"\t"+prof.getCurriculo().getBancasMestrado()+"\t"+prof.getCurriculo().getBancasPFGraduacao()
                                  +"\t"+prof.getCurriculo().getOrientaDoutorado()+"\t"+prof.getCurriculo().getOrientaMestrado()+"\t"+prof.getCurriculo().getOrientaPFGraduacao()
                                  +"\t"+prof.getCurriculo().getOrientaDrAndamento()+"\t"+prof.getCurriculo().getOrientaMestrAndamento()+"\t"+prof.getCurriculo().getOrientaPFGraduAndamento()+breakLine);
                    
                    // preenchimento do array com a soma das colunas de cada linha
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
                        // saida com o total das colunas de cada linha de pesquisa.
            relatorio.escreve("Total da linha de pesquisa '"+lnPesquisa.getNome()+"'\t");
            for(int i = 0; i < ttLinhaPesquisa.length; i++){
                
                relatorio.escreve(ttLinhaPesquisa[i]+"\t");
                ttProgramaPos[i] += ttLinhaPesquisa[i];   // guarda a soma da linha para a soma total do programa por coluna.
            }
            relatorio.escreve(breakLine);   //quebra de linha
            
            
            for(int i=0; i < ttLinhaPesquisa.length; i++)
                ttLinhaPesquisa[i] = 0;         // zera os valores de soma de cada linha de pesquisa para a proxima linha de pesquisa.
        }
        
        //  aprosenta a soma total de cada coluna em todo o programa.
        relatorio.escreve("Total Programa PosGraduação '"+nomePrograma+"'\t");
        for(int i = 0; i < ttProgramaPos.length; i++)
            relatorio.escreve(ttProgramaPos[i]+"\t");           
        
        
        relatorio.closeRelatorio();         // fecha o arquivo de relatorio .txt
    }
}
