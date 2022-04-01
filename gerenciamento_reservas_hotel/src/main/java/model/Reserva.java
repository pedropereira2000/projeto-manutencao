/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Reserva {

    private int idReserva;
    private String entradaReserva;
    private String saidaCliente;
    private String clienteReserva;
    private String telefoneReserva;
    private String documentoReserva;
    /*Chamando Classe Funcionario*/
    private Funcionario funIdFuncionario;
    /*Chamando Classe Quarto*/
    private Quarto quarIdQuarto;

    public Reserva() {
        idReserva = 0;
        entradaReserva = "";
        saidaCliente = "";
        clienteReserva = "";
        telefoneReserva = "";
        documentoReserva = "";
        funIdFuncionario = new Funcionario();
        quarIdQuarto = new Quarto();
    }

    public Reserva(int idReserva, String entradaReserva, String saidaCliente, String clienteReserva, String telefoneReserva, String documentoReserva, Funcionario funIdFuncionario, Quarto quarIdQuarto) {
        this.idReserva = idReserva;
        this.entradaReserva = entradaReserva;
        this.saidaCliente = saidaCliente;
        this.clienteReserva = clienteReserva;
        this.telefoneReserva = telefoneReserva;
        this.documentoReserva = documentoReserva;
        this.funIdFuncionario = funIdFuncionario;
        this.quarIdQuarto = quarIdQuarto;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getEntradaReserva() {
        return entradaReserva;
    }

    public void setEntradaReserva(String entradaReserva) {
        this.entradaReserva = entradaReserva;
    }

    public String getSaidaCliente() {
        return saidaCliente;
    }

    public void setSaidaCliente(String saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    public String getClienteReserva() {
        return clienteReserva;
    }

    public void setClienteReserva(String clienteReserva) {
        this.clienteReserva = clienteReserva;
    }

    public String getTelefoneReserva() {
        return telefoneReserva;
    }

    public void setTelefoneReserva(String telefoneReserva) {
        this.telefoneReserva = telefoneReserva;
    }

    public String getDocumentoReserva() {
        return documentoReserva;
    }

    public void setDocumentoReserva(String documentoReserva) {
        this.documentoReserva = documentoReserva;
    }

    public Funcionario getFunIdFuncionario() {
        return funIdFuncionario;
    }

    public void setFunIdFuncionario(Funcionario funIdFuncionario) {
        this.funIdFuncionario = funIdFuncionario;
    }

    public Quarto getQuarIdQuarto() {
        return quarIdQuarto;
    }

    public void setQuarIdQuarto(Quarto quarIdQuarto) {
        this.quarIdQuarto = quarIdQuarto;
    }

}
