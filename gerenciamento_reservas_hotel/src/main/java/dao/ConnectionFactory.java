/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection conectar(){
       try{
           return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useTimezone=true&serverTimezone=UTC","root","3621");
       }catch(SQLException e){
           throw new RuntimeException(e);
       }
    }
    
    public static Connection conectar(String sql, String user, String senha){
        try{
            return DriverManager.getConnection(sql, user, senha);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}
