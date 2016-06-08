/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Rafael
 */
public class CurriculoMining {
    
    
    
    public CurriculoMining(String pathCurriculo) throws FileNotFoundException, IOException{
        
        File xmlFile = new File(pathCurriculo);
        Reader fileReader = new FileReader(xmlFile);
        BufferedReader br = new BufferedReader(fileReader);
        
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        
        while(line != null){
                sb.append(line).append("\n");
                line = br.readLine();
        }
        
        String xmlAsString = sb.toString();
        System.out.println(xmlAsString);
        
        br.close();
    }
}
