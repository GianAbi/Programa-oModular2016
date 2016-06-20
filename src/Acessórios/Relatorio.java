/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rafael
 */
public class Relatorio {
    
    private File file;
    private FileWriter fileWritter;
    private BufferedWriter br;
    
    public Relatorio() throws IOException{
        
        file = new File("Relatorio_ProgramaPos.txt");
        if(!file.exists())
            file.createNewFile();
        
        fileWritter = new FileWriter(file.getName(), false);
        br = new BufferedWriter(fileWritter);
    }
    
    public void escreve(String toBeWritten) throws IOException{
        
        br.write(toBeWritten);
//        br.write("\n");
    }
   
    public void closeRelatorio() throws IOException{
        br.close();
    }
}
