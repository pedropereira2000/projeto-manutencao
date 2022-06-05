package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import model.Funcionario;

public class FuncionarioDAO {
    public Connection conectar;

    public FuncionarioDAO(){
        this.conectar = new ConnectionFactory().conectar();
    }
    
    public void setConnection(){
        this.conectar = new ConnectionFactory().conectar("jdbc:mysql://localhost:3306/mydb-tests?useTimezone=true&serverTimezone=UTC","root","3621");
    }

    //Cadastrar Funcionario
    public boolean cadastrarFuncionario(Funcionario func){


        PreparedStatement inserir = null;
        
            try {
            	if(func.getNomeFuncionario().equals("")||func.getEmailFuncionario().equals("")||func.getLoginFuncionario().equals("")||func.getSenhaFuncionario().equals("")) {
            		throw new SQLException();
            	}else {
	                //Query para inserção de um novo Funcionario no banco de dados
	                inserir = conectar.prepareStatement("INSERT INTO FUNCIONARIO (nomeFuncionario,emailFuncionario,loginFuncionario,senhaFuncionario)VALUES(?,?,?,?)");
	                //Passando id do Funcionario como parâmetro
	                //inserir.setInt(1, func.getIdFuncionario());
	                //Passando nome do Funcionario como parâmetro
	                inserir.setString(1, func.getNomeFuncionario());
	                //Passando e-mail do Funcionario como parâmetro
	                inserir.setString(2, func.getEmailFuncionario());
	                //Passando login do Funcionario como parâmetro
	                inserir.setString(3, func.getLoginFuncionario());
	                //Passando senha do Funcionario como parâmetro
	                inserir.setString(4, func.getSenhaFuncionario());
	                //Executando a query
	                inserir.execute();
	                //Encerrando a execução
	                inserir.close();
	                return true;
            	}
            } catch (SQLException erro) {
                throw new RuntimeException("Nao eh possivel cadastrar o funcionario");
            }
       
    }
    //Editar Funcionario
    public boolean editarFuncionario(Funcionario func){

        PreparedStatement atualizar = null;

        try {
            //Query para atualizar o Funcionario
            atualizar = conectar.prepareStatement("UPDATE FUNCIONARIO SET nomeFuncionario = ? ,emailFuncionario = ?,loginFuncionario = ?,senhaFuncionario = ? WHERE idFuncionario = ?");
            //Passando id do Funcionario como parâmetro
            atualizar.setInt(5, func.getIdFuncionario());
            //Passando nome do Funcionario como parâmetro
            atualizar.setString(1, func.getNomeFuncionario());
            //Passando e-mail do Funcionario como parâmetro
            atualizar.setString(2, func.getEmailFuncionario());
            //Passando login do Funcionario como parâmetro
            atualizar.setString(3, func.getLoginFuncionario());
            //Passando senha do Funcionario como parâmetro para comparação
            atualizar.setString(4, func.getSenhaFuncionario());
            //Executando a query
            atualizar.execute();
            int linhasAfetadas = atualizar.getUpdateCount();
            if(linhasAfetadas < 1){
                throw new SQLException();
            }else {
                //Finalizando execução
                atualizar.close();
                return true;
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Funcionario nao existe");
        }
    }
    //Excluir Funcionario
    public boolean excluirFuncionario(Funcionario func){

        PreparedStatement excluir = null;

        try {
            //Query para deletar o Funcionario selecionado atraves do id
            excluir = conectar.prepareStatement("DELETE FROM FUNCIONARIO WHERE idFuncionario = ?");
            //Passando id do Funcionario como parâmetro
            excluir.setInt(1, func.getIdFuncionario());
            //Executando a query
            excluir.execute();
            int linhasAfetadasExcluir = excluir.getUpdateCount();

            if(linhasAfetadasExcluir < 1){
                throw new SQLException();
            }else {
                //Finalizando a execução
                excluir.close();
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Nao existe funcionario para exclusao");
        }

    }
    //Consultando Funcionario por login e nome
    public List<Funcionario> pesquisarFuncionario(String login,String nome ) {

        PreparedStatement consulta = null;
       // PreparedStatement ConsultanumPesquisa = null;
        ResultSet resultado = null;
       // ResultSet resConsultanumPesquisa = null;

        List<Funcionario> funcionarios = new ArrayList<>();
        //Verificando se não foi passada informação para a pesquisa
        if(login.equals(" ") && nome.equals(" ")){
            //JOptionPane.showMessageDialog(null, "Nenhum Funcionario Selecionado Na Tabela");
            throw new RuntimeException("Não foi selecionado nenhuma informação para pesquisa");
        }else{
            try {
                //Pegando query de seleção para buscar Funcionario por login e nome
                consulta = conectar.prepareStatement("SELECT Count(*) as total, idFuncionario, nomeFuncionario, emailFuncionario, loginFuncionario, senhaFuncionario FROM FUNCIONARIO WHERE loginFuncionario = ? AND nomeFuncionario = ? ");
                //ConsultanumPesquisa = conectar.prepareStatement("SELECT COUNT(*) AS 'numPesquisa' FROM FUNCIONARIO WHERE loginFuncionario = ? AND nomeFuncionario = ? ");
                //Passando login como parâmetro
                consulta.setString(1, login);
                //Passando nome como parâmetro
                consulta.setString(2, nome);
                //Executando query
                resultado = consulta.executeQuery();
                //resConsultanumPesquisa = ConsultanumPesquisa.executeQuery();
                //int linhasAfetadasPesquisar = consulta.getUpdateCount();
                //int linhasAfetadasPesquisar = resConsultanumPesquisa.getInt("numPesquisa");
                //if(linhasAfetadasPesquisar < 1){
                    //throw new RuntimeException("Nao existe funcionario para pesquisa");
                //}else {                    
                    //Laço de repetição para popular a classe Funcionario
                resultado.next();
                if(resultado.getInt("total") == 0) throw new SQLException();
                do{
                	
                    Funcionario func = new Funcionario();
                    func.setIdFuncionario(resultado.getInt("idFuncionario"));
                    func.setNomeFuncionario(resultado.getString("nomeFuncionario"));
                    func.setEmailFuncionario(resultado.getString("emailFuncionario"));
                    func.setLoginFuncionario(resultado.getString("loginFuncionario"));
                    func.setSenhaFuncionario(resultado.getString("senhaFuncionario"));
                    funcionarios.add(func);
                }while (resultado.next());
                    
               //}

            } catch (SQLException erro) {
                //JOptionPane.showMessageDialog(null, "Nenhum Funcionario Selecionado Na Tabela");
                throw new RuntimeException("Não foi possivel encontrar a pesquisa");
            }
        }

        return funcionarios;
    }
     //Listar todos os login
    public List<Funcionario> listarLogin(){
        
        PreparedStatement listarB = null;
        PreparedStatement countBusc = null;
        ResultSet resultadoB = null;
        ResultSet rsCount = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            //Query de seleção dos login
            countBusc  = conectar.prepareStatement("SELECT COUNT(*) as total FROM Funcionario");
            listarB  = conectar.prepareStatement("SELECT loginFuncionario FROM Funcionario");
            //executando query
            rsCount = countBusc.executeQuery();
            resultadoB = listarB.executeQuery();
            rsCount.next();
            if(rsCount.getInt("total") == 0) throw new SQLException();
            //Laço de repetição para pegar os login buscados
            while (resultadoB.next()){
                Funcionario func = new Funcionario();
                func.setLoginFuncionario(resultadoB.getString("loginFuncionario"));
                funcionarios.add(func);
            }
            rsCount.close();
            resultadoB.close();
            return funcionarios;

        } catch (SQLException erro) {
            throw new RuntimeException("Não há Logins para listar");
        }

    }
    //Listando todos os nomes referentes ao login
    public List<Funcionario> listarNome(String login){
        
        PreparedStatement listarS = null;
        ResultSet resultadoS = null;
        PreparedStatement countList = null;
        ResultSet rsList = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            //Query para buscar os nomes
        	countList  = conectar.prepareStatement("SELECT COUNT(*) as total FROM Funcionario WHERE loginFuncionario = ?");
        	countList.setString(1, login);
            listarS  = conectar.prepareStatement("SELECT nomeFuncionario FROM Funcionario WHERE loginFuncionario = ?");
            //Passando login como parâmetro
            listarS.setString(1, login);
            //Executando query para buscar os nomes
            resultadoS = listarS.executeQuery();
            rsList = countList.executeQuery();
            rsList.next();
            if(rsList.getInt("total")==0) throw new SQLException();
            //Laço de repetição para pegar os nomes
            while (resultadoS.next()) {
                Funcionario func = new Funcionario();
                func.setNomeFuncionario(resultadoS.getString("nomeFuncionario"));
                funcionarios.add(func);
            }
            
            return funcionarios;

        } catch (SQLException erro) {
            throw new RuntimeException("Não há funcionários para listar");
        }

    }
    
    public Funcionario getFuncionarios(int idFuncionario){
        Funcionario funcionario = new Funcionario();

        try {
            //Query para buscar os nomes
            String countList = "SELECT COUNT(*) as total FROM Funcionario WHERE idFuncionario = "+idFuncionario;
            PreparedStatement StmtCount = conectar.prepareStatement(countList);
            ResultSet rsList = StmtCount.executeQuery();
            rsList.next();
           
            if(rsList.getInt("total")==0) throw new SQLException();
            else {
	            //Laço de repetição para armazenar as informações do Funcionario
            	String sql = "SELECT * FROM Funcionario WHERE idFuncionario = "+idFuncionario;
                PreparedStatement Stmt = conectar.prepareStatement(sql);
                //Executando Query
                ResultSet rs = Stmt.executeQuery();
                rs.next();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setEmailFuncionario(rs.getString("emailFuncionario"));
                funcionario.setLoginFuncionario(rs.getString("loginFuncionario"));
                funcionario.setSenhaFuncionario(rs.getString("senhaFuncionario"));
                return funcionario;
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Funcionario informado não existe");
        }

    }
    
    //Listando todos os Funcionarios
    public List<Funcionario> listarFuncionarios(){
        
        PreparedStatement listar = null;
        ResultSet resultado = null;
        PreparedStatement countList = null;
        ResultSet rsList = null;


        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            //Query para buscar todos os Funcionarios ordenados pelo nome
        	countList  = conectar.prepareStatement("SELECT COUNT(*) as total FROM Funcionario");
            listar  = conectar.prepareStatement("SELECT * FROM Funcionario ORDER BY nomeFuncionario");
            //Executando a query
            resultado = listar.executeQuery();
            //Laço de repetição para armazenar as informações do Funcionario
            rsList = countList.executeQuery();
            rsList.next();
            if(rsList.getInt("total")==0) throw new SQLException();
            else {
	            while (resultado.next()) {
	
	                Funcionario funcionario = new Funcionario();
	                
	                funcionario.setIdFuncionario(resultado.getInt("idFuncionario"));
	                funcionario.setNomeFuncionario(resultado.getString("nomeFuncionario"));
	                funcionario.setEmailFuncionario(resultado.getString("emailFuncionario"));
	                funcionario.setLoginFuncionario(resultado.getString("loginFuncionario"));
	                funcionario.setSenhaFuncionario(resultado.getString("senhaFuncionario"));
	                funcionarios.add(funcionario);
	                
	            }
            
	            return funcionarios;
            }

        } catch (SQLException erro) {
            throw new RuntimeException("Não há funcionários para listar");
        }

    }
    
    public int validaLogin(String user, String passWord) {
        int func = 0;
        PreparedStatement sql = null;
        ResultSet rs = null;
        PreparedStatement countList = null;
        ResultSet rsList = null;

        try {
        	countList  = conectar.prepareStatement("SELECT COUNT(*) as total FROM Funcionario WHERE loginFuncionario = ? and senhaFuncionario = ?");
        	countList.setString(1, user);
        	countList.setString(2, passWord);
            //Query para buscar todos os Funcionarios ordenados pelo nome
            rsList = countList.executeQuery();
            rsList.next();
            if(rsList.getInt("total")==0) {throw new SQLException();}
            else {
            	sql = conectar.prepareStatement("SELECT idFuncionario FROM Funcionario WHERE loginFuncionario = ? and senhaFuncionario = ?");
            	sql.setString(1, user);
            	sql.setString(2, passWord);
	            //Executando Query
	            rs = sql.executeQuery();
	            rs.next();            //Laço de repetição para armazenar as informações do Funcionario
                func = rs.getInt("idFuncionario");
                return func;
	            
            }

        } catch (SQLException erro) {
            if(user.isEmpty()){
                return 0;
            }else if(passWord.isEmpty()){
                return 0;
            }else{
                JOptionPane.showMessageDialog(null, "Login ou senha informados não existe");
                throw new RuntimeException("Login e senha informados não existe");
            }
            
        } 
    }
    
    public boolean excluirTudoBase() {
        PreparedStatement excluir = null;
        try {
            //Preparando Query para Exclusão
            excluir = conectar.prepareStatement("DELETE FROM RESERVA");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("DELETE FROM QUARTO");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("DELETE FROM ANDAR");
            //Execução Query
            excluir.execute();
            //Encerramento Query
            excluir.close();
            excluir = conectar.prepareStatement("DELETE FROM FUNCIONARIO");
            //Execução Query
            excluir.execute();
            //Encerramento Query
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE RESERVA AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE ANDAR AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE QUARTO AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            excluir = conectar.prepareStatement("ALTER TABLE FUNCIONARIO AUTO_INCREMENT = 1;");
            excluir.execute();
            excluir.close();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro na Exclusão dos funcionários");
        }
    }    
}


