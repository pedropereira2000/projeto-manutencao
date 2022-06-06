package controller;

import controller.GerenciaFuncionario;
import dao.FuncionarioDAO;
import model.Funcionario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TesteFuncionarios {

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
    public void testCadastrarAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecFunc = new GerenciaFuncionario();
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.cadastrarFuncionario(anyObject())).thenReturn(true);
        
        gerecFunc.setFuncDAO(funcMock);
        
        assertTrue(gerecFunc.cadastrarFuncionarios("Pedro", "pedro@hotmail.com", "pedro", "0704"));
    }
    
    @Test
    public void testValidaCadatroEmailErradoMock(){
        var func = new Funcionario(1, "Pedro", "pedro", "pedro", "0704");
        var gerecFunc = new GerenciaFuncionario();
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.cadastrarFuncionario(anyObject())).thenReturn(false);
        
        gerecFunc.setFuncDAO(funcMock);
        var res = assertThrows(RuntimeException.class, () -> {
            gerecFunc.cadastrarFuncionarios("Pedro", "pedro", "pedro", "0704");
        }).getMessage();

        assertEquals("Erro no email informado: Verique se foi informado um email válido com provedor e @", res);
    } 
    
    @Test
    public void testValidaEditarEmailErradoMock(){
        
        var func = new Funcionario(1, "marcos vinicius", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();

        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        funcController.cadastrarFuncionarios("marcos vinicius", "marcos@email.com", "mvsr", "m123");
        //Verificando se a alteração foi feita no banco
        var res = assertThrows(RuntimeException.class, () -> {
            funcController.editarFuncionario(1, "1", "marcosresa", "marcos", "mvsr", "m123");
        }).getMessage();     

        assertEquals("Erro ao Editar: Campos Vazios", res);
    } 
    
    @Test
    public void testEditarAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecFunc = new GerenciaFuncionario();
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.editarFuncionario(anyObject())).thenReturn(true);
        
        gerecFunc.setFuncDAO(funcMock);
        
        assertTrue(gerecFunc.editarFuncionario(1, "1", "Pedro", "pedro@hotmail.com", "pedro", "0704"));
    }
    
    @Test
    public void testExcluirAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecFunc = new GerenciaFuncionario();
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.excluirFuncionario(anyObject())).thenReturn(true);
        
        gerecFunc.setFuncDAO(funcMock);
        
        assertTrue(gerecFunc.excluirFuncionario(1, "1"));
    }
    
    @Test
    public void testBuscarAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var func2 = new Funcionario(1, "Carlos", "carlos@hotmail.com", "carlos", "0704");
        var func3 = new Funcionario(1, "Mario", "mario@hotmail.com", "mario", "0704");
        var gerecFunc = new GerenciaFuncionario();
        var funcMock = mock(FuncionarioDAO.class);
        ArrayList<Funcionario> funcs = new ArrayList();
        
        funcs.add(func2);
        
        when(funcMock.pesquisarFuncionario(anyString(), anyString())).thenReturn(funcs);
        
        gerecFunc.setFuncDAO(funcMock);
        
        assertEquals("carlos@hotmail.com" ,gerecFunc.pesquisarFuncionario("carlos", "Carlos").get(0).getEmailFuncionario());
    }

    @Test
    public void testCadastroFuncionariosValidos() throws Exception {
        var func = new Funcionario(71, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");
        //Verificando o funcionario cadastrado no banco
        System.out.println(funcController.pesquisarFuncionario("mvsr", "marcos").get(0));
        assertEquals("mvsr", funcController.pesquisarFuncionario("mvsr", "marcos").get(0).getLoginFuncionario());
        assertEquals("m123", funcController.pesquisarFuncionario("mvsr", "marcos").get(0).getSenhaFuncionario());
    }

    @Test
    public void testEditarFuncionariosValidos() throws Exception{
        var func = new Funcionario(1, "marcos vinicius", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();

        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        funcController.cadastrarFuncionarios("marcos vinicius", "marcos@email.com", "mvsr", "m123");
        funcController.editarFuncionario(1, "1", "marcosresa", "marcos@email.com", "mvsr", "m123");
        //Verificando se a alteração foi feita no banco
        assertEquals("marcosresa", funcController.pesquisarFuncionario("mvsr", "marcosresa").get(0).getNomeFuncionario());
    }

    @Test
    public void testExcluirFuncionariosValido(){
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");
        funcController.excluirFuncionario(1, "1");
        var res = assertThrows(RuntimeException.class, () -> {
            funcController.pesquisarFuncionario("mvsr", "marcos");
        }).getMessage();
        assertEquals("Não foi possivel encontrar a pesquisa", res);
    }

    @Test
    public void testPesquisarFuncionariosValida()throws Exception {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios( "marcos", "marcos@email.com", "mvsr", "m123");

        ArrayList<Funcionario> lista = new ArrayList();

        lista.add(func);

        assertEquals("marcos@email.com", funcController.pesquisarFuncionario("mvsr", "marcos").get(0).getEmailFuncionario());
    }

    @Test
    public void testCadastroFuncionariosCamposVazios() {
        var func = new Funcionario(1, null, "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        var res = assertThrows(Exception.class, () -> {
            funcController.cadastrarFuncionarios("", "", "", "");
        }).getMessage();

        assertEquals("Erro ao Cadastrar: Campos Vazios", res);
    }

    @Test
    public void testNenhumFuncionariosSelecionadoParaEditar() {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        assertTrue(funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123"));

        var res = assertThrows(Exception.class, () -> {
            funcController.editarFuncionario(1, "0", "marcos vinicius", "marcos@email.com", "mvsr", "m123");
        }).getMessage();

        assertEquals("Erro ao Editar: Campos Vazios", res);
    }

    @Test
    public void testEditarFuncionariosCamposVazios() {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");

        var res = assertThrows(Exception.class, () -> {
            funcController.editarFuncionario(1, "1", "", "", "", "");
        }).getMessage();

        assertEquals("Erro ao Editar: Campos Vazios", res);
    }

    @Test
    public void testNenhumFuncionarioSelecionadoParaExcluir() {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");

        var res = assertThrows(Exception.class, () -> {
            funcController.excluirFuncionario(1, "0");
        }).getMessage();

        assertEquals("Funcionario não existe", res);
    }

    @Test
    public void testFuncionarioNaoExisteParaExcluir() {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");

        var res = assertThrows(Exception.class, () -> {
            funcController.excluirFuncionario(1, "2");
        }).getMessage();

        assertEquals("Funcionario não existe", res);
    }

    
    @Test
    public void testPesquisarFuncionariosInvalida() {
            var func = new Funcionario(1,"marcos","marcos@email.com","mvsr","m123");
            var funcPesquisa = new Funcionario(1,"m","marcos@email.com","m","m123");
            var funcC = new GerenciaFuncionario();
            funcC.setFuncDAO(funcDAO);

            funcDAO.cadastrarFuncionario(func);

            var res = assertThrows(Exception.class,() -> {
                    funcC.pesquisarFuncionario("m", "m");
            }).getMessage();

            assertEquals("Não foi possivel encontrar a pesquisa",res);

    }
     
    @Test
    public void testPesquisarFuncionariosVazio() {
        var func = new Funcionario(1, "marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, " ", "marcos@email.com", " ", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        funcController.cadastrarFuncionarios("marcos", "marcos@email.com", "mvsr", "m123");

        var res = assertThrows(Exception.class, () -> {
            funcController.pesquisarFuncionario(funcPesquisa.getLoginFuncionario(), funcPesquisa.getNomeFuncionario());
        }).getMessage();

        assertEquals("Não foi selecionado nenhuma informação para pesquisa",res);

    }
    
    @Test
    public void testThrowExceptionCadastro() {
    	var func = new Funcionario(1, "", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, " ", "marcos@email.com", " ", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.cadastrarFuncionario(func);
        }).getMessage();

        assertEquals("Nao eh possivel cadastrar o funcionario",res);
    }
    
    @Test
    public void testThrowExceptionEditar() {
    	var func = new Funcionario(1, "", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, " ", "marcos@email.com", " ", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.editarFuncionario(func);
        }).getMessage();

        assertEquals("Funcionario nao existe",res);
    }
    
    @Test
    public void testThrowExceptionExcluir() {
    	var func = new Funcionario(1, "", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, " ", "marcos@email.com", " ", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.excluirFuncionario(func);
        }).getMessage();

        assertEquals("Nao existe funcionario para exclusao",res);
    }
    
    @Test
    public void testThrowExceptionPesquisaFuncionarios() {
    	var func = new Funcionario(1, "", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, " ", "marcos@email.com", " ", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.pesquisarFuncionario("caio", "Caio");
        }).getMessage();

        assertEquals("Não foi possivel encontrar a pesquisa",res);
    }
    
    @Test
    public void testThrowExceptiosListaLoginsInvalidos() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        //funcDAO.cadastrarFuncionario(func);
        //funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.listarLogin();
        }).getMessage();

        assertEquals("Não há Logins para listar",res);
    }
    
    @Test
    public void testThrowExceptiosListaFuncionarios() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        funcDAO.cadastrarFuncionario(func);
        funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = funcDAO.listarLogin();

        assertEquals("mvsr",res.get(0).getLoginFuncionario());
        assertEquals("pedro",res.get(1).getLoginFuncionario());
    }
    
    
    @Test
    public void testThrowExceptiosListaNomesInvalidos() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        //funcDAO.cadastrarFuncionario(func);
        //funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.listarNome("pedro");
        }).getMessage();

        assertEquals("Não há funcionários para listar",res);
    }
    
    @Test
    public void testThrowExceptiosNomesFuncionarios() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "pedro", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        funcDAO.cadastrarFuncionario(func);
        funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = funcDAO.listarNome("pedro");

        assertEquals("Marcos",res.get(0).getNomeFuncionario());
        assertEquals("Pedro",res.get(1).getNomeFuncionario());
    }
    
    @Test
    public void testThrowExceptiosGetFuncionariosInvalidos() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        //funcDAO.cadastrarFuncionario(func);
        //funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.getFuncionarios(1);
        }).getMessage();

        assertEquals("Funcionario informado não existe",res);
    }
    
    @Test
    public void testThrowExceptiosGetFuncionarios() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "pedro", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        funcDAO.cadastrarFuncionario(func);
        funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = funcDAO.getFuncionarios(1);
        var res2 = funcDAO.getFuncionarios(2);

        assertEquals("Marcos",res.getNomeFuncionario());
        assertEquals("Pedro",res2.getNomeFuncionario());
    }
    
    @Test
    public void testThrowExceptiosListaFuncionariosInvalidos() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        //funcDAO.cadastrarFuncionario(func);
        //funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.listarFuncionarios();
        }).getMessage();

        assertEquals("Não há funcionários para listar",res);
    }
    
    @Test
    public void testThrowExceptiosListarFuncionarios() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "pedro", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        funcDAO.cadastrarFuncionario(func);
        funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = funcDAO.listarFuncionarios();

        assertEquals("Marcos",res.get(0).getNomeFuncionario());
        assertEquals("Pedro",res.get(1).getNomeFuncionario());
    }
    
    @Test
    public void testThrowExceptiosLoginInvalido() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcPesquisa = new Funcionario(1, "Pedro", "Pedro@email.com", "pedro", "0704");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        //funcDAO.cadastrarFuncionario(func);
        //funcDAO.cadastrarFuncionario(funcPesquisa);

        var res = assertThrows(RuntimeException.class, () -> {
            funcDAO.validaLogin("teste","teste");
        }).getMessage();

        assertEquals("Login e senha informados não existe",res);
    }
    
    @Test
    public void testThrowExceptiosValidaLogin() {
    	var func = new Funcionario(1, "Marcos", "marcos@email.com", "mvsr", "m123");
        var funcController = new GerenciaFuncionario();
        //Função para definir o banco de teste
        funcController.setFuncDAO(funcDAO);
        
        funcDAO.cadastrarFuncionario(func);

        var res = funcDAO.validaLogin("mvsr", "m123");

        assertEquals("Marcos",funcDAO.getFuncionarios(res).getNomeFuncionario());
    }
}
