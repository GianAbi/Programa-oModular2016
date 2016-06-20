package Model;


import java.util.List;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael
 */
public class Professor {
    
    private String nome;
    private String cod;
    private String linhasPesquisa;
    private Curriculo curriculo;    

    public Professor(String nome, String cod){//, Curriculo curriculo, String instituicao, String graduacao, String formacao, List<Artigo> artigos) {
        this.nome = nome;
        this.cod = cod;
        this.curriculo = null;
        this.linhasPesquisa = "";
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCod() {
        return cod;
    }
    
    public String getLinhaPesquisa() {
        return linhasPesquisa;
    }

    public void setLinhaPesquisa(String linhaPesquisa) {
        
        linhasPesquisa=linhaPesquisa;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
    
}
