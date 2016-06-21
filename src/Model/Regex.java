/*

 */
package model;

/**
 *Classe destinada a representar as informações do arquivo qualis.xml que contém as Regexs e classificações.
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
