package Acessórios;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael
 */
public class Download {
    
    private URL oURL;
    
    private HttpURLConnection httpConn;
    
    public Download(URL oURL){
        
        // identifica o nome do arquivo a ser gerado para download.
        String nameFile = oURL.toString().substring(oURL.toString().lastIndexOf("/")+1, oURL.toString().length());
            
        try{
            this.oURL = oURL;
            
            httpConn = (HttpURLConnection) this.oURL.openConnection();                

            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK){

                BufferedInputStream bis = new BufferedInputStream(httpConn.getInputStream());
                FileOutputStream fis = new FileOutputStream(nameFile);
                byte[] buffer = new byte[1024];
                int count;
                while((count = bis.read(buffer,0,1024)) != -1)
                    fis.write(buffer, 0, count);
                
                fis.close();
                bis.close();
            }else
                System.out.println("Erro ao tentar se conectar com a url " + oURL.toString() +".\n Server replied HTTP code: " + httpConn.getResponseCode());

            httpConn.disconnect();
        } catch(MalformedURLException mre) {
            System.out.println("Problemas com a URL de download " + nameFile + ".xml");
        } catch (IOException ie){
            System.out.println("Erro ao tentar baixar o arquivo " + nameFile + ".xml");
        }
    }
    
    public Download(String programaPos, String codProf){
        
        try{
            this.oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/" + codProf + ".zip");
            
            httpConn = (HttpURLConnection)oURL.openConnection();

            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = codProf+".zip";

                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1)
                    outputStream.write(buffer, 0, bytesRead);

                outputStream.close();
                inputStream.close();

            }else
                System.out.println("Erro ao tentar fazer donwload de arquivo do programa" + programaPos+".\n Server replied HTTP code: " + httpConn.getResponseCode());

            httpConn.disconnect();
        }catch(MalformedURLException mre) {
            System.out.println("Problemas com a URL de download " + codProf + ".zip");
        } catch (IOException ie){
            System.out.println("Erro ao tentar baixar o arquivo " + codProf + ".zip");
        }
    }
}
