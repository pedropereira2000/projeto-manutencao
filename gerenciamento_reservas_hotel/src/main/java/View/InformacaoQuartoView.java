/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.GerenciaAndar;
import controller.GerenciaQuarto;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.Quarto;


public class InformacaoQuartoView extends javax.swing.JFrame {

    private GerenciaQuarto gencQuarto = new GerenciaQuarto();

    /**
     * Creates new form InformacaoQuartoView
     */
    public InformacaoQuartoView() {
        initComponents();
        gerenciaTable();
    }

    public void gerenciaTable() {
        if (jRadioButtonOcupados.isSelected()) {
            jpQuarto.setVisible(false);
            jpQuarto2.setVisible(true);
            populaTabelaQuartoOcupados();
        } else if(jRadioButtonLivres.isSelected()){
            //Fazer alterações não funciona
            jpQuarto.setVisible(true);
            jpQuarto2.setVisible(false);
            populaTabelaQuartoLivres();
        }
    }

    //Quartos Ocupados
    public void populaTabelaQuartoOcupados() {
        DefaultTableModel modelo = (DefaultTableModel) tblQuartoOcupados.getModel();
        modelo.setNumRows(0);
        for (Quarto q : gencQuarto.buscTodosQuartosOcupados()) {
            modelo.addRow(new Object[]{
                q.getIdQuarto(),
                q.getNumQuarto(),
                q.getTipoQuarto(),
                q.getNumCamasQuarto(),
                q.getQtdBanheirosQuarto(),
                q.getDescricaoQuarto(),
                q.getContatoQuarto(),
                q.getAndar().getNumAndar()
            });
        }
    }

    //Quartos Livras não ta pronto
    public void populaTabelaQuartoLivres() {
        ///fazer alterações não funciona
        DefaultTableModel modelo = (DefaultTableModel) tblQuartoLivres.getModel();
        modelo.setNumRows(0);
        for (Quarto q : gencQuarto.buscTodosQuartosLivres()) {
            modelo.addRow(new Object[]{
                q.getIdQuarto(),
                q.getNumQuarto(),
                q.getTipoQuarto(),
                q.getNumCamasQuarto(),
                q.getQtdBanheirosQuarto(),
                q.getDescricaoQuarto(),
                q.getContatoQuarto(),
                q.getAndar().getNumAndar()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRadioButtonOcupados = new javax.swing.JRadioButton();
        jRadioButtonLivres = new javax.swing.JRadioButton();
        jpQuarto = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblQuartoLivres = new javax.swing.JTable();
        jpQuarto2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQuartoOcupados = new javax.swing.JTable();
        btnReservar = new javax.swing.JButton();
        txtDoc = new javax.swing.JTextField();
        btnBusca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Informação Quarto");

        buttonGroup1.add(jRadioButtonOcupados);
        jRadioButtonOcupados.setText("Quartos Ocupados");
        jRadioButtonOcupados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOcupadosActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonLivres);
        jRadioButtonLivres.setSelected(true);
        jRadioButtonLivres.setText("Quartos Livres");
        jRadioButtonLivres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLivresActionPerformed(evt);
            }
        });

        tblQuartoLivres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Número Quarto", "Tipo", "Camas", "Banheiros", "Descrição", "Contato", "Andar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblQuartoLivres);

        javax.swing.GroupLayout jpQuartoLayout = new javax.swing.GroupLayout(jpQuarto);
        jpQuarto.setLayout(jpQuartoLayout);
        jpQuartoLayout.setHorizontalGroup(
            jpQuartoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpQuartoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jpQuartoLayout.setVerticalGroup(
            jpQuartoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpQuartoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblQuartoOcupados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Número Quarto", "Tipo", "Camas", "Banheiros", "Descrição", "Contato", "Andar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblQuartoOcupados);

        javax.swing.GroupLayout jpQuarto2Layout = new javax.swing.GroupLayout(jpQuarto2);
        jpQuarto2.setLayout(jpQuarto2Layout);
        jpQuarto2Layout.setHorizontalGroup(
            jpQuarto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpQuarto2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpQuarto2Layout.setVerticalGroup(
            jpQuarto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpQuarto2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnReservar.setText("Reservar");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        btnBusca.setText("Buscar por Cliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jpQuarto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jpQuarto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jRadioButtonOcupados)
                                .addGap(43, 43, 43)
                                .addComponent(btnReservar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBusca))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(259, 259, 259)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButtonLivres)))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonLivres)
                    .addComponent(jRadioButtonOcupados)
                    .addComponent(btnReservar)
                    .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusca))
                .addGap(7, 7, 7)
                .addComponent(jpQuarto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpQuarto2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonLivresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLivresActionPerformed
        gerenciaTable();
    }//GEN-LAST:event_jRadioButtonLivresActionPerformed

    private void jRadioButtonOcupadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonOcupadosActionPerformed
        gerenciaTable();
    }//GEN-LAST:event_jRadioButtonOcupadosActionPerformed

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        new TelaReservas().show();
        dispose();
    }//GEN-LAST:event_btnReservarActionPerformed

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
            java.util.logging.Logger.getLogger(InformacaoQuartoView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InformacaoQuartoView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InformacaoQuartoView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InformacaoQuartoView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InformacaoQuartoView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnReservar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButtonLivres;
    private javax.swing.JRadioButton jRadioButtonOcupados;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpQuarto;
    private javax.swing.JPanel jpQuarto2;
    private javax.swing.JTable tblQuartoLivres;
    private javax.swing.JTable tblQuartoOcupados;
    private javax.swing.JTextField txtDoc;
    // End of variables declaration//GEN-END:variables
}
