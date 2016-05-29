
import java.util.List;

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
    private int cod;
    private Curriculo curriculo;
    private String instituicao;
    private String graduacao;
    private String formacao;
    int test;
    
    private List<Artigo> artigos;
    
    

    public Professor(String nome, int cod, Curriculo curriculo, String instituicao, String graduacao, String formacao, List<Artigo> artigos) {
        this.nome = nome;
        this.cod = cod;
        this.curriculo = curriculo;
        this.instituicao = instituicao;
        this.graduacao = graduacao;
        this.formacao = formacao;
        this.artigos = artigos;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }
    
    
}
