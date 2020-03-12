import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class OverAluno3 extends javax.swing.JFrame {

    Scanner in;
    PrintWriter out;
    String loginn;
    Object materia;
    Object submateria;
    JFrame father;

    /**
     * Creates new form OverAluno3
     */
    public OverAluno3() {
        initComponents();
    }

    public OverAluno3(JFrame father) {
        initComponents();
        this.father = father;
    }
    DecimalFormat df = new DecimalFormat("#.00");

    OverAluno3(String loginn1, Object materia1, Object submateria1) {
        initComponents();
        loginn = loginn1;
        materia = materia1;
        submateria = submateria1;
        atualizaDados();
    }

    public void atualizaDados() {
        ArrayList<Questoes> questao = new ArrayList();

        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Desempenho do aluno " + loginn + "");
        listModel.addElement("" + submateria + " ------- Porcentagem de acerto");
        listModel.addElement("\n");

        try {
            Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("OVERVIEW_TOPICO_ALUNO     " + loginn + "‰" + submateria);

            while (!out.equals("")) {
                String input = in.nextLine();
                if (input.substring(0, 11).equals("OVER_TOPICO")) {

                    int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                    int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                    int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                    int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +
                    int quinta = input.indexOf("‰", quarta + 1);

                    int qf = Integer.parseInt(input.substring(11, primeira));
                    int qc = Integer.parseInt(input.substring(primeira + 1, segunda));
                    int qf1 = Integer.parseInt(input.substring(segunda + 1, terceira));
                    int qc1 = Integer.parseInt(input.substring(terceira + 1, quarta));
                    int qf2 = Integer.parseInt(input.substring(quarta + 1, quinta));
                    int qc2 = Integer.parseInt(input.substring(quinta + 1));

                    questao.add(new Questoes(qc, qf, qc1, qf1, qc2, qf2));

                } else if (input.equals("NAO_RESPONDEU_AINDA")) {
                    listModel.addElement("Voce ainda nao respondeu nenhuma questao dessa submateria!");
                    break;
                } else {
                    break;
                }
            }
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(OverAluno1.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Questoes c : questao) {
            double p1 = (double) c.getQf();
            double p2 = (double) c.getQc();
            double p4 = (double) c.getQf1();
            double p5 = (double) c.getQc1();
            double p6 = (double) c.getQf2();
            double p7 = (double) c.getQc2();
            double p3 = (p2 / p1) * 100;

            if ((int) p1 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes faceis é:Voce nao fez nenhuma questão nessa dificuldade");
                listModel.addElement("\n");
            } else if ((int) p3 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes faceis é:" + "0.00%");
                listModel.addElement("\n");
            } else {
                listModel.addElement("Sua porcentagem de acerto em questoes faceis é:" + df.format(p3) + "%");
                listModel.addElement("\n");
            }

            p3 = (p5 / p4) * 100;
            if ((int) p4 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes medias é:Voce nao fez nenhuma questão nessa dificuldade");
                listModel.addElement("\n");
            } else if ((int) p3 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes medias é:" + "0.00%");
                listModel.addElement("\n");
            } else {
                listModel.addElement("Sua porcentagem de acerto em questoes medias é:" + df.format(p3) + "%");
                listModel.addElement("\n");
            }
            p3 = (p7 / p6) * 100;
            if ((int) p6 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes dificeis é:Voce nao fez nenhuma questão nessa dificuldade");
                listModel.addElement("\n");
            } else if ((int) p3 == 0) {
                listModel.addElement("Sua porcentagem de acerto em questoes dificeis é:" + "0.00%");
                listModel.addElement("\n");
            } else {
                listModel.addElement("Sua porcentagem de acerto em questoes dificeis é:" + df.format(p3) + "%");
                listModel.addElement("\n");
            }
            p3 = ((p7 + p5 + p2) / (p1 + p4 + p6)) * 100;
            if ((int) p3 == 0) {
                listModel.addElement("Sua porcentagem de acerto total é:" + "0.00%");
                listModel.addElement("\n");
            } else {
                listModel.addElement("Sua porcentagem de acerto total é:" + df.format(p3) + "%");
                listModel.addElement("\n");
            }
        }

        listMat.setModel(listModel);
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
        listMat = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma do Aluno");
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listMat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listMat.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listMat);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 960, 280));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N
        jLabel1.setText("Resultado da Disciplina por Tópico");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 360, 80));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("Voltar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 610, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("Ir para Desempenho por Matéria");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 560, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Documents-(1).jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-90, 0, 1110, 740));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        OverAluno2 mc = new OverAluno2(loginn);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);           // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        OverAluno1 mc = new OverAluno1(loginn);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(OverAluno3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OverAluno3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OverAluno3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OverAluno3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OverAluno3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listMat;
    // End of variables declaration//GEN-END:variables
}
