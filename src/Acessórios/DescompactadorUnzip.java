package Acessórios;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipError;
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
    
//    public static void main (String args[]){
//        
//        String zipFilePath = "c:\\Users\\HP\\Downloads\\4284646.zip";
//         
//        String destDir = "c:\\Users\\HP\\Desktop\\output";
//         
//        DescompactadorUnzip unzip = new DescompactadorUnzip(zipFilePath, destDir);
//    }
    
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
                
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                //new File(newFile.getPath()).mkdir();
                
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
                
                //CurriculoMining curriculo = new CurriculoMining(destDir);// + File.separator + fileName);
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
