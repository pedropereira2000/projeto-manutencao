/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Andar;
import model.Funcionario;

public class AndarDAO {
    private FuncionarioDAO funcDAO = new FuncionarioDAO();
    public Connection conectar;

    public void setFuncDAO(FuncionarioDAO funcDAO) {
    	this.funcDAO =funcDAO;
    }
    
    public AndarDAO() {
        this.conectar = new ConnectionFactory().conectar();
    }
    
    public void setConnection(){
        this.conectar = new ConnectionFactory().conectar("jdbc:mysql://localhost:3306/mydb-tests?useTimezone=true&serverTimezone=UTC","root","3621");
    }

    //Cadastrar
    public boolean cadastarAndar(Andar andar) {
        PreparedStatement inserir = null;
        try {
            //Preparando Query
            inserir = conectar.prepareStatement("INSERT INTO Andar (numAndar,Funcionario_idFuncionario)VALUES(?,?)");
            //Passando Parâmetros para cadastro  
            inserir.setInt(1, andar.getNumAndar());
            inserir.setInt(2, andar.getFuncionario().getIdFuncionario());
            //Execução Query
            inserir.execute();
            if(inserir.getUpdateCount()==0)throw new SQLException();
            //Encerramento da Query
            inserir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro no Cadastro do andar verificar para encontar o erro");
        }
    }

    //Editar Andar
    /*public boolean editarAndar(Andar andar) {
        PreparedStatement editar = null;
        try {
            //Preparando Query para Atualização do Cadastro
            editar = conectar.prepareStatement("UPDATE ANDAR SET numAndar = ?, Funcionario_idFuncionario = ? WHERE idAndar = ?");
            //Passando Parâmetros para editar cadastro
            editar.setInt(1, andar.getNumAndar());
            editar.setInt(2, andar.getFuncionario().getIdFuncionario());
            //Execução Query
            editar.execute();
            //Verificação de linhas afetadas
            if (editar.executeUpdate() < 1) {
                throw new RuntimeException("Andar não existe");
            } else {
                //Encerramento Query
                editar.close();
                return true;
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro em Alterar o Cadastro de Andar");
        }
    }*/

    //Excluir Andar
    public boolean excluirAndar(Andar andar) {
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM Andar WHERE idAndar = ?");
            //Passando Parâmetros exclusão de cadastro
            excluir.setInt(1, andar.getIdAndar());
            //Execução Query
            excluir.execute();
            if(excluir.getUpdateCount()==0)throw new SQLException();
            //Encerramento Query
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na exclusão");
        }
    }

    //Verificar se existe um Amdar com essa informação passada
    public Andar buscarInformacaoNumeroAndar(int numAndar) {
        Andar andar = new Andar();
        try {
            //Query para Pesquisa
            String selectSql = ("SELECT * FROM Andar WHERE numAndar = " + numAndar);
            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
            //Executando Query
            ResultSet rs = Stmt.executeQuery();
            rs.next();
            //Pegando resposta
            andar.setIdAndar(rs.getInt("idAndar"));
            andar.setNumAndar(rs.getInt("numAndar"));
            //Precisa de integração com o funcionario
            // andar.setFuncionario(rs.getInt(3));
            andar.setFuncionario(funcDAO.getFuncionarios(rs.getInt("Funcionario_idFuncionario")));
            
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca do Andar");
        }
        return andar;
    }
    //Verifica o Ultimo Andar Cadastrado

    public int buscarUltimoAndar() {
        int cont = 0;
        try {
            //Query para Pesquisa
            String selectSql = ("SELECT numAndar FROM Andar ORDER BY numAndar DESC LIMIT 1");
            PreparedStatement stmt = conectar.prepareStatement(selectSql);
            //Executando Query
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Pegando resposta
                cont = (rs.getInt(1));
            } else {
                cont = 0;
            }
            

        } catch (SQLException erro) {
            throw new RuntimeException("Erro não há andares cadastrados");
        }
        return cont;
    }

    //
    public int atualizarUltimoAndar() {
        int cont = 0;
        try {
            //Query para Pesquisa
            String selectSql = ("SELECT idAndar FROM Andar ORDER BY idAndar DESC LIMIT 1");
            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
            //Executando Query
            ResultSet rs = Stmt.executeQuery();

            rs.next();
            //Pegando resposta
            cont = (rs.getInt(1));

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca do Andar");
        }
        return cont;
    }

    //Verificação se existe Andar com as infoermações passadas
    public Andar buscarIdAndar(int idAndar) {
        // String cont = " ";
        Andar cont = new Andar();
        try {
        	String countSql = ("SELECT COUNT(*) as total FROM Andar WHERE idAndar = "+idAndar);
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                    String selectSql = ("SELECT * FROM Andar WHERE idAndar = "+idAndar);
	            PreparedStatement stmt = conectar.prepareStatement(selectSql);
	            //Executando Query
	            ResultSet rs = stmt.executeQuery();
	            rs.next();
	            //Pegando resposta
	            cont.setIdAndar(rs.getInt(1));
	            cont.setNumAndar(rs.getInt(2));
	            cont.setFuncionario(new Funcionario(rs.getInt(3),"","","",""));
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca do Andar");
        }
        return cont;
    }

    //
    public ArrayList<Andar> buscarTodosAndar() {

        ArrayList<Andar> and = new ArrayList();
        try {
            //Query para Pesquisa

            String selectSql = ("SELECT * FROM Andar");
            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
            //Executando Query
            ResultSet rs = Stmt.executeQuery();
            while (rs.next()) {
                //Integração com o funcionario ver, popular o funcionario
                Funcionario func = new Funcionario();
                Andar andar = new Andar();
                andar.setIdAndar(rs.getInt("idAndar"));
                
                andar.setNumAndar(rs.getInt("numAndar"));
                //get dos outros dados pelo método de pessquisa do funcionario
                
                func.setIdFuncionario(rs.getInt("Funcionario_idFuncionario"));
                func.setNomeFuncionario("Pedro");
                func.setEmailFuncionario("pedro@hotmail.com");
                func.setLoginFuncionario("pedro");
                func.setSenhaFuncionario("0704");
                andar.setFuncionario(func);
                and.add(andar);
            }
            rs.close();

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca dos Andar");
        }
        return and;
    }
    
    public boolean excluirTudoTestes() {
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM Quarto");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("DELETE FROM Andar");
            //Execução Query
            excluir.execute();
            //Encerramento Query
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE Andar AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE Quarto AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na Exclusão do Andar");
        }
    }    
}
