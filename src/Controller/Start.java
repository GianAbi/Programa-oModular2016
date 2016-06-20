/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Acessórios.Download;
import Acessórios.Download;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Rafael
 */
public class Start {
    
    public static void main(String args[]) throws IOException, MalformedURLException, SAXException, ParserConfigurationException{
        
        if (args.length < 3){
            System.out.println("Parâmetro insuficiente");
            return;
        }
        
        System.out.println(args.length);
        
        long start = System.currentTimeMillis();
        ProgramaPosGraduacao ppg = new ProgramaPosGraduacao(args[0], args[1], args[2]);
        ppg.geraRelatorio();
        long end = System.currentTimeMillis();
        
        System.out.println((end-start)/1000 + "seg");
        //Download download = new Download(args[0], ppg.listaProfessores());
        
        //return;
        
    }
}
