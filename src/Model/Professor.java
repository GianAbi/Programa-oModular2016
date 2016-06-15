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
    private long cod;
    private Vector<String> linhasPesquisa;
    private Curriculo curriculo;
//    private String instituicao;
//    private String formacao;
    
//    private List<Artigo> artigos;
    
    

    public Professor(String nome, long cod){//, Curriculo curriculo, String instituicao, String graduacao, String formacao, List<Artigo> artigos) {
        this.nome = nome;
        this.cod = cod;
//        this.curriculo = curriculo;
//        this.instituicao = instituicao;
//        this.graduacao = graduacao;
//        this.formacao = formacao;
//        this.artigos = artigos;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public String getInstituicao() {
//        return instituicao;
//    }
//
//    public void setInstituicao(String instituicao) {
//        this.instituicao = instituicao;
//    }
//
//    public String getFormacao() {
//        return formacao;
//    }
//
//    public void setFormacao(String formacao) {
//        this.formacao = formacao;
//    }
//
//    public List<Artigo> getArtigos() {
//        return artigos;
//    }
//
//    public void setArtigos(List<Artigo> artigos) {
//        this.artigos = artigos;
//    }
    
    public String getCodEmString(){
        return String.valueOf(cod);
    }
    
    public long getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
    
    public Vector<String> getLinhasPesquisa() {
        return linhasPesquisa;
    }
    
    public String getUmaLinhaPesquisa(int o){
        
        return linhasPesquisa.get(o);
    }

    public void addLinhaPesquisa(String linhaPesquisa) {
        
        if(linhasPesquisa == null){
            linhasPesquisa = new Vector<>();
            linhasPesquisa.add(linhaPesquisa);
        }else
            linhasPesquisa.add(linhaPesquisa);
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
    
}
