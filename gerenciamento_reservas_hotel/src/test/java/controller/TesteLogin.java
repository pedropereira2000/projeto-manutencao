package controller;

import model.Funcionario;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.FuncionarioDAO;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;

//import controller.Login;
//import controller.LoginConnection;

public class TesteLogin {
	
    private static FuncionarioDAO funcDAO;
	
    @BeforeAll
    public static void setUpClass() {
        funcDAO = new FuncionarioDAO();
        funcDAO.setConnection();
    }

    @AfterEach
    public void limpaTabelaFuncionarios() {
        funcDAO.excluirTudoBase();
    }
    
    @Test
    public void testeLoginVazio(){
        var lg = new GerenciaLogin();
        lg.setLoginConn(funcDAO);
        
        //funcDAO.cadastrarFuncionario(new Funcionario(0,"antonio", "antonio@hotmail.com","antonio","0704"));
        
        var res = assertThrows(NullPointerException.class, () -> {lg.VerificaAcesso("", "0704");}).getMessage();
        assertEquals(res, "Verifique o campo de login pois está vazio");
    }
    
    @Test
    public void testeSenhaVazio(){
        var lg = new GerenciaLogin();
        lg.setLoginConn(funcDAO);
        
        //funcDAO.cadastrarFuncionario(new Funcionario(0,"antonio", "antonio@hotmail.com","antonio","0704"));
        
        var res = assertThrows(NullPointerException.class, () -> {lg.VerificaAcesso("antonio", "");}).getMessage();
        assertEquals(res, "Verifique o campo de senha pois está vazio" );
    }
    
    @Test
    public void testeUsuarioLoginCorretos(){
        var lg = new GerenciaLogin();
        lg.setLoginConn(funcDAO);
        
        funcDAO.cadastrarFuncionario(new Funcionario(0,"antonio", "antonio@hotmail.com","antonio","0704"));
        
        //Se existir o login e senha pega o id do Funcionário
        var res = lg.VerificaAcesso("antonio", "0704");
        //Acessa e compara o id retornado pela função com o id do funcionário cadastrado no banco que é 1
        assertEquals("antonio", funcDAO.getFuncionarios(res).getLoginFuncionario());
        assertEquals("0704", funcDAO.getFuncionarios(res).getSenhaFuncionario());
    }
    
    @Test
    public void testeUsuarioErrado(){
        var lg = new GerenciaLogin();
        lg.setLoginConn(funcDAO);
        
        funcDAO.cadastrarFuncionario(new Funcionario(0,"Antonio", "antonio@hotmail.com","antonio","0704"));
        
        var res = assertThrows(RuntimeException.class, () -> {
        	lg.VerificaAcesso("arnaldinho", "0704");
        }).getMessage();
        
        //Retorna 0 pois na DAO tem uma verificação pela query do sql que faz um count dois itens encontrados, se não encontrou nenhum retorna 0
        assertEquals("Login e senha informados não existe", res);
    }
    
    @Test
    public void testeUsuarioErradoMock() {
        var lg = new GerenciaLogin();
        var mockFuncDao = mock(FuncionarioDAO.class);
        when(mockFuncDao.validaLogin(anyString(), anyString())).thenReturn(0);
        
        lg.setLoginConn(mockFuncDao);
        
        assertEquals(0,lg.VerificaAcesso("antonio", "0704"));
    }
    
    @Test
    public void testeUsuarioLoginCorretosMock() {
        var lg = new GerenciaLogin();
        var mockFuncDao = mock(FuncionarioDAO.class);
        lg.setLoginConn(mockFuncDao);        
        
        when(mockFuncDao.validaLogin(anyString(), anyString())).thenReturn(1);
        
        assertEquals(1, lg.VerificaAcesso("antonio", "0704"));
    }
    
}