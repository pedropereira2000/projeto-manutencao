/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.FuncionarioDAO;

public class GerenciaLogin {

    private FuncionarioDAO funcDAO = new FuncionarioDAO();

    public void setLoginConn(FuncionarioDAO funcDAO) {
        this.funcDAO = funcDAO;
    }
  
    public int VerificaAcesso(String login, String senha) {
        
        int idLogin = 0;
        if (login != "") {
            if (senha != "")idLogin = funcDAO.validaLogin(login, senha);
            else {
                throw new NullPointerException("Verifique o campo de senha pois está vazio");
            }
        } else {
            throw new NullPointerException("Verifique o campo de login pois está vazio");
        }
        return idLogin;
    }

}
