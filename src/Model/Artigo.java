package model;

/*

 */

/**
 *    Classe destinada a representar as informações de um artigo.
 * @author Rafael
 */
public class Artigo {
    
    private String classificacao;
    private String revista;
    private String evento;
    private int ano;
    
    public Artigo(int ano){
        this.classificacao = "";
        revista = "";
        evento = "";
        this.ano = ano;
    }

    public String getRevista() {
        return revista;
    }

    public void setRevista(String revista) {
        this.revista = revista;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String conferencia) {
        this.evento = conferencia;
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
