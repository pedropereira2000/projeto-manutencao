/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Andar;
import model.Funcionario;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import dao.AndarDAO;
import dao.FuncionarioDAO;
import dao.QuartoDAO;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Matchers.anyString;

public class TesteGerenciaAndar {

    private static AndarDAO andarDAO;
    private static QuartoDAO quartoDAO;
    private static FuncionarioDAO funcDAO;

    @BeforeAll
    public static void setUpClass() {
        andarDAO = new AndarDAO();
        andarDAO.setConnection();
        quartoDAO = new QuartoDAO();
        quartoDAO.setConnection();
        funcDAO = new FuncionarioDAO();
        funcDAO.setConnection();
        andarDAO.setFuncDAO(funcDAO);
    }
    
    @BeforeEach
    public void controlFuncs() {
    	funcDAO.cadastrarFuncionario(new Funcionario(0, "Pedro", "pedro@hotmail.com", "pedro", "0704"));
    }

    @AfterEach
    public void tearDownClass() {
        andarDAO.excluirTudoTestes();
        funcDAO.excluirTudoBase();
    }
    
    @AfterAll
    public static void limparFunc(){
        funcDAO.excluirTudoBase();
    }
    
    @Test
    public void testCadastrarAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecQuarto = new GerenciaQuarto();
        var andar = new GerenciaAndar();
        var quartoMock = mock(QuartoDAO.class);
        var andarMock = mock(AndarDAO.class);
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.getFuncionarios(anyInt())).thenReturn(func);
        when(andarMock.buscarUltimoAndar()).thenReturn(1);
        when(andarMock.atualizarUltimoAndar()).thenReturn(2);
        when(andarMock.cadastarAndar(anyObject())).thenReturn(true);
        when(quartoMock.cadastarQuarto(anyObject())).thenReturn(true);
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        
        assertTrue(andar.criarNewAndar(1, 1));
    }
    
    @Test
    public void testExcluirAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecQuarto = new GerenciaQuarto();
        var andar = new GerenciaAndar();
        var quartoMock = mock(QuartoDAO.class);
        var andarMock = mock(AndarDAO.class);
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.getFuncionarios(anyInt())).thenReturn(func);
        when(andarMock.buscarUltimoAndar()).thenReturn(1);
        when(andarMock.atualizarUltimoAndar()).thenReturn(2);
        when(andarMock.cadastarAndar(anyObject())).thenReturn(true);
        when(andarMock.excluirAndar(anyObject())).thenReturn(true);
        when(andarMock.buscarInformacaoNumeroAndar(anyInt())).thenReturn(new Andar(1, 1, func));
        when(quartoMock.cadastarQuarto(anyObject())).thenReturn(true);
        when(quartoMock.excluirQuarto(anyObject())).thenReturn(true);
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        andar.criarNewAndar(1, 1);
        assertTrue(andar.excluirAndar(1));
    }
    
    @Test
    public void testBuscarAndarMock(){
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecQuarto = new GerenciaQuarto();
        var andar = new GerenciaAndar();
        var quartoMock = mock(QuartoDAO.class);
        var andarMock = mock(AndarDAO.class);
        var funcMock = mock(FuncionarioDAO.class);
        
        when(funcMock.getFuncionarios(anyInt())).thenReturn(func);
        when(andarMock.buscarInformacaoNumeroAndar(anyInt())).thenReturn(new Andar(1, 2, func));
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        assertEquals(2, andar.buscarAndar(1).getNumAndar());
    }

    @Test
    public void testCadastraQtdAndarInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        var res = assertThrows(RuntimeException.class, () -> {
        	gerecAndar.criarNewAndar(-1, 1);
        }).getMessage();
        
        
        assertEquals("ERRO!! Porfavor insira um número de andares e quarto válido para ser cadastrado",res);
    }
    
    @Test
    public void testCadastraQtdQuartoInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        var res = assertThrows(RuntimeException.class, () -> {
        	gerecAndar.criarNewAndar(1, -1);
        }).getMessage();
        
        
        assertEquals("ERRO!! Porfavor insira um número de andares e quarto válido para ser cadastrado",res);
    }
    
    @Test
    public void testCadastraNovoAndarQuartoValido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(2, 1);
        
        //Pegando no banco o número do andar cadastrado
        assertEquals(1,gerecAndar.buscarAndar(1).getNumAndar());
    }
    
    @Test
    public void testCadastraExistenteAndarQuartoValido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);

        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(2, 1);
        gerecAndar.criarNewAndar(2, 1);
        
        //Pegando no banco o número do andar cadastrado
        assertEquals(4,gerecAndar.buscarAndar(4).getNumAndar());
    }

    @Test
    public void testCadastraAndarQuartoInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));        
        
        var res = assertThrows(RuntimeException.class, () -> {
            gerecAndar.criarNewAndar(-1, 1);
        }).getMessage();
        
        assertEquals("ERRO!! Porfavor insira um número de andares e quarto válido para ser cadastrado", res);
    }

    @Test
    public void testBuscaAndarValido() {
        Andar and = new Andar(1, 3, new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704"));
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(4, 1);
        
        assertEquals(1, gerecAndar.buscarAndar(1).getNumAndar());
        assertEquals(2, gerecAndar.buscarAndar(2).getNumAndar());
        assertEquals(3, gerecAndar.buscarAndar(3).getNumAndar());
        assertEquals(4, gerecAndar.buscarAndar(4).getNumAndar());
    }

    @Test
    public void testBuscaAndarInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(4, 1);
        
        var res = assertThrows(RuntimeException.class, () -> {
            gerecAndar.buscarAndar(-1);
        }).getMessage();
        
        assertEquals("ERRO!! Porfavor insira um número de andar válido para ser cadastrado", res);
    }

    @Test
    public void testBuscaAndarNumInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(4, 1);
        
        var res = assertThrows(RuntimeException.class, () -> {
            gerecAndar.buscarAndar(20);
        }).getMessage();
        
        assertEquals("Erro na busca do Andar", res);
    }

    @Test
    public void testBuscaTodosAndar() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(4, 1);

        assertEquals(1, gerecAndar.buscTodosAndares().get(0).getNumAndar());
        assertEquals(2, gerecAndar.buscTodosAndares().get(1).getNumAndar());
        assertEquals(3, gerecAndar.buscTodosAndares().get(2).getNumAndar());
        assertEquals(4, gerecAndar.buscTodosAndares().get(3).getNumAndar());
    }

    @Test
    public void testExcluiAndar() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(2, 1);
        
        //Excluindo o 2 andar
        gerecAndar.excluirAndar(2);
        
        var res = assertThrows(RuntimeException.class, () -> {
        	gerecAndar.buscarAndar(2);
        }).getMessage();
        
        assertEquals("Erro na busca do Andar", res);
    }

    @Test
    public void testExcluiAndarInexistente() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        
        gerecAndar.criarNewAndar(2, 1);
        
        var res = assertThrows(RuntimeException.class, () -> {
            gerecAndar.excluirAndar(-1);
        }).getMessage();
        
        assertEquals(res, "ERRO!! Porfavor insira um número de andar válido para ser excluido");
    }

    /*@Test
    public void TesteExcluiAndarInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        var mockGerecQuarto = mock(GerenciaQuarto.class);
        mockGerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        gerecAndar.criarNewAndar(2, 1);
        var res = assertThrows(RuntimeException.class, () -> {
            gerecAndar.excluirAndar(50);
        }).getMessage();
        assertEquals(res, "Erro na busca do Andar");
    }*/
    
    @Test
    public void testThrowsExceptionCadastrar() {
        
    	funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com","pedro","0704"));
    	
        var res = assertThrows(RuntimeException.class, () -> {
        	andarDAO.cadastarAndar(new Andar(0, -30, new Funcionario(20, "Carlos", "carlo@hotmail.com", "carlos", "123")));
        }).getMessage();
        
        assertEquals("Erro no Cadastro do andar verificar para encontar o erro", res);
    }

    @Test
    public void testThrowsExceptionExclusao() {
        
    	funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com","pedro","0704"));
    	
        var res = assertThrows(RuntimeException.class, () -> {
        	andarDAO.excluirAndar(new Andar(0, 2, new Funcionario(20, "Carlos", "carlo@hotmail.com", "carlos", "123")));
        }).getMessage();
        
        assertEquals("Erro na exclusão", res);
    }
    
    @Test
    public void testThrowsExceptionBuscarIdAndar() {    
    	
    	andarDAO.cadastarAndar(new Andar(0, 2, new Funcionario(1, "Carlos", "carlo@hotmail.com", "carlos", "123")));       
        
        assertEquals(2, andarDAO.buscarIdAndar(1).getNumAndar());
    }
    
    @Test
    public void testThrowsExceptionBuscarIdAndarInvalido() {    	
        var res = assertThrows(RuntimeException.class, () -> {
        	andarDAO.buscarIdAndar(1);
        }).getMessage();
        
        assertEquals("Erro na busca do Andar", res);
    }

}
