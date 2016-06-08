package Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 *  Classe que detém informações de Programa de Gradução.
 * @author Rafael
 */
public class ProgramaPosGraduacao {
    
    private String nomeLinha;
    List<Professor> professores;
    
    List<Artigo> artigos;
    List<Pesquisa> pesquisas;
    Date ultimaAval;
    int classificacao;
    
    public ProgramaPosGraduacao(String programaPos) throws MalformedURLException, IOException{
        
//        URL oURL;
//        HttpURLConnection httpConn;
//                                              // tem que ser passado por parâmetro
//        oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/contents.xml");
//        httpConn = (HttpURLConnection)oURL.openConnection();
//        
//        System.out.println(httpConn.getContentEncoding());
        
        URL oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/contents.xml");
        HttpURLConnection httpConn = (HttpURLConnection)oURL.openConnection();
        
        if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK){
            
            String nome = null;
            String cod = null;
            String linhaPesquisa = null;
            String inputLine =  null;
            
            professores = new ArrayList<>();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(oURL.openStream()));
            while ((inputLine = br.readLine()) != null){
                if(inputLine.startsWith("<?") || inputLine.equals("<programa>") 
                   || inputLine.equals("\t</linha>") || inputLine.equals("</programa>"))
                    continue;

                if(inputLine.startsWith("\t<linha")){
                    inputLine.trim();
                    linhaPesquisa = inputLine.substring(inputLine.indexOf('"')+1, inputLine.indexOf(">")-1);
                    continue;
                }

                inputLine.trim();
                nome = inputLine.substring(inputLine.indexOf("nome")+6, inputLine.indexOf("/")-1);
                cod = inputLine.substring(inputLine.indexOf("codigo")+8, inputLine.indexOf("nome")-2);
                
                professores.add(new Professor(nome, cod, linhaPesquisa));

                System.out.println(inputLine);
            }

            for(Professor prof : professores)
                System.out.println(prof.getNome() + "   " + prof.getCod());

            br.close();
        }else
            System.out.println("Erro ao tentar se conectar com a url " + oURL.toString() +"."
                                   + "\n Server replied HTTP code: " + httpConn.getResponseCode());
    }
    
    public List<Professor> listaProfessores(){
        return professores;
    }
    
}
