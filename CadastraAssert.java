import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class CadastraAssert extends javax.swing.JFrame {

    Scanner in;
    PrintWriter out;
    private String login1;
    private int qtd1;
    private int question1;
    private int questao1;
    private int num_assert;
    JFrame father;

    /**
     * Creates new form CadastraAssert
     */
    public CadastraAssert() {
        initComponents();
        question1 = 0;
        login1 = "";
        num_assert = 0;
        questao1 = 0;
    }

    public CadastraAssert(JFrame father) {
        initComponents();
        this.father = father;
        question1 = 0;
        login1 = "";
        num_assert = 0;
        questao1 = 0;
// TODO add your handling code here:
    }

    CadastraAssert(String login, int question, int num_asser, int questao, int qtd) {//SEMPRE ENTRA NESSE
        initComponents();
        question1 = question;//QUESTIONARIOID--PIN
        login1 = login;
        num_assert = num_asser;
        questao1 = questao;//QUESTAO ID
        qtd1 = qtd;
        atualizaClientes(num_assert);
    }

    private void atualizaClientes(int nome) {
        NumAsser.setText("" + nome);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAssert = new javax.swing.JTextPane();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtPesAssert = new javax.swing.JTextPane();
        btnAddAsser = new javax.swing.JButton();
        NumAsser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma do Professor");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setText("Insira a assertiva");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 76, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Insira a assertiva :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, -1, 32));

        jScrollPane1.setViewportView(txtAssert);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 470, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("Insira o peso da assertiva :");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 50));

        jScrollPane11.setViewportView(txtPesAssert);

        getContentPane().add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 470, -1));

        btnAddAsser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAddAsser.setText("Adicionar Assertiva");
        btnAddAsser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAsserActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddAsser, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 440, -1, -1));

        NumAsser.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        NumAsser.setText("jLabel3");
        getContentPane().add(NumAsser, new org.netbeans.lib.awtextra.AbsoluteConstraints(809, 76, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7335.jpg"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1368, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAsserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAsserActionPerformed
        String ass = txtAssert.getText();
        String pes = txtPesAssert.getText();

        if (txtAssert.getText().equals("") || txtPesAssert.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum campo pode estar em branco.", "Erro ao efetuar o Cadastro!", JOptionPane.ERROR_MESSAGE);
            txtAssert.setText("");
            txtPesAssert.setText("");
        } else {

            try {
                Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                int cond = 0;
                out.println("CADASTRAR_ASSERTIVA" + ass + "‰" + pes + "‰" + num_assert + "‰" + questao1 + "‰" + question1);

                while (!out.equals("")) {
                    cond = 1;
                    String input = in.nextLine();

                    if (input.substring(0, 14).equals("PROXIMA_ASSERT")) {
                        num_assert = Integer.parseInt(input.substring(14));//diminui no servidor
                        this.setVisible(false);
                        CadastraAssert mc = new CadastraAssert(login1, question1, num_assert, questao1, qtd1);
                        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        mc.setVisible(true);
                        socket.close();
                    } else if (input.equals("CADASTRAR_RESPOSTA")) {
                        this.setVisible(false);
                        CadastraResposta mc = new CadastraResposta(login1, question1, questao1, qtd1);
                        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        mc.setVisible(true);
                        socket.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum campo pode estar em branco.", "Erro ao efetuar o Cadastro!", JOptionPane.ERROR_MESSAGE);
                        txtAssert.setText("");
                        txtPesAssert.setText("");
                    }
                }
                if (cond == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhum campo pode estar em branco.", "Erro ao efetuar o Cadastro!", JOptionPane.ERROR_MESSAGE);
                    txtAssert.setText("");
                    txtPesAssert.setText("");
                }
                socket.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Servidor fora do ar, aguarde...", "Erro ao efetuar o Cadastro!", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(CadastraAssert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAddAsserActionPerformed

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
            java.util.logging.Logger.getLogger(CadastraAssert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastraAssert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastraAssert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastraAssert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastraAssert().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NumAsser;
    private javax.swing.JButton btnAddAsser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JTextPane txtAssert;
    private javax.swing.JTextPane txtPesAssert;
    // End of variables declaration//GEN-END:variables
}
