package Acessórios;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael
 */
public class DescompactadorUnzip {
    
    public DescompactadorUnzip(String zipFile, String folderProf) {
        
        File dir = new File("Curriculos"+ File.separator +folderProf);
        
        if(!dir.exists())
            dir.mkdirs();
        
        byte[] buffer = new byte[2048];
        try {
            
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                
                String fileName = ze.getName();
                File newFile = new File(dir.getPath() + File.separator + fileName);
                
                
                //create directories for sub directories in zip
                //new File(newFile.getPath()).mkdir();
                
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
                
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            
            File file = new File(zipFile);
            file.deleteOnExit();
        } catch (IOException e) {
            System.err.println("Erro ao descompactar arquivo: " + zipFile);
        }
         
    }
    
}
