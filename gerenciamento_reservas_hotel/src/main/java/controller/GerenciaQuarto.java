/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Andar;
import model.Quarto;

import java.util.ArrayList;

import dao.AndarDAO;
import dao.QuartoDAO;

public class GerenciaQuarto {

    //Criação do Objeto QuartoDao do pacote DAO
    private QuartoDAO quartoDAO = new QuartoDAO();
    private AndarDAO andarDAO = new AndarDAO();
    
    public void setQuartoDAO(QuartoDAO quartoDAO){
        this.quartoDAO = quartoDAO;
    }
    
    public void setAndarDAO(AndarDAO andarDAO){
        this.andarDAO = andarDAO;
    }

    //Criação do Método criação de quantidade de Quarto para chamar no criarNewQuarto na classe GerenciAndar
    public boolean criarNewQuarto(Andar andar, int qtdQuarto) {
        //inserindo contador apartir de 0
        //int cont = 0;
        for (int i = 1; i <= qtdQuarto; i++) {
            //Passando As informações de todos os campos para cadastro do Quarto
            Quarto quarto = new Quarto(1, (andar.getNumAndar() * 100) + i, 2, "Básico", 1, "Quarto básico possui arcondicionado, frigobar, tv com canais por assinatura", (andar.getNumAndar() * 100) + i, andar);
            //Chamando o método cadastrar Quarto da DAO utilizando o Insert do MySQL
            quartoDAO.cadastarQuarto(quarto);

        }

        return true;
    }
    
    public boolean editarQuarto(Quarto quarto, String tipo, int qtdCamas, int qtdBanheiros, String desc) {        
        if (quarto.getTipoQuarto().equals(tipo)&&quarto.getNumCamasQuarto()==qtdCamas&&quarto.getQtdBanheirosQuarto()==qtdBanheiros&&quarto.getDescricaoQuarto().equals(desc)) {
            return false;
        } else {
            quartoDAO.editarQuarto(quarto);
            return true;            
        }
    }
    
    public boolean excluirQuarto(int idQuarto){ 
        if (idQuarto > 0 ){
            //Chamando Objeto
            //Quarto quarto = new Quarto();
            //Passando a informação do Andar que eu quero apagar
           // quarto.setIdQuarto(idQuarto);
            //Exclusão Quarto
            quartoDAO.excluirQuarto(quartoDAO.buscarIdQuarto(idQuarto));
            
        }else{
            throw new RuntimeException("Erro Id Não existente");
        }
        
        return true;
    }
    
    public Quarto buscarQuarto(int numQuarto) {
        Quarto quarto = new Quarto();
        if (numQuarto > 0) {
            quarto = quartoDAO.buscarInformacaoNumeroQuarto(numQuarto);
            return quarto;
        } else {
            throw new RuntimeException("ERRO!! Porfavor insira um número de quarto válido para ser buscado");
        }
    }    
    
    public Andar buscarAndar(int numAndar){
        Andar andar = new Andar();
        if(numAndar > 0){
            andar = andarDAO.buscarInformacaoNumeroAndar(numAndar);
            return andar;
        } else {
            throw new RuntimeException("ERRO!! Porfavor insira um número de andar válido para ser buscado");
        }
        
    }
    
    public ArrayList<Quarto> buscTodosQuartos(){
        ArrayList<Quarto> quarto = quartoDAO.buscarTodosQuarto();
        return quarto;
    }
    
    //Método especifico para exclusão de um andar inteiro
    public boolean excluirQuartoAndar(Andar andar) {
        quartoDAO.excluirQuartoAndar(andar);
        return true;
    }

    //Método para pegar quartos ocupados
    public ArrayList<Quarto> buscTodosQuartosOcupados() {
        ArrayList<Quarto> quarto = quartoDAO.buscarTodosQuartoOcupados();
        return quarto;
    }
    
   //Método para pegar quartos Livres
    public ArrayList<Quarto> buscTodosQuartosLivres() {
        ArrayList<Quarto> quarto = quartoDAO.buscarTodosQuartoVagos();
        return quarto;
    }
}