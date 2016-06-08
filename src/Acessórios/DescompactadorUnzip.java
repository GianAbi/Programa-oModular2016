package Acessórios;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystemNotFoundException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipError;
import java.util.zip.ZipInputStream;
import javax.annotation.processing.FilerException;

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
    
//    public static void main (String args[]){
//        
//        String zipFilePath = "c:\\Users\\HP\\Downloads\\4284646.zip";
//         
//        String destDir = "c:\\Users\\HP\\Desktop\\output";
//         
//        DescompactadorUnzip unzip = new DescompactadorUnzip(zipFilePath, destDir);
//    }
    
    public DescompactadorUnzip(String zipFile, String destDir) {
        
        File dir = new File(destDir);
        
        if(!dir.exists())
            dir.mkdirs();
        
        FileInputStream fis;
        
        byte[] buffer = new byte[1024];
        try {
            
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                
//                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
//                new File(newFile.getParent()).mkdirs();
                
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
                
                CurriculoMining curriculo = new CurriculoMining(destDir + File.separator + fileName);
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipError z){
            System.err.println("Erro ao carregar arquivo .ZIP");
        }
         
    }
    
}
