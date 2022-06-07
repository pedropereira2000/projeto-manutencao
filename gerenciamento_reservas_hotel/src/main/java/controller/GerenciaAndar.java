/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Andar;
import model.Funcionario;
import java.util.ArrayList;
import dao.AndarDAO;

public class GerenciaAndar {

    private AndarDAO andarDAO = new AndarDAO();
    private Funcionario func = new Funcionario();
    private GerenciaQuarto gerecQuarto = new GerenciaQuarto();

    public void setAndarDAO(AndarDAO andarDAO) {
        this.andarDAO = andarDAO;
    }

    public void setQuartoDAO(GerenciaQuarto gerecQuarto) {
        this.gerecQuarto = gerecQuarto;
    }

    public void setFuncionario(Funcionario func) {
        this.func = func;
    }

    //Criação de Métodos
    public boolean criarNewAndar(int qtdAndar, int qtdQuarto) {
        //Inserindo contador
        int cont = 1;
        //Verificação Ultimo Andar cadastrado
        if (qtdAndar > 0 && qtdQuarto > 0) {
            cont = andarDAO.buscarUltimoAndar();
            //Perguntado se a algum andar cadatrado se contador for maior que zero já existe um andar cadastrado
            if (cont > 0) {
                //Laço de repetição parapegar o para continuar o cadastro de andar apartir de andares existentes
                for (int i = 1; i <= qtdAndar; i++) {
                    cont++;
                    //Passando as informações para o cadastro do Andar
                    //Exemplo Id do Andar, contador do andar e idFuncionario
                    Andar andar = new Andar(1, cont, func);
                    //Chamando o método cadastrar Andar da DAO utilizando o Insert do MySQL
                    andarDAO.cadastarAndar(andar);
                    //Atualização o Id do objeto Andar cadastrado anteriormente
                    andar.setIdAndar(andarDAO.atualizarUltimoAndar());
                    //cadastrarQuartos
                    gerecQuarto.criarNewQuarto(andar, qtdQuarto);
                }
                //Cadastrando Primeiro andar apartir do momento não existir nenhum
            } else {
                //Mesma coisa do if de cima mas dó pondo de vista que não tinha nada cadastrado no Banco
                for (int i = 1; i <= qtdAndar; i++) {
                    Andar andar = new Andar(1, i, func);
                    andarDAO.cadastarAndar(andar);
                    andar.setIdAndar(andarDAO.atualizarUltimoAndar());
                    gerecQuarto.criarNewQuarto(andar, qtdQuarto);
                }
            }
            return true;
        } else {
            throw new RuntimeException("ERRO!! Porfavor insira um número de andares e quarto válido para ser cadastrado");
        }
    }

    public boolean excluirAndar(int numAndar) {
        if (numAndar > 0) {
            //Chamando Objeto
            //Andar andar = new Andar();
            //Passando a informação do Andar que eu quero apagar
            //andar.setIdAndar(numAndar);
            //Exclusão Quarto
            gerecQuarto.excluirQuartoAndar(andarDAO.buscarInformacaoNumeroAndar(numAndar));
            //Exclusão de Andar
            andarDAO.excluirAndar(andarDAO.buscarInformacaoNumeroAndar(numAndar));

        } else {
            throw new RuntimeException("ERRO!! Porfavor insira um número de andar válido para ser excluido");
        }

        return true;
    }

    public Andar buscarAndar(int numAndar) {
        Andar andar = new Andar();
        if (numAndar > 0) {
            andar = andarDAO.buscarInformacaoNumeroAndar(numAndar);
            return andar;
        } else {
            throw new RuntimeException("ERRO!! Porfavor insira um número de andar válido para ser cadastrado");
        }
    }

    public ArrayList<Andar> buscTodosAndares() {
        ArrayList<Andar> and = andarDAO.buscarTodosAndar();
        return and;
    }

}
