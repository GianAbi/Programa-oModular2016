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
public class Artigo {
    
    private String classificacao;
    private Boolean revista;
    private Boolean conferencia;
    private int ano;
    
    public Artigo(int ano){
        this.classificacao = null;
        revista = false;
        conferencia = false;
        this.ano = ano;
    }

    public Boolean getRevista() {
        return revista;
    }

    public void setRevista(Boolean revista) {
        this.revista = revista;
    }

    public Boolean getConferencia() {
        return conferencia;
    }

    public void setConferencia(Boolean conferencia) {
        this.conferencia = conferencia;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getAno() {
        return ano;
    }
}
