package model;


import java.util.List;
import java.util.Vector;

/*

 */

/**
 *Classe destinada a representar as informações de um professor.
 * @author Rafael
 */
public class Professor {
    
    private String nome;
    private String cod;
    private String linhasPesquisa;
    private Curriculo curriculo;    

    public Professor(String nome, String cod){
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
