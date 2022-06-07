/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.ControllerData;
import dao.AndarDAO;
import dao.FuncionarioDAO;
import dao.QuartoDAO;
import dao.ReservaDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Andar;
import model.Funcionario;
import model.Quarto;
import model.Reserva;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TesteReserva {
    
    private static FuncionarioDAO funcDAO;
    private static ReservaDAO reservDAO;
    private static QuartoDAO quartoDAO;
    private static AndarDAO andarDAO;
	
    @BeforeAll
    public static void defineAll(){
        funcDAO = new FuncionarioDAO();
        funcDAO.setConnection();
        andarDAO = new AndarDAO();
        andarDAO.setConnection();
        andarDAO.setFuncDAO(funcDAO);
        quartoDAO = new QuartoDAO();
        quartoDAO.setConnection();
        quartoDAO.setAndarDAO(andarDAO);
        reservDAO = new ReservaDAO();
        reservDAO.setConnection();
        reservDAO.setFuncionarioDAO(funcDAO);
        reservDAO.setQuartoDAO(quartoDAO);
    }
    
    @BeforeEach
    public void setUpClass() {
        funcDAO.cadastrarFuncionario(new Funcionario(0, "Pedro", "pedro@hotmail.com", "pedro", "0704"));
        andarDAO.cadastarAndar(new Andar(0, 1, funcDAO.getFuncionarios(1)));
        quartoDAO.cadastarQuarto(new Quarto(0, 101, 2, "Básico", 1, "Desc", 101, andarDAO.buscarIdAndar(1)));
    }

    @AfterEach
    public void limpaTabelaFuncionarios() {
        funcDAO.excluirTudoBase();
    }

    @Test
    public void testeCadastroReservaMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.cadastrarReserva(anyObject())).thenReturn(true);
        when(mockFuncDAO.getFuncionarios(anyInt())).thenReturn(funcEx);
        when(mockQuartoDAO.buscarIdQuarto(anyInt())).thenReturn(quartEx);
        when(mockQuartoDAO.buscarInformacaoNumeroQuarto(anyInt())).thenReturn(quartEx);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        genReserva.setFuncionarioDAO(mockFuncDAO);
        genReserva.setQuartoDAO(mockQuartoDAO);
        
        assertEquals(true, genReserva.realizaReserva("04-12-2022", "07-12-2022", "carlos", "45295985865", "14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockEntradaNull() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva(null, "07-12-2021", "carlos", "548135464", "14-996977494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockEntradaEmptyFormatacao() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("", "07-12-2021", "carlos", "548135464", "14-996977494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockEntradaEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("  -  -    ", "07-12-2021", "carlos", "548135464", "14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockNomeEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "", "548135464", "14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockNomeNulll() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", null, "548135464", "14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockDocNulll() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", null,"14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockDocEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "","14-99697-7494", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockContatoEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        var resMock = mock(GerenciaReserva.class);
        
        when(resMock.validaContatoCliente(anyString())).thenReturn(false);
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "548135464", "", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockContatoEmptyFormatacao() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "548135464", "  -     -    ", 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockContatoNull() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "548135464", null, 1, 1));
    }
    
    @Test
    public void testeCadastroReservaMockFuncIdMenorUm() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "14", "14-99697-7494", 0, 1));
    }
    
    @Test
    public void testeCadastroReservaMockQuartoIdMenorUm() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.realizaReserva("27-11-2021", "07-12-2021", "carlos", "14", "14-99697-7494", 1, 0));
    }
    
    @Test
    public void testValidaCadastroReservaContatoErrado() throws Exception{        
        var genReserva = new GerenciaReserva();
        genReserva.setReservaDAO(reservDAO);
        
        assertFalse(genReserva.realizaReserva("27-11-2022", "07-12-2022", "carlos", "14", "14-99697-749", 1, 1));
    } 
    
    @Test
    public void testValidaEditarReservaContatoErrado() throws Exception{        
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27-11-2022", "12-12-2022", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscaReserva(anyString(), anyInt())).thenReturn(reservEx);
        when(mockReservDAO.editarReserva(anyObject())).thenReturn(false);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertFalse(genReserva.editarReserva("27-11-2021", 101, "carlos", "54650031", "14-95643-546 "));
    } 
    
    @Test
    public void testeEditarReservaMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscaReserva(anyString(), anyInt())).thenReturn(reservEx);
        when(mockReservDAO.editarReserva(anyObject())).thenReturn(true);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals(true, genReserva.editarReserva("27/11/2021", 101, "carlos", "54650031", "14-95643-5462"));
    }
    
    @Test
    public void testeEditarReservaMockEntradaNull() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva(null, 1, "carlos", "548135464", "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockEntradaEmptyFormatacao() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("", 1, "carlos", "548135464", "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockEntradaEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("  /  /    ", 1, "carlos", "548135464", "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockNomeEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "", "548135464", "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockNomeNulll() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, null, "548135464", "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockDocNulll() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "carlos", null, "14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockDocEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "carlos", "","14-99697-7494"));
    }
    
    @Test
    public void testeEditarReservaMockContatoEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "carlos", "548135464", ""));
    }
    
    @Test
    public void testeEditarReservaMockContatoEmptyFormatacao() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "carlos", "548135464", "  -     -    "));
    }
    
    @Test
    public void testeEditarReservaMockContatoNull() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 1, "carlos", "548135464", null));
    }
    
    @Test
    public void testeEditarReservaMockQuartoIdMenorUm() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.editarReserva("27/11/2021", 0, "carlos", "1546624", "14-99697-7494"));
    }
    
    @Test
    public void testeExcluirReservaMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscaReserva(anyString(), anyInt())).thenReturn(reservEx);
        when(mockReservDAO.excluirReserva(anyObject())).thenReturn(true);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals(true, genReserva.excluirReserva("27/11/2021", 101));
    }
    
    @Test
    public void testeExcluirReservaMockEntradaNull() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.excluirReserva(null, 1));
    }
    
    @Test
    public void testeExcluirReservaMockEntradaEmptyFormatacao() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.excluirReserva("", 1));
    }
    
    @Test
    public void testeExcluirReservaMockEntradaEmpty() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.excluirReserva("  /  /    ", 1));
    }
    
    @Test
    public void testeExcluirReservaMockQuartoIdMenorUm() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(false, genReserva.excluirReserva("27/11/2021", 0));
    }
    
    @Test
    public void testeBuscaTodasReservasMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "pedro@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscTodasReservas()).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("12/12/2021", genReserva.buscTodasReservas().get(0).getSaidaCliente());
    }
    
    @Test
    public void testeBuscaReservasEntradaSaidaQuartoMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasEntradaSaidaNumQuarto(anyString(), anyString(), anyInt())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("12/12/2021", genReserva.buscarReservas("27/11/2021", "12/12/2021", 101, "", "").get(0).getSaidaCliente());
    }
    
    @Test
    public void testeBuscaReservasEntradaQuartoMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasEntradaNumQuarto(anyString(), anyInt())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("27/11/2021", genReserva.buscarReservas("27/11/2021", "", 101, "", "").get(0).getEntradaReserva());
    }
    
    @Test
    public void testeBuscaReservasSaidaQuartoMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasSaidaNumQuarto(anyString(), anyInt())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("12/12/2021", genReserva.buscarReservas("", "12/12/2021", 101, "", "").get(0).getSaidaCliente());
    }
    
    @Test
    public void testeBuscaReservasNomeDocCliMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasNomeDocCli(anyString(), anyString())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("carlos", genReserva.buscarReservas("", "", 0, "carlos", "1421481").get(0).getClienteReserva());
    }
    
    @Test
    public void testeBuscaReservasNomeCliMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasNomeCli(anyString())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("carlos", genReserva.buscarReservas("", "", 0, "carlos", "").get(0).getClienteReserva());
    }
    
    @Test
    public void testeBuscaReservasDocCliMock() throws Exception{
        var funcEx = new Funcionario(1, "carlos", "carlos@hotmail.com", "carlos", "123");
        var quartEx = new Quarto(1, 101, 2, "Basico", 1, "Desc", 101, new Andar(1, 1, funcEx));
        var reservEx = new Reserva(1, "27/11/2021", "12/12/2021", "carlos", "14-99697-7494", "5465456", funcEx, quartEx);
        ArrayList<Reserva> reserva = new ArrayList();
        
        reserva.add(reservEx);
        
        var mockReservDAO = mock(ReservaDAO.class);
        var mockFuncDAO = mock(FuncionarioDAO.class);
        var mockQuartoDAO = mock(QuartoDAO.class);
        
        when(mockReservDAO.buscReservasDocCli(anyString())).thenReturn(reserva);
        
        var genReserva = new GerenciaReserva();
        
        genReserva.setReservaDAO(mockReservDAO);
        
        assertEquals("5465456", genReserva.buscarReservas("", "", 0, "", "5465456").get(0).getDocumentoReserva());
    }
    
    @Test
    public void testeBuscaReservasNumQuartoMenorUm() throws Exception{
        var genReserva = new GerenciaReserva();
        
        assertEquals(true, genReserva.buscarReservas(null, null, 0, "carlos", "51561785" ).isEmpty());
    }
    
    @Test
    public void testeCadastroReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2022", "07-12-2022", "Amaral", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("2022-11-27", reservDAO.buscaReserva("2022-11-27", 101).getEntradaReserva());
    }
    
    @Test
    public void testeEditarReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2022", "07-12-2022", "Amaral", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        res.setClienteReserva("Amaral");
        res.setDocumentoReserva("2329");
        res.setTelefoneReserva("55-555");
        res.setEntradaReserva("2022-11-27");
        res.setSaidaCliente("2022-12-07");
        reservDAO.editarReserva(res);
        assertEquals("Amaral", reservDAO.buscaReserva("2022-11-27", 101).getClienteReserva());
    }
    
    @Test
    public void testeBuscarTodasReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2021", "07-12-2021", "Pedro", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        Reserva res2 = new Reserva(1, "27-11-2021", "07-12-2021", "Carlos", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        Reserva res3 = new Reserva(2, "27-11-2021", "07-12-2021", "Jonas", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        Reserva res4 = new Reserva(3, "27-11-2021", "07-12-2021", "Marcos", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        reservDAO.cadastrarReserva(res2);
        reservDAO.cadastrarReserva(res3);
        reservDAO.cadastrarReserva(res4);
        assertEquals("Jonas", reservDAO.buscTodasReservas().get(2).getClienteReserva());
    }
    
    @Test
    public void testeBuscarEntradaSaidaQuartoReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2022", "07-12-2022", "Amaral", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("Amaral", reservDAO.buscReservasEntradaSaidaNumQuarto("2022-11-27", "2022-12-07", 101).get(0).getClienteReserva());
    }
    
    @Test
    public void testeBuscarEntradaQuartoReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2021", "07-12-2021", "Carlinho", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("Carlinho", reservDAO.buscReservasEntradaNumQuarto("2021-11-27", 101).get(0).getClienteReserva());
    }
    
    @Test
    public void testeBuscarSaidaQuartoReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2022", "07-12-2022", "Amarguinho", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("Amarguinho", reservDAO.buscReservasSaidaNumQuarto("2022-12-07", 101).get(0).getClienteReserva());
    }
    
    @Test
    public void testeBuscarNomeDocumentoReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2021", "07-12-2021", "Castiel", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals(101, reservDAO.buscReservasNomeDocCli("Castiel", "1412441").get(0).getQuarIdQuarto().getNumQuarto());
    }
    
    @Test
    public void testeBuscarNomeReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2021", "07-12-2021", "Castiel", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("2021-11-27", reservDAO.buscReservasNomeCli("Castiel").get(0).getEntradaReserva());
    }
    
    @Test
    public void testeBuscarDocumentoReservas() throws Exception{
        var genReserva = new GerenciaReserva();
        
        Reserva res = new Reserva(0, "27-11-2021", "07-12-2021", "Castiel", "14-99697-7494", "1412441", funcDAO.getFuncionarios(1), quartoDAO.buscarIdQuarto(1));
        reservDAO.cadastrarReserva(res);
        assertEquals("2021-11-27", reservDAO.buscReservasDocCli("1412441").get(0).getEntradaReserva());
    }
    
    @Test
    public void testDataValida() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data1 = sdf.parse("20-12-2022");
        Date data2 = sdf.parse("25-12-2022");
        var dataController = new ControllerData();
        
        assertTrue(dataController.cadastrarData(data1,data2));
    }

    @Test
    public void testDataMaiorQue14Dias() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data1 = sdf.parse("15-12-2021");
        Date data2 = sdf.parse("30-12-2021");
        var dataController = new ControllerData();

        var res = assertThrows(Exception.class,() -> {
            dataController.cadastrarData(data1,data2);
        }).getMessage();
        assertEquals("ERRO:Data Inicio não pode ser inferior a data atual",res);
    }

    @Test
    public void testDataFimAnteriorADataInicio() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data1 = sdf.parse("15-12-2022");
        Date data2 = sdf.parse("01-12-2022");
        var dataController = new ControllerData();

        var res = assertThrows(Exception.class,() -> {
            dataController.cadastrarData(data1,data2);
        }).getMessage();
        assertEquals("ERRO:Data Fim não pode ser anterior a data inicio",res);
    }

    @Test
    public void testDataFimIgualADataInicio() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data1 = sdf.parse("15-12-2022");
        Date data2 = sdf.parse("15-12-2022");
        var dataController = new ControllerData();

        var res = assertThrows(Exception.class,() -> {
            dataController.cadastrarData(data1,data2);
        }).getMessage();
        assertEquals("ERRO:Data Fim não pode ser igual a data inicio",res);
    }

    @Test
    public void testDataInicioAnteriorADataAtual() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date data1 = sdf.parse("29-11-2021");
        Date data2 = sdf.parse("02-12-2021");
        var dataController = new ControllerData();

        var res = assertThrows(Exception.class,() -> {
            dataController.cadastrarData(data1,data2);
        }).getMessage();
        assertEquals("ERRO:Data Inicio não pode ser inferior a data atual",res);
    }
    
    @Test
    public void testConvertStringDate() {
        var controlRes = new GerenciaReserva();
        
        var res = assertThrows(ParseException.class,() -> {
            controlRes.convertStringDate("afas");
        }).getMessage();
        
        assertEquals("Não foi possivel converter a data informada",res);
    }
    
    @Test
    public void testCpfInvalido() throws Exception{
       var genReserva = new GerenciaReserva();

       genReserva.setReservaDAO(reservDAO);
       genReserva.setFuncionarioDAO(funcDAO);
       genReserva.setQuartoDAO(quartoDAO);

       assertFalse(genReserva.realizaReserva("04-12-2022", "07-12-2022", "Jorge", "999999999", "14-996977494", 1, 101));
    }
    @Test
    public void testCpfValido() throws Exception{
       var genReserva = new GerenciaReserva();

       genReserva.setReservaDAO(reservDAO);
       genReserva.setFuncionarioDAO(funcDAO);
       genReserva.setQuartoDAO(quartoDAO);

       assertTrue(genReserva.realizaReserva("04-07-2022", "12-07-2022", "Jorge", "45295985865", "14-99697-7494", 1, 101));
    }
}
