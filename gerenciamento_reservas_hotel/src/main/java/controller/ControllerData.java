/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class ControllerData {
    public Connection conectar;
    
    public ControllerData() {
        this.conectar = new ConnectionFactory().conectar();
    }
    
    public boolean historicoQuartos(){
        try{
            String projectPath = System.getProperty("user.dir");
            String reportPath = "/reports/models/historic_rooms.jrxml";
            int qt = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual é o número do quarto?"));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("search_room",qt);
            JasperReport jr = JasperCompileManager.compileReport(projectPath+reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, conectar);
            JasperViewer.viewReport(jp, false);
            return true;
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean historicoReserva(){
        try{
            String projectPath = System.getProperty("user.dir");
            String reportPath = "/reports/models/hitoric_reservation.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(projectPath+reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr,null, conectar);
            JasperViewer.viewReport(jp, false);
            conectar.close();
            return true;
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    public int daysBetween(Date d1, Date d2){

        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    public boolean cadastrarData(Date DataInicio,Date DataFim){
        Date dataAtual = new Date();
       
        if(dataAtual.getTime() < DataInicio.getTime()){

            if(DataFim.getTime() > DataInicio.getTime()){
                if(daysBetween(DataInicio,DataFim)>14){
                    JOptionPane.showMessageDialog(null, "ERRO:Prazo maior que 14 dias");
                    throw new RuntimeException("ERRO:Prazo maior que 14 dias");
                }else{
                    return true;
                }
            }else if(DataFim.getTime() < DataInicio.getTime()){
                JOptionPane.showMessageDialog(null, "ERRO:Data Fim não pode ser anterior a data inicio");
                throw new RuntimeException("ERRO:Data Fim não pode ser anterior a data inicio");
            }else if(DataFim.getTime() == DataInicio.getTime()){
                JOptionPane.showMessageDialog(null, "ERRO:Data Fim não pode ser igual a data inicio");
                throw new RuntimeException("ERRO:Data Fim não pode ser igual a data inicio");
            }else{
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "ERRO:Data Inicio não pode ser inferior a data atual");
            throw new RuntimeException("ERRO:Data Inicio não pode ser inferior a data atual");
        }
      
    }
}
