/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Funcionario;

import java.util.List;
//import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;

import dao.FuncionarioDAO;
import javax.swing.JOptionPane;

public class GerenciaFuncionario {
    private FuncionarioDAO funcDao = new FuncionarioDAO();
        
    public void setFuncDAO(FuncionarioDAO funcDao){ this.funcDao = funcDao; }
    
    //FUNÇÃO PARA CADASTRAR Funcionarios NO BANCO DE DADOS 
    public boolean cadastrarFuncionarios(String nome,String email,String login,String senha){         
         boolean var = false;
         try{
                if(nome.equals("") && email.equals("") && login.equals("") && senha.equals("")){
                    //JOptionPane.showMessageDialog(null, "Erro ao Cadastrar: Campos Vazios");
                    throw new Exception();
                }else{
                   Funcionario func = new Funcionario();

                   //Pegando nome do campo text e colocando na classe
                   func.setNomeFuncionario(nome);
                   //Pegando e-mail do campo text e colocando na classe
                   func.setEmailFuncionario(email);
                   //Pegando login do campo combo Box e colocando na classe
                   func.setLoginFuncionario(login);
                   //Pegando senha do campo text e colocando na classe
                   func.setSenhaFuncionario(senha);
                   //chamando função para cadastrar Funcionario
                   funcDao.cadastrarFuncionario(func);

                   //JOptionPane.showMessageDialog(null, "Funcionario cadastrado com Sucesso!");
                   var = true;     
                }
        }catch(Exception erro){
            //JOptionPane.showMessageDialog(null, "Nao eh possivel cadastrar o funcionario"); 
            throw new RuntimeException("Erro ao Cadastrar: Campos Vazios");
        }
    return var;        
    }
     //FUNÇÃO PARA EDITAR Funcionarios NO BANCO DE DADOS 
    public boolean editarFuncionario(int id,String idEditar,String nome,String email,String login,String senha){
        //Edição do Cadastro
    	boolean op = false;
        Funcionario func = new Funcionario();
        try {
	        if(Integer.parseInt(idEditar) == 0){
	            //JOptionPane.showMessageDialog(null, "Nenhum Funcionario Selecionado Na Tabela");
	            throw new RuntimeException("Nenhum Funcionario Selecionado Na Tabela");
	        }else{
	            //Verificação se o login foi alterado
	            if(id == Integer.parseInt(idEditar)){
	                if(nome.equals("") && email.equals("") && login.equals("") && senha.equals("")) {
	                    //JOptionPane.showMessageDialog(null, "Erro ao Editar: Campos Vazios");
	                    throw new Exception();
	                }else{
                    
                        //Se o cpf não foi alterado permite a edição direta do Funcionario
                        //Pegando id do campo text e colocando na classe
                        func.setIdFuncionario(Integer.parseInt(idEditar));
                        //Pegando nome do campo text e colocando na classe
                        func.setNomeFuncionario(nome);
                        //Pegando cpf do campo text e colocando na classe
                        func.setEmailFuncionario(email);
                        //Pegando endereco do campo combo Box e colocando na classe
                        func.setLoginFuncionario(login);
                        //Pegando telefone do campo text e colocando na classe
                        func.setSenhaFuncionario(senha);
                        //Chamando função para editar servidor
                        funcDao.editarFuncionario(func);
                        //JOptionPane.showMessageDialog(null, "Funcionario editado com Sucesso!");
                        op = true;
	                }
	            }
	        }
        } catch (Exception e) {
        	throw new RuntimeException("Erro ao Editar: Campos Vazios");
        }
        return op;
    }
    

    //FUNÇÃO PARA EXCLUIR Funcionarios NO BANCO DE DADOS 
    public boolean excluirFuncionario(int id,String idExcluir){
    	boolean op = false;
    	try{
	        if(id != 0 && Integer.parseInt(idExcluir) !=  0){
	            if(id == Integer.parseInt(idExcluir)){
                
                    Funcionario func = new Funcionario();

                    func.setIdFuncionario(Integer.parseInt(idExcluir));    

                    funcDao.excluirFuncionario(func);
                    
                    //JOptionPane.showMessageDialog(null, "Funcionario excluido com Sucesso!");
                    op = true;
	            }else{
	                //JOptionPane.showMessageDialog(null, "Funcionario não existe");
	                throw new Exception();
	                
	            }
	        }else{
	            JOptionPane.showMessageDialog(null, "Nenhum Funcionario Selecionado Na Tabela");
	            throw new RuntimeException("Nenhum Funcionario Selecionado Na Tabela");

	        }

        }catch(Exception erro){
        	throw new RuntimeException("Funcionario não existe");
        }
    	return op;
    }
    
    public List<Funcionario> pesquisarFuncionario(String login,String nome ) throws RuntimeException{
        List<Funcionario> lista = funcDao.pesquisarFuncionario(login, nome);
        if(lista.isEmpty()){throw new RuntimeException("Nenhum funcionário encontrado");}
        else return lista;
    }
    
}
