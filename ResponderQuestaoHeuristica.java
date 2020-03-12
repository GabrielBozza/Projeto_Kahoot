import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ResponderQuestaoHeuristica extends javax.swing.JFrame {

    Scanner in;
    PrintWriter out;
    Timer timer = new Timer();
    int fechar = 1;
    double qc;
    double qe;
    int counter1;
    int counter2;
    int counter3,num_erradas;
    int qc1, qf1;
    String materia;
    String submateria;
    String dificuldade;
    String p = null;
    String r;
    int x = 0;
    String login;
    int quest;
    int num;
    int num_quest;
    JFrame father;

    public ResponderQuestaoHeuristica(String login1, int quest1, int num1, int num_quest1, double qc1, double qe1,int num_erradas1,String materia1) {
        initComponents();
        login = login1;
        num = num1;
        quest = quest1;
        num_quest = num_quest1;
        qc = qc1;
        qe = qe1;
        num_erradas=num_erradas1;
        materia=materia1;
        atualizaQuestao();
        atualizaDados();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void atualizaQuestao() {
        jLabel2.setText(Integer.toString(num_quest));
    }

    public void atualizaDados() {
        ArrayList<Questoes> questao = new ArrayList();
        ArrayList<Asserts> asserts = new ArrayList();

        try {
            Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            int i = 0;//NAO EH O COND
            System.out.println("DADOS_RESP_QUESTAO_HEUR" + login +"‰"+ materia);
            out.println("DADOS_RESP_QUESTAO_HEUR" + login +"‰"+ materia);

            while (!out.equals("")) {
                String input = in.nextLine();
                if (input.substring(0, 23).equals("PROX_QUESTAO_HEURISTICA")) {

                    int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                    int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                    int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                    int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +
                    int quinta = input.indexOf("‰", quarta + 1);
                    int sexta = input.indexOf("‰", quinta + 1);
                    int setima = input.indexOf("‰", sexta + 1);
                    int oitava = input.indexOf("‰", setima + 1);

                    String pergunta = input.substring(23, primeira);
                    String materia = input.substring(primeira + 1, segunda);
                    String submateria = input.substring(segunda + 1, terceira);
                    String dificuldade = input.substring(terceira + 1, quarta);
                    int questaoID = Integer.parseInt(input.substring(quarta + 1, quinta));
                    String resposta = input.substring(quinta + 1, sexta);
                    Integer questoes_certas = Integer.parseInt(input.substring(sexta + 1, setima));
                    Integer questoes_feitas = Integer.parseInt(input.substring(setima + 1, oitava));
                    Integer counter3 = Integer.parseInt(input.substring(oitava + 1));
                    System.out.println(pergunta+" "+counter3);
                    questao.add(new Questoes(pergunta, questaoID, resposta, materia, submateria, dificuldade, questoes_certas, questoes_feitas, counter3));//CARREGA TODAS AS QSTOES DO QUESTIONARIO
                    break;
                } else {
                    break;
                }
            }
            System.out.println("ASSERT_QUESTAO         ");
            for (Questoes c : questao) {//NESSE CASO SOH VAI TER 1 MSM
                    materia = c.getMateria();
                    submateria = c.getSubmateria();
                    dificuldade = c.getDificuldade();
                    x = c.getQuestaoID();
                    p = c.getPergunta();
                    r = c.getResposta();
                    counter3 = c.getTempoTotal();
                    qf1 = c.getQft();
                    qc1 = c.getQct();
            }
            out.println("ASSERT_QUESTAO         " + x);
            System.out.println("ASSERT_QUESTAO         " + x);

            while (!out.equals("")) {
                String input = in.nextLine();

                if (input.substring(0, 11).equals("PROX_ASSERT")) {
                    int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +

                    String assertiva = input.substring(11, primeira);
                    String peso = input.substring(primeira + 1);

                    asserts.add(new Asserts(assertiva, peso));
                } else {
                    break;
                }
            }
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ResponderQuestaoHeuristica.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultListModel listModel = new DefaultListModel();//CRIA A CAIXA DDE TEXTO ONDE FICARAO AS ASSERTIVAS E SEUS RESPECT PESOS
        listModel.addElement(new String(p));//ADD A PERGUNTA A CAIXA DE TEXTO

        for (Asserts c : asserts) {
            listModel.addElement(new String(c.getPergunta() + " ------- " + c.getPeso()));
        }

        //new timer
        TimerTask task = new TimerTask() {
            public void run() {
                timeLeft.setText(Integer.toString(counter3)); //the timer lable to counter.
                counter3--;
                if (counter3 == -1) {
                    timer.cancel();
                    fechartela();

                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

        listQuest.setModel(listModel);
    }//ACABA ATUALIZA DADOS

    public void fechartela() {//SE ACABOU O TEMPO E O ALUNO NAO RESPONDEU A QUESTAO---->ERRROUU
        num_quest++;

        try {
            Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("RESPONDER_OVERVIEW_ERROU" + login + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + x + "‰" + qc1 + "‰" + qf1);//X EH A QUESTAOID

            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ResponderQuestao.class.getName()).log(Level.SEVERE, null, ex);
        }

        qe++;//NUMERO DE QUESTOES ERRADAS++
        this.setVisible(false);
        Errou mc = new Errou(login, quest, num, num_quest, qc, qe,materia,num_erradas);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);
    }

    public ResponderQuestaoHeuristica() {
        initComponents();

    }

    /**
     * Creates new form InsiraPin
     */
    public ResponderQuestaoHeuristica(JFrame father) {
        initComponents();
        this.father = father;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        textField1 = new java.awt.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listQuest = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResp = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        timeLeft = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        textField1.setText("textField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma do Aluno");
        setMinimumSize(new java.awt.Dimension(1000, 800));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listQuest.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listQuest.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listQuest);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 1010, 390));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Responda a questao");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));

        jButton1.setBackground(new java.awt.Color(204, 0, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 0));
        jButton1.setText("Enviar Resposta");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 680, 190, 50));

        txtResp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(txtResp);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 614, 210, 50));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Insira a resposta: ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 610, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("Abandonar Questionário");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 800, 290, 50));

        timeLeft.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        timeLeft.setForeground(new java.awt.Color(255, 255, 255));
        timeLeft.setText("0");
        getContentPane().add(timeLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, -130, 650, 350));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/388088-PC4BSQ-224.jpg"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -180, 1600, 1210));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String resp = txtResp.getText();
        if (txtResp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "A resposta nao pode estar em branco!", "Erro ao computar resposta!", JOptionPane.ERROR_MESSAGE);
        } else {
            //num_quest++;
            timer.cancel();
            if (resp.equals(r)) {//**************************ACERTOU A QUESTAO******************************************

                try {
                    Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);

                    out.println("RESPONDER_OVERVIEW_ACERTOU" + login + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + x + "‰" + qc1 + "‰" + qf1);

                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ResponderQuestao.class.getName()).log(Level.SEVERE, null, ex);
                }

                //qc++;
                this.setVisible(false);
                Acertou mc = new Acertou(login, quest, num, num_quest, qc, qe);
                mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                mc.setVisible(true);
            } else {//ERROU A QUESTAO------------------------------------------------------------------------------------------------------------ERROU

                try {
                    Socket socket = new Socket("192.168.91.60", 59001); //INFOS DO SERVIDOR
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);

                    out.println("RESPONDER_OVERVIEW_ERROU" + login + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + x + "‰" + qc1 + "‰" + qf1);
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ResponderQuestao.class.getName()).log(Level.SEVERE, null, ex);
                }

                //qe++;
                this.setVisible(false);
                Errou mc = new Errou(login, quest, num, num_quest, qc, qe,materia,num_erradas);
                mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                mc.setVisible(true);

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        timer.cancel();
        this.setVisible(false);
        TelaAluno1 mc = new TelaAluno1(login);
        mc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mc.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(ResponderQuestao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResponderQuestao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResponderQuestao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResponderQuestao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResponderQuestao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listQuest;
    private java.awt.TextField textField1;
    private javax.swing.JLabel timeLeft;
    private javax.swing.JTextPane txtResp;
    // End of variables declaration//GEN-END:variables
}
