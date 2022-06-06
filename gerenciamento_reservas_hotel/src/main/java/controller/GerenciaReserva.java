/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import View.TelaReservas;
import dao.FuncionarioDAO;
import dao.QuartoDAO;
import dao.ReservaDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Quarto;
import model.Reserva;

public class GerenciaReserva {
    //Criação do Objeto QuartoDao do pacote DAO
    private QuartoDAO quartoDAO = new QuartoDAO();
    private FuncionarioDAO funcDAO = new FuncionarioDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();
    private ControllerData controllDt = new ControllerData();
    
    public void setControlData(ControllerData controllDt){
        this.controllDt = controllDt;
    }
    
    public void setQuartoDAO(QuartoDAO quartoDAO){
        this.quartoDAO = quartoDAO;
    }
    
    public void setFuncionarioDAO(FuncionarioDAO funcDAO){
        this.funcDAO = funcDAO;
    }
    
    public void setReservaDAO(ReservaDAO reservaDAO){
        this.reservaDAO = reservaDAO;
    }
    
    public boolean validaContatoCliente(String contato) throws RuntimeException{
        boolean var = false;
        String[] dig = contato.split("-");
        if(dig[0].indexOf(' ') > 0||dig[1].indexOf(' ') > 0||dig[2].indexOf(' ') > 0){
            throw new RuntimeException("ERRO: Verifique o campo de contado, pode estar faltando algum caractere");
        }else{
            var = true;
        }
        return var;
    }
    
    public Date convertStringDate(String date) throws ParseException{
        try {
            SimpleDateFormat sddia = new SimpleDateFormat("dd-MM-yyyy");//cria um obj de formatação de data
            Date dataParse = sddia.parse(date);
            
            return dataParse;
        } catch (ParseException ex) {
            throw new ParseException("Não foi possivel converter a data informada", 1);
        }
    }

    public boolean realizaReserva (String entrada, String saida, String cliNome, String docCli, String telContato, int funcId, int quartoId) throws Exception {
        try {
            Quarto qt = quartoDAO.buscarInformacaoNumeroQuarto(quartoId);
            quartoId = qt.getIdQuarto();
            
            if(!entrada.equals("")&&entrada!=null&&!entrada.equals("  -  -    ")){
                if(!saida.equals("")&&saida!=null&&!saida.equals("  -  -    ")){
                    if(controllDt.cadastrarData(convertStringDate(entrada), convertStringDate(saida))){
                        if(!cliNome.equals("")&&cliNome!=null){
                            if(!docCli.equals("")&&docCli!=null){
                                if(!telContato.equals("")&&!telContato.equals("  -     -    ")&&telContato!=null&&validaContatoCliente(telContato)==true){
                                    if(funcId>=1){
                                        if(quartoId>=1){
                                            //Preciso calcular a saida do quarto, a partir da entrada
                                            Reserva reserv = new Reserva(0, entrada, saida, cliNome, telContato, docCli, funcDAO.getFuncionarios(funcId),  quartoDAO.buscarIdQuarto(quartoId));
                                            reservaDAO.cadastrarReserva(reserv);
                                            return true;
                                        }
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "ERRO:Verifique o campo de contato");
                                }
                            }
                        }
                    }
                }
            }
        }catch (RuntimeException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public boolean editarReserva(String entrada, int numQuarto, String nomeAlt, String docAlt, String contatoAlt) {        
        try {
            if(!entrada.equals("")&&entrada!=null&&!entrada.equals("    -  -  ")){
                if(!nomeAlt.equals("")&&nomeAlt!=null){
                    if(!docAlt.equals("")&&docAlt!=null){
                        if(!contatoAlt.equals("")&&!contatoAlt.equals("  -     -    ")&&contatoAlt!=null&&validaContatoCliente(contatoAlt)==true){
                            if(numQuarto>=1){
                                Reserva reserv = reservaDAO.buscaReserva(entrada, numQuarto);
                                reserv.setClienteReserva(nomeAlt);
                                reserv.setDocumentoReserva(docAlt);
                                reserv.setTelefoneReserva(contatoAlt);
                                reservaDAO.editarReserva(reserv);
                                return true;
                            }
                        }
                    }
                }
            }
        }catch (RuntimeException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public boolean excluirReserva(String entradaReserva, int numQuarto){ 
        try {
            if(!entradaReserva.equals("")&&entradaReserva!=null&&!entradaReserva.equals("  /  /    ")){
                if (numQuarto > 0 ){
                    Reserva reserv = reservaDAO.buscaReserva(entradaReserva, numQuarto);
                    reservaDAO.excluirReserva(reserv);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public ArrayList<Reserva> buscarReservas(String entrada, String saida, int numQuarto, String nomeCli, String docCli){
        ArrayList<Reserva> reservas = new ArrayList();
        try {
            if(numQuarto > 0 &&!entrada.equals("")&&entrada!=null&&!entrada.equals("  /  /    ")&&!saida.equals("")&&saida!=null&&!saida.equals("  /  /    ")){
                reservas = reservaDAO.buscReservasEntradaSaidaNumQuarto(entrada, saida, numQuarto);
            }else{
                if(numQuarto > 0 &&!entrada.equals("")&&entrada!=null&&!entrada.equals("  /  /    ")){
                    reservas = reservaDAO.buscReservasEntradaNumQuarto(entrada, numQuarto);
                }else if(numQuarto > 0 &&!saida.equals("")&&saida!=null&&!saida.equals("  /  /    ")){
                    reservas = reservaDAO.buscReservasSaidaNumQuarto(saida, numQuarto);
                }
                if(!nomeCli.equals("")&&nomeCli!=null&&!docCli.equals("")&&docCli!=null){
                    reservas = reservaDAO.buscReservasNomeDocCli(nomeCli, docCli);
                }else{
                    if(!nomeCli.equals("")&&nomeCli!=null){
                        reservas = reservaDAO.buscReservasNomeCli(nomeCli);
                    }else if(!docCli.equals("")&&docCli!=null){
                        reservas = reservaDAO.buscReservasDocCli(docCli);
                    }
                }
            }
            return reservas;
        } catch (Exception e) {
            reservas = new ArrayList();
            return reservas;
        }
    }
    
    public ArrayList<Reserva> buscTodasReservas(){
        ArrayList<Reserva> reservas = reservaDAO.buscTodasReservas();
        return reservas;
    }
}
