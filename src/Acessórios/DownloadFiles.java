package Acessórios;


import Acessórios.DescompactadorUnzip;
import Model.Professor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
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
public class DownloadFiles {
    
    public DownloadFiles(String programaPos, List<Professor> professores) throws MalformedURLException, IOException{
        
        
        for(Professor prof : professores){
        
            URL oURL;
            HttpURLConnection httpConn;
                                                  // tem que ser passado por parâmetro
            oURL = new URL("https://s3.amazonaws.com/posgraduacao/" + programaPos + "/" + prof.getCod() +".zip");
            httpConn = (HttpURLConnection)oURL.openConnection();

            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK){

                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                        disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = oURL.toString().substring(oURL.toString().lastIndexOf("/") + 1,
                            oURL.toString().length());
                }

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + httpConn.getContentLength());
                System.out.println("fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = "c:\\Users\\HP\\Desktop\\output" + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded");

                DescompactadorUnzip dscpUnzip = new DescompactadorUnzip(saveFilePath, saveFilePath.substring(0, 26));
            }else{
                System.out.println("Erro ao tentar fazer donwload de arquivo do programa" + programaPos+"."
                                       + "\n Server replied HTTP code: " + httpConn.getResponseCode());
            }
            httpConn.disconnect();
        }
    }
}
