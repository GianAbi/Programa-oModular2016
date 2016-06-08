/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Acessórios.DownloadFiles;
import Acessórios.DownloadFiles;
import Model.ProgramaPosGraduacao;
import java.io.IOException;

/**
 *
 * @author Rafael
 */
public class Start {
    
    public static void main(String args[]) throws IOException{
        
//        if (args.length < 3){
//            System.out.println("Parâmetro insuficiente");
//            return;
//        }
        
        System.out.println(args.length);
        
        ProgramaPosGraduacao ppg = new ProgramaPosGraduacao(args[0]);
        DownloadFiles download = new DownloadFiles(args[0], ppg.listaProfessores());
        
        return;
        
    }
}
