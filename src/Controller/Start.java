/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import acessórios.Download;
import acessórios.Download;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *  Controller para inicar a aplicação de geração do relatório.
 * @author Rafael
 */
public class Start {
    
    public static void main(String args[]) throws IOException, MalformedURLException, SAXException, ParserConfigurationException{
        
        if (args.length < 3){
            System.out.println("Parâmetro insuficiente");
            return;
        }

        ProgramaPosGraduacao ppg = new ProgramaPosGraduacao(args[0], args[1], args[2]);
        ppg.geraRelatorio();

        
    }
}
