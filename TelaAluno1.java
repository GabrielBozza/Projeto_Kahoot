
import java.awt.Color;
import javax.swing.WindowConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author silve
 */
public class TelaAluno1 extends javax.swing.JFrame {
private String login1;
    /**
     * Creates new form TelaAluno1
     */
    public TelaAluno1() {
        initComponents();
             
    }

    TelaAluno1(String loginn) {
        initComponents();
        login1 = loginn;
        atualizaClientes(login1);
        
    }
 private void atualizaClientes(String nome) {   
            jLabel4.setText(nome);
    }

 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCadastraQuestionario = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnOverview = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCadastraQuestionario1 = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        btnOverview1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Escolha uma das opçoes a seguir:");

        btnCadastraQuestionario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCadastraQuestionario.setText("Cadastrar Questionário");
        btnCadastraQuestionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastraQuestionarioActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnOverview.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnOverview.setText("Overview de Questionário");
        btnOverview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOverviewActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setText("Seja Bem-Vindo ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma do Aluno");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(120, 240, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusable(false);
        setForeground(new java.awt.Color(255, 51, 51));
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(2000, 2000));
        setMinimumSize(new java.awt.Dimension(700, 700));
        setPreferredSize(new java.awt.Dimension(700, 720));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel4.setText("jLabel2");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 300, 60));

        jLabel5.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel5.setText("Aluno:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 40));

        btnCadastraQuestionario1.setBackground(new java.awt.Color(205, 230, 240));
        btnCadastraQuestionario1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCadastraQuestionario1.setText("Responder Questionário");
        btnCadastraQuestionario1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCadastraQuestionario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastraQuestionario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastraQuestionario1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnCadastraQuestionario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 230, 55));

        btnSair1.setBackground(new java.awt.Color(205, 230, 240));
        btnSair1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSair1.setText("Sair");
        btnSair1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSair1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 90, 55));

        btnOverview1.setBackground(new java.awt.Color(205, 230, 240));
        btnOverview1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnOverview1.setText("Overview de Desempenho");
        btnOverview1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnOverview1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOverview1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOverview1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnOverview1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 240, 55));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 922, 351, 90));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/404338-PD3H9L-8.jpg"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastraQuestionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastraQuestionarioActionPerformed
        this.setVisible(false);
        InsiraPin mc = new InsiraPin();
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);
    }//GEN-LAST:event_btnCadastraQuestionarioActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.setVisible(false);
        LoginProfessor1 mc = new LoginProfessor1();
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnOverviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOverviewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOverviewActionPerformed

    private void btnCadastraQuestionario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastraQuestionario1ActionPerformed
        this.setVisible(false);
        InsiraPin mc = new InsiraPin(login1);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);
    }//GEN-LAST:event_btnCadastraQuestionario1ActionPerformed

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed
        this.setVisible(false);
        LoginAluno1 mc = new LoginAluno1();
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnOverview1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOverview1ActionPerformed
this.setVisible(false);
        OverAluno0 mc = new OverAluno0(login1);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);    }//GEN-LAST:event_btnOverview1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaAluno1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAluno1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAluno1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAluno1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAluno1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastraQuestionario;
    private javax.swing.JButton btnCadastraQuestionario1;
    private javax.swing.JButton btnOverview;
    private javax.swing.JButton btnOverview1;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSair1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
