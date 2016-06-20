/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Rafael
 */
public class Regex {

    private String regex;
    private String classificacao;
    private String type;
    
    
    public Regex(String regex, String classificacao, String type) {
        this.regex = regex;
        this.classificacao = classificacao;
        this.type = type;
    }

    public String getRegex() {
        return regex;
    }

    public String getClassificacao() {
        return classificacao;
    }
    
    public String getType() {
        return type;
    }
}
