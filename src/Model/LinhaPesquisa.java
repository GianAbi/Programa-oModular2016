package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael
 */
public class LinhaPesquisa {
    
    private String nome;
    private String profNome;
     
     
    public LinhaPesquisa(String nome) {
        this.nome = nome;
        this.profNome = "";
    }

    public String getNome() {
        return nome;
    }

    public String getProfNome() {
        return profNome;
    }
    
    public void setProfNome(String profNome){
        this.profNome = profNome;
    }
    
}
