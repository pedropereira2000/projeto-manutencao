/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Quarto {

    private int idQuarto;
    private int numQuarto;
    private int numCamasQuarto;
    private String tipoQuarto;
    private int qtdBanheirosQuarto;
    private String descricaoQuarto;
    private int contatoQuarto;
    /*Chamando Classe Andar*/
    private Andar andar;

    public Quarto() {
        idQuarto = 0;
        numQuarto = 0;
        numCamasQuarto = 0;
        tipoQuarto = "";
        qtdBanheirosQuarto = 0;
        descricaoQuarto = "";
        contatoQuarto = 0;
        andar = new Andar();
    }

    public Quarto(int idQuarto, int numQuarto, int numCamasQuarto, String tipoQuarto, int qtdBanheirosQuarto, String descricaoQuarto, int contatoQuarto, Andar andar) {
        this.idQuarto = idQuarto;
        this.numQuarto = numQuarto;
        this.numCamasQuarto = numCamasQuarto;
        this.tipoQuarto = tipoQuarto;
        this.qtdBanheirosQuarto = qtdBanheirosQuarto;
        this.descricaoQuarto = descricaoQuarto;
        this.contatoQuarto = contatoQuarto;
        this.andar = andar;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public int getNumQuarto() {
        return numQuarto;
    }

    public void setNumQuarto(int numQuarto) {
        this.numQuarto = numQuarto;
    }

    public int getNumCamasQuarto() {
        return numCamasQuarto;
    }

    public void setNumCamasQuarto(int numCamasQuarto) {
        this.numCamasQuarto = numCamasQuarto;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public int getQtdBanheirosQuarto() {
        return qtdBanheirosQuarto;
    }

    public void setQtdBanheirosQuarto(int qtdBanheirosQuarto) {
        this.qtdBanheirosQuarto = qtdBanheirosQuarto;
    }

    public String getDescricaoQuarto() {
        return descricaoQuarto;
    }

    public void setDescricaoQuarto(String descricaoQuarto) {
        this.descricaoQuarto = descricaoQuarto;
    }

    public int getContatoQuarto() {
        return contatoQuarto;
    }

    public void setContatoQuarto(int contatoQuarto) {
        this.contatoQuarto = contatoQuarto;
    }

    public Andar getAndar() {
        return andar;
    }

    public void setAndar(Andar andar) {
        this.andar = andar;
    }

   

}
