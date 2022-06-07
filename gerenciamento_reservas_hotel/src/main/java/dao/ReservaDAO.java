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
import model.Quarto;
import model.Reserva;

public class ReservaDAO {
    QuartoDAO quartoDAO = new QuartoDAO();
    FuncionarioDAO funcDAO = new FuncionarioDAO();
    public Connection conectar;
    
    public ReservaDAO(){
        this.conectar = new ConnectionFactory().conectar();
    }
    
    public void setConnection(){
        this.conectar = new ConnectionFactory().conectar("jdbc:mysql://localhost:3306/mydb-tests?useTimezone=true&serverTimezone=UTC","root","Pedro@0704");
    }

    
    public void setQuartoDAO(QuartoDAO quartoDAO){
        this.quartoDAO = quartoDAO;
    }

    public void setFuncionarioDAO(FuncionarioDAO funcDAO){
        this.funcDAO = funcDAO;
    }
    
    public Reserva buscaReserva(String entrada, int numQuarto){
        Reserva reserv = null;
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT Count(*) as total FROM Reserva WHERE entradaReserva = DATE('"+entrada+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
	            String selectSql = ("SELECT * FROM Reserva WHERE entradaReserva = DATE('"+entrada+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
	            PreparedStatement Stmt = conectar.prepareStatement(selectSql);
	            //Executando Query
	            ResultSet rs = Stmt.executeQuery();
	            while (rs.next()) {
	                //Integração com o funcionario ver, popular o funcionario
	                reserv = new Reserva(rs.getInt(1),String.valueOf(rs.getDate(2)),String.valueOf(rs.getDate(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8)));
	            }
	            rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca da Reserva");
        }
        return reserv;
    }
    
    public boolean cadastrarReserva(Reserva reserv){
        PreparedStatement inserir = null;
        try {
            //Preparando Query
            inserir = conectar.prepareStatement("INSERT INTO Reserva (entradaReserva,saidaReserva,clienteReserva,telefoneReserva,documentoReserva,Funcionario_idFuncionario,Quarto_idQuarto)VALUES(STR_TO_DATE(?, '%d-%m-%Y'),STR_TO_DATE(?, '%d-%m-%Y'),?,?,?,?,?)");
            //Passando Parâmetros para cadastro
            inserir.setString(1, reserv.getEntradaReserva());
            inserir.setString(2, reserv.getSaidaCliente());
            inserir.setString(3, reserv.getClienteReserva());
            inserir.setString(4, reserv.getTelefoneReserva());
            inserir.setString(5, reserv.getDocumentoReserva());
            inserir.setInt(6, reserv.getFunIdFuncionario().getIdFuncionario());
            inserir.setInt(7, reserv.getQuarIdQuarto().getIdQuarto());
            //Execução Query
            inserir.execute();
            //Encerramento da Query
            if(inserir.getUpdateCount()==0)throw new SQLException();
            inserir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro no Cadastro da Reserva verificar para encontar o erro");
        }
    }
    
    public boolean editarReserva(Reserva reserv){
        PreparedStatement editar = null;
        try {
            //Preparando Query para Atualização do Cadastro
            editar = conectar.prepareStatement("UPDATE Reserva SET clienteReserva = ?, telefoneReserva = ?, documentoReserva = ? WHERE entradaReserva = Date(?) and Quarto_idQuarto = ?");
            //Passando Parâmetros para editar cadastro
            editar.setString(1, reserv.getClienteReserva());
            editar.setString(2, reserv.getTelefoneReserva());
            editar.setString(3, reserv.getDocumentoReserva());
            editar.setString(4, reserv.getEntradaReserva());
            editar.setInt(5, reserv.getQuarIdQuarto().getIdQuarto());
            editar.executeUpdate();
            if(editar.getUpdateCount()==0)throw new SQLException();
            editar.close();
            return true;
            //}
        } catch (SQLException erro) {
            throw new RuntimeException("Erro em Alterar a reserva");
        }
    }
    
    public boolean excluirReserva(Reserva reserv){
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM Reserva WHERE idReserva = ?");
            //Passando Parâmetros exclusão de cadastro
            excluir.setInt(1, reserv.getIdReserva());
            //Execução Query
            excluir.execute();
            //Encerramento Query
            if(excluir.getUpdateCount()==0)throw new SQLException();
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na exclusão da reserva, não encontrado");
        }
    }
    
    public ArrayList<Reserva> buscTodasReservas(){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasEntradaSaidaNumQuarto(String entrada, String saida, int numQuarto){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE entradaReserva = Date('"+entrada+"') and saidaReserva = Date('"+saida+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE entradaReserva = Date('"+entrada+"') and saidaReserva = Date('"+saida+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasEntradaNumQuarto(String entrada, int numQuarto){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE entradaReserva = Date('"+entrada+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE entradaReserva = Date('"+entrada+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasSaidaNumQuarto(String saida, int numQuarto){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE saidaReserva = Date('"+saida+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE saidaReserva = Date('"+saida+"') and Quarto_idQuarto = (SELECT idQuarto FROM Quarto WHERE numQuarto = "+numQuarto+")");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasNomeDocCli(String nomeCli, String docCli){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE clienteReserva = '"+nomeCli+"' and documentoReserva = '"+docCli+"'");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE clienteReserva = '"+nomeCli+"' and documentoReserva = '"+docCli+"'");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas aqui"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasNomeCli(String nomeCli){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE clienteReserva = '"+nomeCli+"'");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE clienteReserva = '"+nomeCli+"'");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
    public ArrayList<Reserva> buscReservasDocCli(String docCli){
        ArrayList<Reserva> reserva = new ArrayList();
        try {
            //Query para Pesquisa
        	String countSql = ("SELECT COUNT(*) as total FROM Reserva WHERE documentoReserva = '"+docCli+"'");
        	PreparedStatement stmtCount = conectar.prepareStatement(countSql);
            //Query para Pesquisa
            // String selectSql = ("SELECT COUNT(*) FROM ANDAR WHERE idAndar = '" + idAndar + "'");
        	ResultSet rsCount = stmtCount.executeQuery();
        	rsCount.next();
            if(rsCount.getInt("total")==0) throw new SQLException();
            else {
                String selectSql = ("SELECT * FROM Reserva WHERE documentoReserva = '"+docCli+"'");
                PreparedStatement Stmt = conectar.prepareStatement(selectSql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                while (rs.next()) {
                    //Integração com o funcionario ver, popular o funcionario
                    reserva.add(new Reserva(rs.getInt(1),String.valueOf(rs.getString(2)),String.valueOf(rs.getString(3)),rs.getString(4),rs.getString(5),rs.getString(6),funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
                }
                rs.close();
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Erro na busca de todas as reservas"+erro);
        }
        return reserva;
    }
    
//    /Busca por nome do Cliente/
    public ArrayList<Reserva> buscReservasNameCli(String nameCli) {
       ArrayList<Reserva> reserva = new ArrayList();

       try{
           //Query para Pesquisa
             String selectSql = ("SELECT *FROM Reserva Where  clienteReserva Like '%"+nameCli+"%';");
             PreparedStatement Stmt = conectar.prepareStatement(selectSql);

            //Executando Query
            ResultSet rs = Stmt.executeQuery();

            while (rs.next()) {
                reserva.add(new Reserva(rs.getInt(1), String.valueOf(rs.getString(2)), String.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5), rs.getString(6), funcDAO.getFuncionarios(rs.getInt(7)), quartoDAO.buscarIdQuarto(rs.getInt(8))));
            }
            rs.close();
       }catch(SQLException erro){
           throw new RuntimeException("Erro na busca de todas as reservas" + erro);
       }

        return reserva;
    }
}
