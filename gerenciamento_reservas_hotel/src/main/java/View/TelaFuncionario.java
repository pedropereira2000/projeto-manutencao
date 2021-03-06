/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.GerenciaFuncionario;
import dao.ConnectionFactory;
import dao.FuncionarioDAO;
import model.Funcionario;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class TelaFuncionario extends javax.swing.JFrame {

    Connection conexao =null;
    private GerenciaFuncionario control = new GerenciaFuncionario();
      
    int id = 0;
    /*FUNÇÃO PARA LIMPAR A TELA*/
    public void limparTela(){
        text_nome.setText("");
        text_email.setText("");
        text_login.setText("");
        text_senha.setText("");
    }
    //FUNÇÃO PARA LISTAR TODOS OS Funcionarios CADASTRADOS NO BANCO DE DADOS 
    public void Listar(){
       try{
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> listaFuncionarios = dao.listarFuncionarios();
            DefaultTableModel model = (DefaultTableModel)tabela_Funcionarios.getModel();
            
            model.setNumRows(0);
            
            for(Funcionario lista : listaFuncionarios){
                model.addRow(new Object[]{
                    lista.getIdFuncionario(),
                    lista.getNomeFuncionario(),
                    lista.getEmailFuncionario(),
                    lista.getLoginFuncionario(),
                    lista.getSenhaFuncionario()
                });
            }
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Erro ao Listar:"+e);
       }
    }
    /*Função para Listar Todos os login Cadastrados*/
     public void listarLogin(){
        cb_nome.removeAllItems();
        cb_nome.addItem("<Selecione o Login>");
        cb_login.removeAllItems();
        cb_login.addItem("<Selecione o Nome>");
     
        FuncionarioDAO funcdao = new FuncionarioDAO();
        
        for(Funcionario f: funcdao.listarLogin()){
            cb_login.addItem(f.getLoginFuncionario());
        } 
    }
    /*Função para Listar Todos os Nomes Cadastrados*/
    public void listarNomes(String desc){
        cb_nome.removeAllItems();
        cb_nome.addItem("<Selecione o Nome>");
        
        FuncionarioDAO funcdao = new FuncionarioDAO();
        
        for(Funcionario f: funcdao.listarNome(desc)){
            cb_nome.addItem(f.getNomeFuncionario());
        } 
    }
   
    //FUNÇÃO PARA PESQUISAR Funcionarios NO BANCO DE DADOS
    public void pesquisarFuncionario(){
       // Botão Pesquisar
         try{
            String login = cb_login.getSelectedItem().toString();
            String nome = cb_nome.getSelectedItem().toString();
            if(login != "<Selecione o Login>" && nome != "<Selecione o Nome>"){
                System.out.println(""+login);
                FuncionarioDAO dao = new FuncionarioDAO();
                List<Funcionario> consultarFuncionarioLogin = dao.pesquisarFuncionario(login,nome);
                DefaultTableModel model = (DefaultTableModel)tabela_Funcionarios.getModel();
                
                model.setNumRows(0);
                
                for(Funcionario consulta : consultarFuncionarioLogin){
                    model.addRow(new Object[]{
                        consulta.getIdFuncionario(),
                        consulta.getNomeFuncionario(),
                        consulta.getEmailFuncionario(),
                        consulta.getLoginFuncionario(),
                        consulta.getSenhaFuncionario()
                    });
                }
           
            }else{
               Listar(); 
            }
       }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Erro:"+e);
       }
    }
    
    public TelaFuncionario() {
        initComponents();
        
        conexao = ConnectionFactory.conectar();
       
        Listar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text_nome = new javax.swing.JTextField();
        text_email = new javax.swing.JTextField();
        text_login = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        text_senha = new javax.swing.JTextField();
        jLabel_Nome = new javax.swing.JLabel();
        jLabel_email = new javax.swing.JLabel();
        jLabel_Login = new javax.swing.JLabel();
        jLabel_Senha = new javax.swing.JLabel();
        jButton_Editar = new javax.swing.JButton();
        jButton_Excluir = new javax.swing.JButton();
        jButton_Pesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_Funcionarios = new javax.swing.JTable();
        cb_nome = new javax.swing.JComboBox<>();
        cb_login = new javax.swing.JComboBox<>();
        jLabel_pesNome = new javax.swing.JLabel();
        jLabel_pesLogin = new javax.swing.JLabel();
        Txt_loginEditar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        text_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nomeActionPerformed(evt);
            }
        });

        jButton1.setText("Cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel_Nome.setText("Nome");

        jLabel_email.setText("E-mail");

        jLabel_Login.setText("Login");

        jLabel_Senha.setText("Senha");

        jButton_Editar.setText("Editar");
        jButton_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EditarActionPerformed(evt);
            }
        });

        jButton_Excluir.setText("Excluir");
        jButton_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ExcluirActionPerformed(evt);
            }
        });

        jButton_Pesquisar.setText("Pesquisar");
        jButton_Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PesquisarActionPerformed(evt);
            }
        });

        tabela_Funcionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Nome", "E-mail", "Login", "Senha"
            }
        ));
        tabela_Funcionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela_FuncionariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela_Funcionarios);

        cb_nome.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione o Nome>" }));
        cb_nome.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cb_nomePopupMenuWillBecomeVisible(evt);
            }
        });

        cb_login.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione o Login>" }));
        cb_login.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cb_loginPopupMenuWillBecomeVisible(evt);
            }
        });

        jLabel_pesNome.setText("Pesquisar Nome");

        jLabel_pesLogin.setText("Pesquisar Login");

        Txt_loginEditar.setEditable(false);
        Txt_loginEditar.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_login, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_pesLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_pesNome)
                            .addComponent(cb_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel_Nome)
                            .addComponent(text_nome, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(jLabel_Login)
                            .addComponent(text_login))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_email, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_email)
                            .addComponent(jLabel_Senha)
                            .addComponent(text_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Txt_loginEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Txt_loginEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Nome)
                    .addComponent(jLabel_email))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Login)
                    .addComponent(jLabel_Senha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_login, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_pesNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_pesLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_login, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Editar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void text_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_nomeActionPerformed

    private void tabela_FuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela_FuncionariosMouseClicked
        // TODO add your handling code here:
        // Pegar o login do funcionario
        text_nome.setText(tabela_Funcionarios.getValueAt(tabela_Funcionarios.getSelectedRow(), 1).toString());
        text_email.setText(tabela_Funcionarios.getValueAt(tabela_Funcionarios.getSelectedRow(), 2).toString());
        text_login.setText(tabela_Funcionarios.getValueAt(tabela_Funcionarios.getSelectedRow(), 3).toString());
        text_senha.setText(tabela_Funcionarios.getValueAt(tabela_Funcionarios.getSelectedRow(), 4).toString());
        Txt_loginEditar.setText(tabela_Funcionarios.getValueAt(tabela_Funcionarios.getSelectedRow(), 0).toString());
        id = Integer.parseInt(Txt_loginEditar.getText());
    }//GEN-LAST:event_tabela_FuncionariosMouseClicked

    private void jButton_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EditarActionPerformed
        // TODO add your handling code here:
        //editarFuncionario();
        
        if(text_nome.getText().isEmpty()||text_email.getText().isEmpty()||text_login.getText().isEmpty()||text_senha.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Erro: Campos Vazios");
                
        }else{
            control.editarFuncionario(id, Txt_loginEditar.getText(), text_nome.getText(), text_email.getText(),text_login.getText(),text_senha.getText());
            Listar(); 
            limparTela();
        }
    }//GEN-LAST:event_jButton_EditarActionPerformed

    private void cb_nomePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cb_nomePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        listarNomes(cb_login.getSelectedItem().toString());
    }//GEN-LAST:event_cb_nomePopupMenuWillBecomeVisible

    private void cb_loginPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cb_loginPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        listarLogin();
    }//GEN-LAST:event_cb_loginPopupMenuWillBecomeVisible

    private void jButton_PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PesquisarActionPerformed
        // TODO add your handling code here:
        pesquisarFuncionario();
    }//GEN-LAST:event_jButton_PesquisarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(text_nome.getText().isEmpty()||text_email.getText().isEmpty()||text_login.getText().isEmpty()||text_senha.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Erro: Campos Vazios");
                
        }else{
            if(control.cadastrarFuncionarios(text_nome.getText(), text_email.getText(),text_login.getText(),text_senha.getText())){
                JOptionPane.showMessageDialog(null, "Funcionario cadastrado com Sucesso!");
            }else{
                JOptionPane.showMessageDialog(null, "Funcionario não cadastrado!");
            }
            Listar();  
            limparTela();
        
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ExcluirActionPerformed
        // TODO add your handling code here:
        //excluirFuncionario();

            control.excluirFuncionario(id, Txt_loginEditar.getText());
            Listar(); 
            limparTela();
        
    }//GEN-LAST:event_jButton_ExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaFuncionario tlFunc = new TelaFuncionario();
                //Define o que acontece ao clicar em fechar
                tlFunc.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                
                tlFunc.addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent evt){
                        //TelaPrincipal tlPrinc = new TelaPrincipal();
                        //tlPrinc.setVisible(true);
                        tlFunc.dispose();
                    }
                });
                
                tlFunc.setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Txt_loginEditar;
    private javax.swing.JComboBox<String> cb_login;
    private javax.swing.JComboBox<String> cb_nome;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Editar;
    private javax.swing.JButton jButton_Excluir;
    private javax.swing.JButton jButton_Pesquisar;
    private javax.swing.JLabel jLabel_Login;
    private javax.swing.JLabel jLabel_Nome;
    private javax.swing.JLabel jLabel_Senha;
    private javax.swing.JLabel jLabel_email;
    private javax.swing.JLabel jLabel_pesLogin;
    private javax.swing.JLabel jLabel_pesNome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_Funcionarios;
    private javax.swing.JTextField text_email;
    private javax.swing.JTextField text_login;
    private javax.swing.JTextField text_nome;
    private javax.swing.JTextField text_senha;
    // End of variables declaration//GEN-END:variables
}
