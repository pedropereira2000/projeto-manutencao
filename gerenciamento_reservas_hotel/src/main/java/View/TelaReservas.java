/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.Date;
import javax.swing.SpringLayout;
import controller.ControllerData;
import controller.GerenciaQuarto;
import controller.GerenciaReserva;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Quarto;
import model.Reserva;

public class TelaReservas extends javax.swing.JFrame {
    
    private GerenciaReserva controlRes = new GerenciaReserva();
    private GerenciaQuarto gencQuarto = new GerenciaQuarto();
    private int numQuarto = 0;
    private String entrada = "";
    private String saida = "";
    private int quartoSelect = 0;
    /**
     * Creates new form Teste_Data
     */
    public TelaReservas() {
        initComponents();
        populaComboQuartoLivres();
        populaTabelaReserva();
    }
    
    public void populaCamposEdicao(int numQuarto, String entrada, String saida){
        this.numQuarto = numQuarto;
        this.entrada = entrada;
        this.saida = saida;
        ArrayList<Reserva> res = controlRes.buscarReservas(entrada, saida, numQuarto, "", "");
        ftxtTelCli.setText(res.get(0).getTelefoneReserva());
        ftxtDocCli.setText(res.get(0).getDocumentoReserva());
        txtCliName.setText(res.get(0).getClienteReserva());
        try{
            DataFim.setDate(controlRes.convertStringDate(res.get(0).getSaidaCliente()));
            DataFim.setEnabled(false);
            DataInicio.setDate(controlRes.convertStringDate(res.get(0).getEntradaReserva()));
            DataInicio.setEnabled(false);
        }catch(ParseException er){
            JOptionPane.showMessageDialog(this, "Náo foi possivel converter a data");
        }
        cbxQuartosVagos.setSelectedItem(res.get(0).getQuarIdQuarto().getAndar().getNumAndar()+"-"+res.get(0).getQuarIdQuarto().getNumQuarto());
        quartoSelect = res.get(0).getQuarIdQuarto().getNumQuarto();
        cbxQuartosVagos.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);        
        btnReservar.setEnabled(false);
    }
    
    public void populaComboQuartoLivres() {
        ///fazer alterações não funciona
        for (Quarto q : gencQuarto.buscTodosQuartosLivres()) {
            cbxQuartosVagos.addItem(q.getAndar().getNumAndar()+"-"+q.getNumQuarto());
        }
    }
    
    public void populaTabelaReserva(){
        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        
        modelo.setNumRows(0);
        
        for(Reserva q: controlRes.buscTodasReservas()){
            modelo.addRow(new Object[]{
                q.getIdReserva(),
                q.getEntradaReserva(),
                q.getSaidaCliente(),
                q.getClienteReserva(),
                q.getTelefoneReserva(),
                q.getDocumentoReserva(),
                q.getFunIdFuncionario().getNomeFuncionario(),
                q.getQuarIdQuarto().getAndar().getNumAndar(),
                q.getQuarIdQuarto().getNumQuarto()
            });
        }
        
        
    }
    
    public void limparCampos(){
        cbxQuartosVagos.removeAllItems();
        txtCliName.setText("");
        ftxtDocCli.setText("");
        ftxtTelCli.setText("");
        DataInicio.setDate(null);
        DataFim.setDate(null);
        populaComboQuartoLivres();
        populaTabelaReserva();
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnReservar.setEnabled(true);
        cbxQuartosVagos.setEnabled(true);
        DataFim.setEnabled(true);
        DataInicio.setEnabled(true);
        quartoSelect=0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DataInicio = new com.toedter.calendar.JDateChooser();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        btnReservar = new javax.swing.JButton();
        DataFim = new com.toedter.calendar.JDateChooser();
        lblCliName = new javax.swing.JLabel();
        lblDocCLi = new javax.swing.JLabel();
        lblTelCli = new javax.swing.JLabel();
        txtCliName = new javax.swing.JTextField();
        ftxtDocCli = new javax.swing.JFormattedTextField();
        ftxtTelCli = new javax.swing.JFormattedTextField();
        cbxQuartosVagos = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        DataInicio.setDateFormatString("dd/MM/yyyy");
        DataInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DataInicioPropertyChange(evt);
            }
        });

        label1.setText("Data Fim");

        label2.setText("Data Inicio");

        btnReservar.setText("Reservar");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        DataFim.setDateFormatString("dd/MM/yyyy");

        lblCliName.setText("Nome do Cliente");

        lblDocCLi.setText("Documento do Cliente");

        lblTelCli.setText("Telefone Cliente");

        try {
            ftxtDocCli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtDocCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtDocCliActionPerformed(evt);
            }
        });

        try {
            ftxtTelCli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-9####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idReserva", "entrada", "saida", "cliente", "telefone", "documento", "funcionario", "andar", "quarto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblReservasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblReservas);

        btnEditar.setText("Editar Reserva");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Cancelar Reserva");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar Edicao");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReservar)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCliName)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblCliName)
                                    .addGap(27, 27, 27))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDocCLi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ftxtDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(79, 79, 79)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblTelCli)
                                    .addComponent(ftxtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                .addComponent(cbxQuartosVagos, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(85, 85, 85))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCancelar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnEditar)
                                        .addGap(31, 31, 31)
                                        .addComponent(btnExcluir)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblCliName)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDocCLi)
                                .addGap(16, 16, 16)
                                .addComponent(ftxtDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTelCli)
                                    .addComponent(cbxQuartosVagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(ftxtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(21, 21, 21)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(DataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReservar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DataInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DataInicioPropertyChange
        // TODO add your handling code here:
         
    }//GEN-LAST:event_DataInicioPropertyChange

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        if(txtCliName.getText().isEmpty()||ftxtDocCli.getText().isEmpty()||ftxtTelCli.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Erro: Campos Vazios");
        }else{
            if(DataFim.isEnabled()==false&&DataInicio.isEnabled()==false&&cbxQuartosVagos.isEnabled()==false){
                Date dataInicio = DataInicio.getDate();//transforma o obj calendar em obj Date
                SimpleDateFormat sddia = new SimpleDateFormat("dd-MM-yyyy");//cria um obj de formatação de data
                String diaEntrada = sddia.format(dataInicio);
                String idQuarto = String.valueOf(cbxQuartosVagos.getSelectedItem());
                int pos = idQuarto.indexOf("-");
                controlRes.editarReserva(diaEntrada, Integer.parseInt(idQuarto.substring(pos+1, idQuarto.length())), txtCliName.getText(), ftxtDocCli.getText(),ftxtTelCli.getText());
                limparCampos();
            }else{
                Date dataInicio = DataInicio.getDate();//transforma o obj calendar em obj Date
                Date dataFim = DataFim.getDate();//transforma o obj calendar em obj Date
                SimpleDateFormat sddia = new SimpleDateFormat("dd-MM-yyyy");//cria um obj de formatação de data
                String diaFim = sddia.format(dataFim);

                String diaEntrada = sddia.format(dataInicio);
                String idQuarto = String.valueOf(cbxQuartosVagos.getSelectedItem());
                int pos = idQuarto.indexOf("-");
                try {
                    //Integer.parseInt(idQuarto.substring(pos+1, idQuarto.length()))

                    controlRes.realizaReserva(diaEntrada, diaFim, txtCliName.getText(), ftxtDocCli.getText(), ftxtTelCli.getText(), 1, Integer.parseInt(idQuarto.substring(pos+1, idQuarto.length())));
                    limparCampos();
                } catch (Exception ex) {
                    Logger.getLogger(TelaReservas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
    }//GEN-LAST:event_btnReservarActionPerformed

    private void tblReservasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReservasMousePressed
        populaCamposEdicao(Integer.parseInt(String.valueOf(tblReservas.getValueAt(tblReservas.getSelectedRow(), 8))), String.valueOf(tblReservas.getValueAt(tblReservas.getSelectedRow(), 1)),String.valueOf(tblReservas.getValueAt(tblReservas.getSelectedRow(), 2)));
    }//GEN-LAST:event_tblReservasMousePressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        controlRes.editarReserva(entrada, quartoSelect, txtCliName.getText(), ftxtDocCli.getText(), ftxtTelCli.getText());
        limparCampos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        controlRes.excluirReserva(entrada, quartoSelect);
        limparCampos();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void ftxtDocCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtDocCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtDocCliActionPerformed

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
            java.util.logging.Logger.getLogger(TelaReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaReservas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DataFim;
    private com.toedter.calendar.JDateChooser DataInicio;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnReservar;
    private javax.swing.JComboBox<String> cbxQuartosVagos;
    private javax.swing.JFormattedTextField ftxtDocCli;
    private javax.swing.JFormattedTextField ftxtTelCli;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JLabel lblCliName;
    private javax.swing.JLabel lblDocCLi;
    private javax.swing.JLabel lblTelCli;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtCliName;
    // End of variables declaration//GEN-END:variables
}
