package Model;

import java.util.ArrayList;
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
public class Curriculo {
    
    private List<Artigo> artigos;
    private int bancasDoutorado;
    private int bancasMestrado;
    private int bancasPFGraduacao;
    private int orientaDoutorado;
    private int orientaMestrado;
    private int orientaPFGraduacao;
    private int orientaDrAndamento;
    private int orientaMestrAndamento;
    private int orientaPFGraduAndamento;
    
    public Curriculo(){
        this.artigos = null;
        this.bancasDoutorado = 0;
        this.bancasMestrado = 0;
        this.bancasPFGraduacao = 0;
        this.orientaDoutorado = 0;
        this.orientaMestrado = 0;
        this.orientaPFGraduacao = 0;
        this.orientaDrAndamento = 0;
        this.orientaMestrAndamento = 0;
        this.orientaPFGraduAndamento =0;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        if(this.artigos == null){
            artigos = new ArrayList<>();
            artigos.addAll(artigos);
        }
        this.artigos.addAll(artigos);
    }

    public int getBancasDoutorado() {
        return bancasDoutorado;
    }

    public void setBancasDoutorado(int bancasDoutorado) {
        this.bancasDoutorado = bancasDoutorado;
    }

    public int getBancasMestrado() {
        return bancasMestrado;
    }

    public void setBancasMestrado(int bancasMestrado) {
        this.bancasMestrado = bancasMestrado;
    }

    public int getBancasPFGraduacao() {
        return bancasPFGraduacao;
    }

    public void setBancasPFGraduacao(int bancasPFGraduacao) {
        this.bancasPFGraduacao = bancasPFGraduacao;
    }

    public int getOrientaDoutorado() {
        return orientaDoutorado;
    }

    public void setOrientaDoutorado(int orientaDoutorado) {
        this.orientaDoutorado = orientaDoutorado;
    }

    public int getOrientaMestrado() {
        return orientaMestrado;
    }

    public void setOrientaMestrado(int orientaMestrado) {
        this.orientaMestrado = orientaMestrado;
    }

    public int getOrientaPFGraduacao() {
        return orientaPFGraduacao;
    }

    public void setOrientaPFGraduacao(int orientaPFGraduacao) {
        this.orientaPFGraduacao = orientaPFGraduacao;
    }

    public int getOrientaDrAndamento() {
        return orientaDrAndamento;
    }

    public void setOrientaDrAndamento(int orientaDrAndamento) {
        this.orientaDrAndamento = orientaDrAndamento;
    }

    public int getOrientaMestrAndamento() {
        return orientaMestrAndamento;
    }

    public void setOrientaMestrAndamento(int orientaMestrAndamento) {
        this.orientaMestrAndamento = orientaMestrAndamento;
    }

    public int getOrientaPFGraduAndamento() {
        return orientaPFGraduAndamento;
    }

    public void setOrientaPFGraduAndamento(int orientaPFGraduAndamento) {
        this.orientaPFGraduAndamento = orientaPFGraduAndamento;
    }
    
    
}
