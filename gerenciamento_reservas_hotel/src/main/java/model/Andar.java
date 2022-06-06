/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Andar {

    private int idAndar;
    private int numAndar;
    /*Chamando Classe Funcionario*/
    private Funcionario funcionario; //Lembrar que tavez preciso alterar
    
    
    public Andar() {
        idAndar = 0;
        numAndar = 0;
        funcionario = new Funcionario();
        
    }

    public Andar(int idAndar, int numAndar, Funcionario funcionario ) {
        this.idAndar = idAndar;
        this.numAndar = numAndar;
        this.funcionario = funcionario;
        
    }

    public int getIdAndar() {
        return idAndar;
    }

    public void setIdAndar(int idAndar) {
        this.idAndar = idAndar;
    }

    public int getNumAndar() {
        return numAndar;
    }

    public void setNumAndar(int numAndar) {
        this.numAndar = numAndar;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

  
    
    
}
