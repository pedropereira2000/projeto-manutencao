/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import javax.swing.JOptionPane;

public class ControllerData {
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
                    JOptionPane.showMessageDialog(null, "Sucesso!!!");
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
