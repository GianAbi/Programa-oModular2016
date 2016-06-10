package Acessórios;


import Acessórios.DescompactadorUnzip;
import Model.Professor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.text.Document;

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
    
    public Download(URL oURL) throws MalformedURLException, IOException{
        
        HttpURLConnection httpConn = (HttpURLConnection)oURL.openConnection();                

        if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK){
            
            BufferedInputStream bis = new BufferedInputStream(httpConn.getInputStream());
            FileOutputStream fis = new FileOutputStream("contents.xml");
            byte[] buffer = new byte[1024];
            int count=0;
            while((count = bis.read(buffer,0,1024)) != -1)
            {
                fis.write(buffer, 0, count);
            }
            fis.close();
            bis.close();
        }else
            System.out.println("Erro ao tentar se conectar com a url " + oURL.toString() +".\n Server replied HTTP code: " + httpConn.getResponseCode());
        
        httpConn.disconnect();
    }
    
    public Download(String programaPos, String codProf) throws MalformedURLException, IOException{
        
        URL oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/" + codProf + ".zip");
        HttpURLConnection httpConn = (HttpURLConnection)oURL.openConnection();
        
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
    }
}
