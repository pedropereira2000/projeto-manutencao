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
        txtName.setText("");
        txtEmail.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
    }
    //FUNÇÃO PARA LISTAR TODOS OS Funcionarios CADASTRADOS NO BANCO DE DADOS 
    public void Listar(){
       try{
            FuncionarioDAO dao = new FuncionarioDAO();
            List<Funcionario> listaFuncionarios = dao.listarFuncionarios();
            DefaultTableModel model = (DefaultTableModel)tabelaFuncionarios.getModel();
            
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
        cbName.removeAllItems();
        cbName.addItem("<Selecione o Login>");
        cbLogin.removeAllItems();
        cbLogin.addItem("<Selecione o Nome>");
     
        FuncionarioDAO funcdao = new FuncionarioDAO();
        
        for(Funcionario f: funcdao.listarLogin()){
            cbLogin.addItem(f.getLoginFuncionario());
        } 
    }
    /*Função para Listar Todos os Nomes Cadastrados*/
    public void listarNomes(String desc){
        cbName.removeAllItems();
        cbName.addItem("<Selecione o Nome>");
        
        FuncionarioDAO funcdao = new FuncionarioDAO();
        
        for(Funcionario f: funcdao.listarNome(desc)){
            cbName.addItem(f.getNomeFuncionario());
        } 
    }
   
    //FUNÇÃO PARA PESQUISAR Funcionarios NO BANCO DE DADOS
    public void pesquisarFuncionario(){
       // Botão Pesquisar
         try{
            String login = cbLogin.getSelectedItem().toString();
            String nome = cbName.getSelectedItem().toString();
            if(login != "<Selecione o Login>" && nome != "<Selecione o Nome>"){
                System.out.println(""+login);
                FuncionarioDAO dao = new FuncionarioDAO();
                List<Funcionario> consultarFuncionarioLogin = dao.pesquisarFuncionario(login,nome);
                DefaultTableModel model = (DefaultTableModel)tabelaFuncionarios.getModel();
                
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

        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        jLabelNome = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jLabelSenha = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaFuncionarios = new javax.swing.JTable();
        cbName = new javax.swing.JComboBox<>();
        cbLogin = new javax.swing.JComboBox<>();
        jLabelPesNome = new javax.swing.JLabel();
        jLabelPesLogin = new javax.swing.JLabel();
        Txt_loginEditar = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        btnCadastrar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jLabelNome.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelNome.setText("Nome");

        jLabelEmail.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelEmail.setText("E-mail");

        jLabelLogin.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelLogin.setText("Login");

        jLabelSenha.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelSenha.setText("Senha");

        btnEditar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnPesquisar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        tabelaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFuncionariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaFuncionarios);

        cbName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione o Nome>" }));
        cbName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbNamePopupMenuWillBecomeVisible(evt);
            }
        });

        cbLogin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione o Login>" }));
        cbLogin.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbLoginPopupMenuWillBecomeVisible(evt);
            }
        });

        jLabelPesNome.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelPesNome.setText("Pesquisar Nome");

        jLabelPesLogin.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelPesLogin.setText("Pesquisar Login");

        Txt_loginEditar.setEditable(false);
        Txt_loginEditar.setText("0");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel1.setText("Funcionário");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPesLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPesNome)
                            .addComponent(cbName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNome)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(jLabelLogin)
                            .addComponent(txtLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(jLabelEmail)
                            .addComponent(jLabelSenha)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(56, 56, 56))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Txt_loginEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194))
            .addGroup(layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Txt_loginEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNome)
                    .addComponent(jLabelEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogin)
                    .addComponent(jLabelSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPassword)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelPesNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelPesLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir))
                .addGap(23, 23, 23))
        );

        setSize(new java.awt.Dimension(946, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void tabelaFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFuncionariosMouseClicked
        // TODO add your handling code here:
        // Pegar o login do funcionario
        txtName.setText(tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 1).toString());
        txtEmail.setText(tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 2).toString());
        txtLogin.setText(tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 3).toString());
        txtPassword.setText(tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 4).toString());
        Txt_loginEditar.setText(tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 0).toString());
        id = Integer.parseInt(Txt_loginEditar.getText());
    }//GEN-LAST:event_tabelaFuncionariosMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        //editarFuncionario();
        control.editarFuncionario(id, Txt_loginEditar.getText(), txtName.getText(), txtEmail.getText(),txtLogin.getText(),txtPassword.getText());
        Listar(); 
        limparTela();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void cbNamePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbNamePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        listarNomes(cbLogin.getSelectedItem().toString());
    }//GEN-LAST:event_cbNamePopupMenuWillBecomeVisible

    private void cbLoginPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLoginPopupMenuWillBecomeVisible
        // TODO add your handling code here:
        listarLogin();
    }//GEN-LAST:event_cbLoginPopupMenuWillBecomeVisible

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        pesquisarFuncionario();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        // TODO add your handling code here:
        
        if(control.cadastrarFuncionarios(txtName.getText(), txtEmail.getText(),txtLogin.getText(),txtPassword.getText())){
            JOptionPane.showMessageDialog(null, "Funcionario cadastrado com Sucesso!");
        }else{
            System.out.println("teste");
            JOptionPane.showMessageDialog(null, "Funcionario não cadastrado!");
        }
        Listar();  
        limparTela();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        //excluirFuncionario();
        control.excluirFuncionario(id, Txt_loginEditar.getText());
        Listar(); 
        limparTela();
    }//GEN-LAST:event_btnExcluirActionPerformed

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
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cbLogin;
    private javax.swing.JComboBox<String> cbName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelPesLogin;
    private javax.swing.JLabel jLabelPesNome;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaFuncionarios;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
