/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import controller.GerenciaQuarto;
import dao.AndarDAO;
import dao.FuncionarioDAO;
import dao.QuartoDAO;
import java.util.ArrayList;
import model.Andar;
import model.Funcionario;
import model.Quarto;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TesteGerenciaQuarto {

    private static FuncionarioDAO funcDAO;
    private static AndarDAO andarDAO;
    private static QuartoDAO quartoDAO;

    @BeforeAll
    public static void setUpClass() {
        andarDAO = new AndarDAO();
        andarDAO.setConnection();
        quartoDAO = new QuartoDAO();
        quartoDAO.setConnection();
        funcDAO = new FuncionarioDAO();
        funcDAO.setConnection();
        funcDAO.cadastrarFuncionario(new Funcionario(0, "Pedro", "pedro@hotmail.com", "pedro", "0704"));
    }

    @AfterEach
    public void tearDownClass() {
        andarDAO.excluirTudoTestes();
    }
    
    @AfterAll
    public static void limparFunc(){
        funcDAO.excluirTudoBase();
    }

    //Teste de Excluir  Quartos Validos
    @Test
    public void testExcluirQuartoValido() {
        var gerecQuarto = new GerenciaQuarto();
        var andar = new GerenciaAndar();
        quartoDAO.setAndarDAO(andarDAO);
        andar.setAndarDAO(andarDAO);
        gerecQuarto.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        andar.setQuartoDAO(gerecQuarto);
        andar.setFuncionario(funcDAO.getFuncionarios(1));
        andar.criarNewAndar(1, 1);
        assertEquals(gerecQuarto.excluirQuarto(1), true);
    }
    
    @Test
    public void testExcluirQuartoValidoMock() {
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
        when(quartoMock.excluirQuarto(anyObject())).thenReturn(true);
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        
        
        assertTrue(andar.criarNewAndar(1, 1));
        
    }

    @Test
    public void testExcluirQuartoInvalidos() {
        var gerecQuarto = new GerenciaQuarto();
        gerecQuarto.setQuartoDAO(quartoDAO);
        var mockGerecQuarto = mock(GerenciaQuarto.class);
        mockGerecQuarto.setQuartoDAO(quartoDAO);
        var res = assertThrows(RuntimeException.class, () -> {
            gerecQuarto.excluirQuarto(-1);
        }).getMessage();
        assertEquals(res, "Erro Id Não existente");
    }

    @Test
    public void testExcluirQuartoNaoCadastrado() {
        var gerecQuarto = new GerenciaQuarto();
        gerecQuarto.setQuartoDAO(quartoDAO);
        var mockGerecQuarto = mock(GerenciaQuarto.class);
        mockGerecQuarto.setQuartoDAO(quartoDAO);
        var res = assertThrows(RuntimeException.class, () -> {
            gerecQuarto.excluirQuarto(50);
        }).getMessage();
        assertEquals(res, "Erro na busca do Quarto");
    }
    
    @Test
    public void testBuscarTodosQuartosMock() {
        var func = new Funcionario(1, "Pedro", "pedro@hotmail.com", "pedro", "0704");
        var gerecQuarto = new GerenciaQuarto();
        var andar = new GerenciaAndar();
        var quartoMock = mock(QuartoDAO.class);
        var andarMock = mock(AndarDAO.class);
        var funcMock = mock(FuncionarioDAO.class);
        ArrayList<Quarto> qtd = new ArrayList();
        qtd.add(new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, func)));
        qtd.add(new Quarto(2, 102, 2, "Basico", 1, "Desc", 102, new Andar(1, 1, func)));
        qtd.add(new Quarto(3, 103, 2, "Basico", 1, "Desc", 103, new Andar(1, 1, func)));
        
        when(funcMock.getFuncionarios(anyInt())).thenReturn(func);
        when(andarMock.buscarUltimoAndar()).thenReturn(1);
        when(andarMock.atualizarUltimoAndar()).thenReturn(2);
        when(andarMock.cadastarAndar(anyObject())).thenReturn(true);
        when(quartoMock.cadastarQuarto(anyObject())).thenReturn(true);
        when(quartoMock.buscarTodosQuarto()).thenReturn(qtd);
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        andar.criarNewAndar(3, 3);
        
        assertEquals(101, gerecQuarto.buscTodosQuartos().get(0).getNumQuarto());
        
    }

    @Test
    public void testBuscarTodosQuartos() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        quartoDAO.setAndarDAO(andarDAO);
                
        funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
        
        andarDAO.cadastarAndar(new Andar(0, 1, funcDAO.getFuncionarios(1)));
        
        quartoDAO.cadastarQuarto(new Quarto(0, 101, 2, "Básico", 1, "", 101, andarDAO.buscarIdAndar(1)));
        quartoDAO.cadastarQuarto(new Quarto(0, 102, 2, "Básico", 1, "", 102, andarDAO.buscarIdAndar(1)));
        quartoDAO.cadastarQuarto(new Quarto(0, 103, 2, "Básico", 1, "", 103, andarDAO.buscarIdAndar(1)));
        quartoDAO.cadastarQuarto(new Quarto(0, 104, 2, "Básico", 1, "", 104, andarDAO.buscarIdAndar(1)));
        
        var res = gerecQuarto.buscTodosQuartos();
        
        assertEquals(101, res.get(0).getNumQuarto());
        assertEquals(102, res.get(1).getNumQuarto());
        assertEquals(103, res.get(2).getNumQuarto());
        assertEquals(104, res.get(3).getNumQuarto());
    }
    
    @Test
    public void testEtidarQuartoValidoMock() {
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
        when(quartoMock.editarQuarto(anyObject())).thenReturn(true);
        
        andar.setAndarDAO(andarMock);
        andar.setFuncionario(funcMock.getFuncionarios(1));
        andar.setQuartoDAO(gerecQuarto);
        
        gerecQuarto.setAndarDAO(andarMock);
        gerecQuarto.setQuartoDAO(quartoMock);
        andar.criarNewAndar(3, 3);
        
        assertTrue(gerecQuarto.editarQuarto(new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, func)), "Luxo", 1, 1, "Teste descricao"));
        
    }
    
    //Verificar
    @Test
    public void testEtidarQuartoValido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        var mockGerecQuarto = mock(GerenciaQuarto.class);
        mockGerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        gerecAndar.criarNewAndar(1, 1);
        var quarto = gerecQuarto.buscarQuarto(101);
        //Verificar  não edito o idQuarto, numQuarto, fora o contato e Andar chave
        var res = gerecQuarto.editarQuarto(quarto,"Normal", 2, 2, "Quarto Com Tv");
        assertEquals(true, res);
    }
        //Verificar
    @Test
    public void testEtidarQuartoInvalido() {
        var gerecAndar = new GerenciaAndar();
        var gerecQuarto = new GerenciaQuarto();
        gerecAndar.setAndarDAO(andarDAO);
        gerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setQuartoDAO(gerecQuarto);
        var mockGerecQuarto = mock(GerenciaQuarto.class);
        mockGerecQuarto.setQuartoDAO(quartoDAO);
        gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
        gerecAndar.criarNewAndar(1, 1);
        var quarto = gerecQuarto.buscarQuarto(101);
        //Verificar  não edito o idQuarto, numQuarto, fora o contato e Andar chave
        var res = gerecQuarto.editarQuarto(quarto ,"", 2, -2, "Quarto Com Tv");
        assertEquals(res,true);
    }
    
   @Test
   public void testEtidarQuartoInfosIguais() {
       var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       
       gerecAndar.criarNewAndar(1, 1);
       
       var quarto = gerecQuarto.buscarQuarto(101);
       //Verificar  não edito o idQuarto, numQuarto, fora o contato e Andar chave
       assertFalse(gerecQuarto.editarQuarto(quarto ,"Básico", 2, 1, "Quarto básico possui arcondicionado, frigobar, tv com canais por assinatura"));
   }
   
   @Test
   public void testBuscarNumQuartoMenorZero() {
       var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       
       gerecAndar.criarNewAndar(1, 1);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   gerecQuarto.buscarQuarto(-1);
       }).getMessage();
       
       assertEquals("ERRO!! Porfavor insira um número de quarto válido para ser buscado", res);
   }
   
   @Test
   public void testBuscarNumAndarMenorZero() {
       var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       
       gerecAndar.criarNewAndar(1, 1);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   gerecQuarto.buscarAndar(-1);
       }).getMessage();
       
       assertEquals("ERRO!! Porfavor insira um número de andar válido para ser buscado", res);
   }
   
   @Test
   public void testBuscarNumAndar() {
       var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       andarDAO.cadastarAndar(new Andar(0, 1, funcDAO.getFuncionarios(1)));
       
       assertEquals(1, gerecQuarto.buscarAndar(1).getNumAndar());
   }
   
   @Test
   public void testExcluiQuartoAndar() {
       var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       andarDAO.cadastarAndar(new Andar(0, 1, funcDAO.getFuncionarios(1)));
       quartoDAO.cadastarQuarto(new Quarto(0, 101, 2, "Básico", 1, "", 101, andarDAO.buscarIdAndar(1)));
       quartoDAO.cadastarQuarto(new Quarto(0, 102, 2, "Básico", 1, "", 102, andarDAO.buscarIdAndar(1)));
       
       assertTrue(gerecQuarto.excluirQuartoAndar(andarDAO.buscarIdAndar(1)));
   }
   
   @Test
   public void testThrowExceptionBuscarInfoNumQuarto() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.buscarInformacaoNumeroQuarto(30);
       }).getMessage();
       
       assertEquals("Erro na busca do Quarto", res);
   }
   
   @Test
   public void testThrowExceptionBuscarTodosQuartos() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.buscarTodosQuarto();
       }).getMessage();
       
       assertEquals("Erro na busca de todos os quartos", res);
   }
   
   @Test
   public void testThrowExceptionExclusaoQuartoAndar() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.excluirQuartoAndar(new Andar(0, 1, funcDAO.getFuncionarios(1)));
       }).getMessage();
       
       assertEquals("Erro na Exclusão dos Quartos por andar", res);
   }
   
   @Test
   public void testThrowExceptionExclusaoQuarto() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.excluirQuarto(new Quarto(0,101,2,"Básico", 1, "Desc",101,new Andar(0, 1, funcDAO.getFuncionarios(1))));
       }).getMessage();
       
       assertEquals("Erro na exclusão do quarto, não encontrado", res);
   }
  
   
   @Test
   public void testThrowExceptionEditarQuarto() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.editarQuarto(new Quarto(0,101,2,"Básico", 1, "Desc",101,new Andar(0, 1, funcDAO.getFuncionarios(1))));
       }).getMessage();
       
       assertEquals("Erro em Alterar o Cadastro do Quarto", res);
   }
  
   @Test
   public void testThrowExceptionCadatrarQuarto() {
	   var gerecAndar = new GerenciaAndar();
       var gerecQuarto = new GerenciaQuarto();
       gerecAndar.setAndarDAO(andarDAO);
       gerecQuarto.setQuartoDAO(quartoDAO);
       gerecQuarto.setAndarDAO(andarDAO);
       gerecAndar.setQuartoDAO(gerecQuarto);
       gerecAndar.setFuncionario(funcDAO.getFuncionarios(1));
       quartoDAO.setAndarDAO(andarDAO);
       funcDAO.cadastrarFuncionario(new Funcionario(0,"Pedro","pedro@hotmail.com", "pedro","123"));
       andarDAO.setFuncDAO(funcDAO);
       
       var res = assertThrows(RuntimeException.class, () -> {
    	   quartoDAO.cadastarQuarto(new Quarto(0,101,2,"Básico", 1, "Desc",101,new Andar(0, 1, funcDAO.getFuncionarios(1))));
       }).getMessage();
       
       assertEquals("Erro no Cadastro do Quarto verificar para encontar o erro", res);
   }
   
}
