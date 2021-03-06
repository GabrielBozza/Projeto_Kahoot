import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class InsiraPinProf extends javax.swing.JFrame {

    Scanner in;
    PrintWriter out;
    String login;
    int quest;
    JFrame father;

    /**
     * Creates new form InsiraPinProf
     */
    public InsiraPinProf() {
        initComponents();
    }

    public InsiraPinProf(JFrame father) {
        initComponents();
        this.father = father;

// TODO add your handling code here:
    }

    InsiraPinProf(String login1) {
        initComponents();
        login = login1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtPin = new javax.swing.JTextPane();
        btnResponder = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma do Professor");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPin.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jScrollPane1.setViewportView(txtPin);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 610, 110, 50));

        btnResponder.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnResponder.setText("Confirmar");
        btnResponder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResponder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderActionPerformed(evt);
            }
        });
        getContentPane().add(btnResponder, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 620, 150, 40));

        btnVoltar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        getContentPane().add(btnVoltar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/key.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 700, 710));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnResponderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderActionPerformed
        if (txtPin.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo Pin não pode estar em branco!", "Erro ao acessar questionário!", JOptionPane.ERROR_MESSAGE);
        } else {

            quest = Integer.parseInt(txtPin.getText());

            try {
                Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                int cond = 0;
                out.println("PIN_PROFESSOR     " + txtPin.getText() + "‰" + login);//OS ESPACOS EM BRANCO SAO PARA QUE SEJA >=18 CHARACT E NAO DE OUTOFBOUND AO CHECAR OS OUTROS IFS

                while (!out.equals("")) {

                    cond = 1;
                    String input = in.nextLine();

                    if (input.substring(0, 10).equals("PIN_VALIDO")) {
                        int num_questoes = Integer.parseInt(input.substring(10));//NAO EH USADO AQUI, MAS PODE SER USADO NO FUTURO
                        this.setVisible(false);
                        OverProf0 mc = new OverProf0(login, quest);
                        socket.close();
                        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        mc.setVisible(true);
                    } else if (input.equals("PIN_DE_OUTRO")) {
                        JOptionPane.showMessageDialog(null, "O questionario pertence a outro professor!", "Erro ao acessar questionário!", JOptionPane.ERROR_MESSAGE);
                        txtPin.setText("");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "O campo Pin não foi encontrado!", "Erro ao acessar questionário!", JOptionPane.ERROR_MESSAGE);
                        txtPin.setText("");
                        break;
                    }
                }
                if (cond == 0) {
                    JOptionPane.showMessageDialog(null, "O campo Pin não foi encontrado!", "Erro ao acessar questionário!", JOptionPane.ERROR_MESSAGE);
                    txtPin.setText("");
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Servidor fora do ar, aguarde...", "Erro ao efetuar o Cadastro!", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(InsiraPin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtPin.setText("");


    }//GEN-LAST:event_btnResponderActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.setVisible(false);
        TelaProfessor1 mc = new TelaProfessor1(login);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnVoltarActionPerformed

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
            java.util.logging.Logger.getLogger(InsiraPinProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsiraPinProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsiraPinProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsiraPinProf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsiraPinProf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResponder;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtPin;
    // End of variables declaration//GEN-END:variables
}
