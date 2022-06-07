/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Andar;
import model.Funcionario;
import model.Quarto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AndarDAO;

public class QuartoDAO {
    AndarDAO andarDAO = new AndarDAO();
    public Connection conectar;
    
    public void setAndarDAO(AndarDAO and){
        this.andarDAO = and;
    }

    public QuartoDAO() {
        this.conectar = new ConnectionFactory().conectar();
    }
    
    public void setConnection(){
        this.conectar = new ConnectionFactory().conectar("jdbc:mysql://localhost:3306/mydb-tests?useTimezone=true&serverTimezone=UTC","root","3621");
    }

    //Cadastrar Quarto
    public boolean cadastarQuarto(Quarto quarto) {
        PreparedStatement inserir = null;
        try {
            //Preparando Query
            inserir = conectar.prepareStatement("INSERT INTO Quarto (numQuarto,numCamasQuarto,tipoQuarto,qtdBanheirosQuarto,descricaoQuarto,contatoQuarto,Andar_idAndar)VALUES(?,?,?,?,?,?,?)");
            //Passando Parâmetros para cadastro
            
            inserir.setInt(1, quarto.getNumQuarto());
            inserir.setInt(2, quarto.getNumCamasQuarto());
            inserir.setString(3, quarto.getTipoQuarto());
            inserir.setInt(4, quarto.getQtdBanheirosQuarto());
            inserir.setString(5, quarto.getDescricaoQuarto());
            inserir.setInt(6, quarto.getContatoQuarto());
            inserir.setInt(7, quarto.getAndar().getIdAndar());
            //Execução Query
            inserir.execute();
            //Encerramento da Query
            if(inserir.getUpdateCount()==0)throw new SQLException();
            inserir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro no Cadastro do Quarto verificar para encontar o erro");
        }
    }

    //Editar Quarto
    public boolean editarQuarto(Quarto quarto) {
        PreparedStatement editar = null;
        try {
            //Preparando Query para Atualização do Cadastro
            editar = conectar.prepareStatement("UPDATE Quarto SET numQuarto = ?, numCamasQuarto = ?, tipoQuarto = ?, qtdBanheirosQuarto = ?, descricaoQuarto = ?, contatoQuarto = ?, Andar_idAndar = ? WHERE idQuarto = ?");
            //Passando Parâmetros para editar cadastro
            editar.setInt(1, quarto.getNumQuarto());
            editar.setInt(2, quarto.getNumCamasQuarto());
            editar.setString(3, quarto.getTipoQuarto());
            editar.setInt(4, quarto.getQtdBanheirosQuarto());
            editar.setString(5, quarto.getDescricaoQuarto());
            editar.setInt(6, quarto.getContatoQuarto());
            editar.setInt(7, quarto.getAndar().getIdAndar());
            editar.setInt(8, quarto.getIdQuarto());
            //Verificação de linhas afetadas
            //System.out.println(editar.executeUpdate());
            //if (editar.executeUpdate() < 1) {
                //throw new RuntimeException("Andar não existe");
            //} else {
                //Encerramento Query
            editar.executeUpdate();
            if(editar.getUpdateCount()==0)throw new SQLException();
            editar.close();
            return true;
            //}
        } catch (SQLException erro) {
            throw new RuntimeException("Erro em Alterar o Cadastro do Quarto");
        }
    }

    //Excluir Andar
    public boolean excluirQuarto(Quarto quarto) {
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM Quarto WHERE idQuarto = ?");
            //Passando Parâmetros exclusão de cadastro
            excluir.setInt(1, quarto.getIdQuarto());
            //Execução Query
            excluir.execute();
            //Encerramento Query
            if(excluir.getUpdateCount()==0)throw new SQLException();
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na exclusão do quarto, não encontrado");
        }
    }
    //
      public boolean excluirQuartoAndar(Andar andar) {
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM Quarto WHERE Andar_idAndar =  ?");
            //Passando Parâmetros exclusão de cadastro
            excluir.setInt(1, andar.getIdAndar());
            //Execução Query
            excluir.execute();
            //Encerramento Query
            if(excluir.getUpdateCount()==0)throw new SQLException();
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na Exclusão dos Quartos por andar");
        }
    }

    //Verificar se existe um Quarto com essa informação passada
    public Quarto buscarInformacaoNumeroQuarto(int numQuarto) {
        Quarto cont = new Quarto();
        try {
        	String countSql = ("SELECT COUNT(*) as total FROM Quarto WHERE numQuarto = "+numQuarto);
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
	            //Query para Pesquisa
	            String selectSql = ("SELECT * FROM Quarto WHERE numQuarto = " + numQuarto);
	            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
	            //Executando Query
	            ResultSet rs = Stmt.executeQuery();
	            rs.next();
	            //Pegando resposta
	            cont.setIdQuarto(rs.getInt(1));
	            cont.setNumQuarto(rs.getInt(2));
	            cont.setNumCamasQuarto(rs.getInt(3));
	            cont.setTipoQuarto(rs.getString(4));
	            cont.setQtdBanheirosQuarto(rs.getInt(5));
	            cont.setDescricaoQuarto(rs.getString(6));
	            cont.setContatoQuarto(rs.getInt(7));
	            cont.setAndar(andarDAO.buscarIdAndar(rs.getInt(8)));
	            rs.close();
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca do Quarto");
        }
        return cont;
    }

    //Verificação se existe Quarto com as infoermações passadas
    public Quarto buscarIdQuarto(int idQuarto) {
        Quarto cont = new Quarto();
        try {
            //Query para Pesquisa
            String selectSql = ("SELECT * FROM Quarto WHERE idQuarto = " + idQuarto);
            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
            //Executando Query
            ResultSet rs = Stmt.executeQuery();
            rs.next();
            //Pegando resposta
            cont.setIdQuarto(rs.getInt(1));
            cont.setNumQuarto(rs.getInt(2));
            cont.setNumCamasQuarto(rs.getInt(3));
            cont.setTipoQuarto(rs.getString(4));
            cont.setQtdBanheirosQuarto(rs.getInt(5));
            cont.setDescricaoQuarto(rs.getString(6));
            cont.setContatoQuarto(rs.getInt(7));
            cont.setAndar(andarDAO.buscarIdAndar(rs.getInt(8)) );
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca do Quarto");
        }
        return cont;
    }
    
    public ArrayList<Quarto> buscarTodosQuarto() {

        ArrayList<Quarto> quarto = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Quarto");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
	            String selectSql = ("SELECT * FROM Quarto");
	            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
	            //Executando Query
	            ResultSet rs = Stmt.executeQuery();
	            while (rs.next()) {
	                //Integração com o funcionario ver, popular o funcionario
	                quarto.add(new Quarto(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getInt(7), andarDAO.buscarIdAndar(rs.getInt(8))));
	            }
	            rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todos os quartos");
        }
        return quarto;
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

/*==============================================*/
    public ArrayList<Quarto> buscarTodosQuartoOcupados() {

        ArrayList<Quarto> quarto = new ArrayList();
        try {
            //Query para Pesquisa
            String countSql = ("SELECT count(*) as total from Quarto left outer join Reserva on Quarto.idQuarto = Reserva.Quarto_idQuarto "
                    + "Where Quarto.idQuarto = Reserva.Quarto_idQuarto order by Quarto.idQuarto;");
            PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
            ResultSet rsCount = stmtCount.executeQuery();
            rsCount.next();
            if (rsCount.getInt("total") == 0) {
                throw new SQLException();
            } else {
                String selectSql = ("SELECT idQuarto, numQuarto, NumCamasQuarto,tipoQuarto,qtdBanheirosQuarto,descricaoQuarto, contatoQuarto,Andar_idAndar "
                    + "from Quarto left outer join Reserva on Quarto.idQuarto = Reserva.Quarto_idQuarto "
                    + "Where Quarto.idQuarto = Reserva.Quarto_idQuarto order by Quarto.idQuarto;");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    quarto.add(new Quarto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), andarDAO.buscarIdAndar(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todos os quartos");
        }
        return quarto;
    }
    
    public ArrayList<Quarto> buscarTodosQuartoVagos() {

        ArrayList<Quarto> quarto = new ArrayList();
        try {
            //Query para Pesquisa
            String countSql = ("SELECT count(*) as total from Quarto left outer join Reserva on Quarto.idQuarto = Reserva.Quarto_idQuarto "
                    + "Where idQuarto not in (select Quarto_idQuarto from Reserva ) order by Quarto.idQuarto;");
            PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
            ResultSet rsCount = stmtCount.executeQuery();
            rsCount.next();
            if (rsCount.getInt("total") == 0) {
                throw new SQLException();
            } else {
                String selectSql = ("SELECT idQuarto, numQuarto, NumCamasQuarto,tipoQuarto,qtdBanheirosQuarto,descricaoQuarto, contatoQuarto,Andar_idAndar " 
                 + "from Quarto left outer join Reserva on Quarto.idQuarto = Reserva.Quarto_idQuarto " 
                 + "Where idQuarto not in (select Quarto_idQuarto from Reserva ) order by Quarto.idQuarto;");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    quarto.add(new Quarto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), andarDAO.buscarIdAndar(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todos os quartos");
        }
        return quarto;
    }    
}
