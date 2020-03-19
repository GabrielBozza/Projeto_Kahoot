package kahootservidor;

/**
 *
 * @author g-boz
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import kahootservidor.Heuristic;

public class KahootServidor {

    public static void main(String[] args) throws Exception {

        System.out.println("O servidor do KnowHall esta funcionando...");

        ExecutorService pool = Executors.newFixedThreadPool(500);
        try (ServerSocket listener = new ServerSocket(59001)) {
            while (true) {
                pool.execute(new Handler(listener.accept()));
            }
        }
    }

    /**
     * The client handler task.
     */
    private static class Handler implements Runnable {

        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket. All the
         * interesting work is done in the run method. Remember the constructor
         * is called from the server's main method, so this has to be as short
         * as possible.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a screen name
         * until a unique one has been submitted, then acknowledges the name and
         * registers the output stream for the client in a global set, then
         * repeatedly gets inputs and broadcasts them.
         */
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {

                    String input = in.nextLine();

                    try {
                        Connection conexao = kahootservidor.ConexaoBD.getInstance().sqlConnection;
                        Statement stmt = conexao.createStatement();
                        Statement stmt1 = conexao.createStatement();
                        Statement stmt2 = conexao.createStatement();
                        Statement stmt3 = conexao.createStatement();
                        Statement stmt4 = conexao.createStatement();
                        Statement stmt5 = conexao.createStatement();
                        Statement stmt6 = conexao.createStatement();
                        Statement stmt7 = conexao.createStatement();
                        Statement stmt8 = conexao.createStatement();
                        Statement stmt9 = conexao.createStatement();
                        Statement stmt10 = conexao.createStatement();

                        if (input.substring(0, 11).equals("LOGIN_ALUNO")) {

                            ///FORMATO-->LOGIN_ALUNOnomeLogin+senha
                            String nomeLogin = input.substring(11, input.indexOf("‰"));
                            String senha = input.substring(input.indexOf("‰") + 1);

                            String SQL = "SELECT * FROM aluno WHERE user_aluno = '" + nomeLogin + "'";
                            ResultSet rs = stmt.executeQuery(SQL);

                            if (rs.next()) {

                                if (rs.getString("user_aluno").equals(nomeLogin) && rs.getString("user_pass").equals(senha)) {//LOGIN VALIDO

                                    out.println("LOGIN_VALIDO");
                                } else {
                                    out.println("ERRO_LOGIN");
                                }
                            } else {
                                out.println("ERRO_LOGIN");
                            }
                        } else if (input.substring(0, 14).equals("CADASTRO_ALUNO")) {

                            //FORMATO DA MENSAGEM: CADASTRO_ALUNONome+CPF+Email+NomeUsuario+Senha
                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +

                            String Nome = input.substring(14, primeira);
                            String CPF = input.substring(primeira + 1, segunda);
                            String Email = input.substring(segunda + 1, terceira);
                            String NomeUsuario = input.substring(terceira + 1, quarta);
                            String Senha = input.substring(quarta + 1);

                            String query1;
                            query1 = "Select * from aluno WHERE user_aluno = '" + NomeUsuario + "'";//NOMEUSUARIO EH A CHAVE PRIMARIA
                            ResultSet rs2 = stmt.executeQuery(query1);

                            if (rs2.next()) {
                                out.println("JA_CADASTRADO");
                            } else {
                                String SQL2 = "INSERT INTO aluno (nome_aluno, cpf_aluno, email_aluno,user_aluno, user_pass) VALUES ('" + Nome + "', '" + CPF + "','" + Email + "','" + NomeUsuario + "','" + Senha + "')";
                                stmt.executeUpdate(SQL2);
                                out.println("CADASTRO_EFETUADO");
                            }

                        } else if (input.substring(0, 14).equals("PIN_ALUNO     ")) {

                            ///FORMATO-->PIN_ALUNOpin
                            int PIN = Integer.parseInt(input.substring(14));

                            String SQL = "SELECT * FROM questionario WHERE pin = '" + PIN + "'";
                            ResultSet rs = stmt.executeQuery(SQL);

                            if (rs.next()) {

                                if (rs.getInt("pin") == PIN) {//LOGIN VALIDO

                                    out.println("PIN_VALIDO" + rs.getInt("num_quest"));
                                } else {
                                    out.println("PIN_INVALIDO");
                                }
                            } else {
                                out.println("PIN_INVALIDO");
                            }

                        } else if (input.equals("SELETOR_MATERIA")) {

                            String SQL = "SELECT * FROM materia";
                            ResultSet rs = stmt.executeQuery(SQL);

                            while (rs.next()) {
                                out.println("MATERIAS_OK" + rs.getString("nome_materia"));
                            }

                            out.println("PARAR_DE_ADD_MATERIAS");

                        } else if (input.substring(0, 15).equals("SELETOR_TOPICOS")) {

                            String SQL = "SELECT * FROM topic";
                            ResultSet rs = stmt.executeQuery(SQL);

                            while (rs.next()) {
                                if (input.substring(15).equals(rs.getString("nome_materia"))) {
                                    out.println("MATERIAS_OK" + rs.getString("nome"));
                                }
                            }
                            out.println("PARAR_DE_ADD_TOPICOS");

                        } else if (input.substring(0, 15).equals("LOGIN_PROFESSOR")) {

                            ///FORMATO-->LOGIN_PROFESSORnomeLogin+senha
                            String nomeLogin = input.substring(15, input.indexOf("‰"));
                            String senha = input.substring(input.indexOf("‰") + 1);

                            String SQL = "SELECT * FROM professor WHERE user_prof = '" + nomeLogin + "'";
                            ResultSet rs = stmt.executeQuery(SQL);

                            if (rs.next()) {

                                if (rs.getString("user_prof").equals(nomeLogin) && rs.getString("pass_prof").equals(senha)) {//LOGIN VALIDO

                                    out.println("LOGIN_VALIDO");
                                } else {
                                    out.println("ERRO_LOGIN");
                                }
                            } else {
                                out.println("ERRO_LOGIN");
                            }

                        } else if (input.substring(0, 18).equals("CADASTRO_PROFESSOR")) {

                            //FORMATO DA MENSAGEM: CADASTRO_PROFESSORNome+CPF+Email+NomeUsuario+Senha
                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +

                            String Nome = input.substring(18, primeira);
                            String CPF = input.substring(primeira + 1, segunda);
                            String Email = input.substring(segunda + 1, terceira);
                            String NomeUsuario = input.substring(terceira + 1, quarta);
                            String Senha = input.substring(quarta + 1);

                            String query1;
                            query1 = "Select * from professor WHERE user_prof = '" + NomeUsuario + "'";//NOMEUSUARIO EH A CHAVE PRIMARIA
                            ResultSet rs2 = stmt.executeQuery(query1);

                            if (rs2.next()) {
                                out.println("JA_CADASTRADO");
                            } else {
                                String SQL2 = "INSERT INTO professor (nome_prof, cpf_prof, email_prof,user_prof, pass_prof) VALUES ('" + Nome + "', '" + CPF + "','" + Email + "','" + NomeUsuario + "','" + Senha + "')";
                                stmt.executeUpdate(SQL2);
                                out.println("CADASTRO_EFETUADO");
                            }

                        } else if (input.substring(0, 18).equals("PIN_PROFESSOR     ")) {

                            ///FORMATO-->PIN_PROFESSOR     pin+nomeProf
                            int PIN = Integer.parseInt(input.substring(18, input.indexOf("‰")));
                            String Prof = input.substring(input.indexOf("‰") + 1);

                            String SQL = "SELECT * FROM questionario WHERE pin = '" + PIN + "'";
                            ResultSet rs = stmt.executeQuery(SQL);

                            if (rs.next()) {

                                if (rs.getInt("pin") == PIN && rs.getString("user_prof").equals(Prof)) {//LOGIN VALIDO

                                    out.println("PIN_VALIDO" + rs.getInt("num_quest"));
                                } else if (rs.getInt("pin") == PIN && !rs.getString("user_prof").equals(Prof)) {
                                    out.println("PIN_DE_OUTRO");
                                } else {
                                    out.println("PIN_INVALIDO");
                                }
                            } else {
                                out.println("PIN_INVALIDO");
                            }

                        } else if (input.substring(0, 18).equals("CADASTRAR_QUESTAO ")) {

                            //FORMATO DA MENSAGEM: CADASTRO_PROFESSORNome+CPF+Email+NomeUsuario+Senha
                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +
                            int quinta = input.indexOf("‰", quarta + 1);
                            int sexta = input.indexOf("‰", quinta + 1);
                            int setima = input.indexOf("‰", sexta + 1);
                            int oitava = input.indexOf("‰", setima + 1);
                            int nona = input.indexOf("‰", oitava + 1);
                            int decima = input.indexOf("‰", nona + 1);

                            String UsuarioProf = input.substring(18, primeira);
                            String Materia = input.substring(primeira + 1, segunda);
                            String Submateria = input.substring(segunda + 1, terceira);
                            String Cadastra1 = input.substring(terceira + 1, quarta);
                            String Cadastra2 = input.substring(quarta + 1, quinta);
                            String Pergunta = input.substring(quinta + 1, sexta);
                            Integer Num_assert = Integer.parseInt(input.substring(sexta + 1, setima));
                            Integer Minuto = Integer.parseInt(input.substring(setima + 1, oitava));
                            Integer Segundo = Integer.parseInt(input.substring(oitava + 1, nona));
                            String Dificuldade = input.substring(nona + 1, decima);
                            Integer qtd1 = Integer.parseInt(input.substring(decima + 1));

                            Integer QuestionarioID = 0;
                            Integer QuestaoID = 0;

                            String query1;
                            query1 = "SELECT max(pin) as max_id FROM questionario";//NOMEUSUARIO EH A CHAVE PRIMARIA
                            ResultSet rs2 = stmt.executeQuery(query1);

                            if (rs2.next()) {
                                if (qtd1 == 1) {//EH A PRIMEIRA QUESTAO DO QUESTIONARIO
                                    String query2;
                                    query2 = "INSERT INTO questionario (user_prof) VALUES ('" + UsuarioProf + "')";
                                    stmt2.executeUpdate(query2);
                                }
                                QuestionarioID = rs2.getInt("max_id");//PIN DO ULTIMO QUESTIONARIO CADASTRADO-----SE ESTIVER A PARTIR DA 2 QUESTAO A CADASTRAR NAO TEM QUE INSERIR!!!
                            }
                            if (qtd1 == 1) {
                                QuestionarioID++;
                            }
                            String query3;
                            query3 = "SELECT max(questaoID) as maxl_id FROM questoes";//NOMEUSUARIO EH A CHAVE PRIMARIA
                            ResultSet rs3 = stmt.executeQuery(query3);
                            if (rs3.next()) {
                                QuestaoID = rs3.getInt("maxl_id");//CODIGO DA ULTIMA QUESTAO CADASTRADA
                            }
                            if (Materia.equals("Selecionar Outro")) {
                                String query4;
                                query4 = "INSERT INTO materia(nome_materia) VALUES ('" + Cadastra1 + "')";//CADASTRA A NOVA MATERIA
                                stmt.executeUpdate(query4);
                            }
                            if (Submateria.equals("Selecionar Outro")) {
                                if (Materia.equals("Selecionar Outro")) {
                                    String query5;
                                    query5 = "INSERT INTO topic(nome_materia, nome) VALUES ('" + Cadastra1 + "','" + Cadastra2 + "')";
                                    stmt.executeUpdate(query5);
                                    String query6;
                                    query6 = "INSERT INTO questoes (pergunta, assert,materia,submateria,question_id, minuto,segundo,dificuldade,qc,qf) VALUES ('" + Pergunta + "','" + Num_assert + "','"
                                            + Cadastra1 + "','" + Cadastra2 + "','" + (QuestionarioID) + "','" + Minuto + "','" + Segundo + "','" + Dificuldade + "','" + (int) 0 + "','" + (int) 0 + "')";
                                    stmt.executeUpdate(query6);
                                    out.println("QUESTAO_CADASTRADA" + QuestionarioID + "‰" + QuestaoID);
                                } else {
                                    String query7;
                                    query7 = "INSERT INTO topic(nome_materia, nome) VALUES ('" + Materia + "','" + Cadastra2 + "')";
                                    stmt.executeUpdate(query7);

                                    String query8;
                                    query8 = "INSERT INTO questoes (pergunta, assert,materia,submateria,question_id, minuto,segundo,dificuldade,qc,qf) VALUES ('" + Pergunta + "'," + Num_assert + ",'" + Materia + "','" + Cadastra2 + "','" + (QuestionarioID + 1) + "'," + Minuto + "," + Segundo + ",'" + Dificuldade + "'," + 0 + "," + 0 + ")";
                                    stmt2.executeUpdate(query8);
                                    out.println("QUESTAO_CADASTRADA" + QuestionarioID + "‰" + QuestaoID);
                                }
                            } else {
                                String query9;
                                query9 = "INSERT INTO questoes (pergunta, assert,materia,submateria,question_id, minuto,segundo,dificuldade,qc,qf) VALUES ('" + Pergunta + "','" + Num_assert
                                        + "','" + Materia + "','" + Submateria + "','" + (QuestionarioID) + "','" + Minuto + "','" + Segundo + "','" + Dificuldade + "','" + (int) 0 + "','" + (int) 0 + "')";
                                stmt.executeUpdate(query9);
                                out.println("QUESTAO_CADASTRADA" + QuestionarioID + "‰" + QuestaoID);
                            }

                        } else if (input.substring(0, 19).equals("CADASTRAR_ASSERTIVA")) {

                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +

                            String ass = input.substring(19, primeira);
                            String pes = input.substring(primeira + 1, segunda);
                            int num_assert = Integer.parseInt(input.substring(segunda + 1, terceira));
                            int questao1 = Integer.parseInt(input.substring(terceira + 1, quarta)) + 1;
                            int question1 = Integer.parseInt(input.substring(quarta + 1));

                            if (num_assert > 1) {//
                                String query;
                                query = "INSERT INTO assertivas (assertiv, peso,questao_id,question_id) VALUES ('" + ass + "','" + pes + "','" + questao1 + "','" + question1 + "')";
                                stmt.executeUpdate(query);
                                out.println("PROXIMA_ASSERT" + (num_assert - 1));
                            } else if (num_assert == 1) {
                                String query;
                                query = "INSERT INTO assertivas (assertiv, peso,questao_id,question_id) VALUES ('" + ass + "','" + pes + "','" + questao1 + "','" + question1 + "')";
                                stmt.executeUpdate(query);
                                out.println("CADASTRAR_RESPOSTA");
                            } else {
                                out.println("ERRO_NA_LEITURA  ");
                            }

                        } else if (input.substring(0, 19).equals("CADASTRAR_RESPOSTA ")) {

                            String resp = input.substring(19, input.indexOf("‰"));
                            int questaoID = Integer.parseInt(input.substring(input.indexOf("‰") + 1));
                            try {
                                String query;
                                query = "UPDATE questoes SET resposta=('" + resp + "') WHERE questaoID = ('" + (questaoID + 1) + "')";
                                stmt.executeUpdate(query);

                                out.println("RESPOSTA_CADASTRADA");
                            } catch (Exception e) {
                                out.println("ERRO");
                                e.printStackTrace();
                            }
                        } else if (input.substring(0, 23).equals("QUESTIONARIO_FINALIZADO")) {

                            int qtd1 = Integer.parseInt(input.substring(23, input.indexOf("‰")));
                            int question1 = Integer.parseInt(input.substring(input.indexOf("‰") + 1));
                            try {
                                String query;
                                query = "UPDATE questionario SET num_quest = ('" + qtd1 + "') WHERE pin = ('" + question1 + "')";
                                stmt.executeUpdate(query);

                                out.println("QUESTIONARIO_CADASTRADO");
                            } catch (Exception e) {
                                out.println("ERRO");
                                e.printStackTrace();
                            }
                        } else if (input.substring(0, 23).equals("DADOS_RESP_QUESTAO     ")) {

                            int pin = Integer.parseInt(input.substring(23));

                            String query = "SELECT segundo,minuto,qc,qf,pergunta,questaoID,resposta,materia,submateria,dificuldade FROM questoes WHERE question_id = ('" + pin + "')";//pegas as infos de todas as questoes do questionario de pin=question_id
                            try {
                                ResultSet rs = stmt.executeQuery(query);
                                while (rs.next() || rs.isLast()) {
                                    String pergunta = rs.getString("pergunta");
                                    String materia = rs.getString("materia");
                                    String submateria = rs.getString("submateria");
                                    String dificuldade = rs.getString("dificuldade");
                                    int questaoID = rs.getInt("questaoID");
                                    String resposta = rs.getString("resposta");
                                    int qc1 = rs.getInt("qc");
                                    int qf1 = rs.getInt("qf");
                                    int counter1 = rs.getInt("segundo");
                                    int counter2 = rs.getInt("minuto");
                                    int counter3 = counter2 * 60 + counter1;

                                    out.println("PROX_QUESTAO" + pergunta + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + questaoID + "‰" + resposta + "‰" + qc1 + "‰" + qf1 + "‰" + counter3);
                                }
                                out.println("ACABARAM_AS_QUESTOES");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }  else if (input.substring(0, 23).equals("DADOS_RESP_QUESTAO_HEUR")) {
                            
                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +

                            String login = input.substring(23, primeira);
                            String materia1 = input.substring(primeira + 1);
                            int id_questao = Heuristic.determinaProximaQuestao(login, materia1);
                            //System.out.println("SELECT segundo,minuto,qc,qf,pergunta,questaoID,resposta,materia,submateria,dificuldade FROM questoes WHERE questaoID = " + id_questao + "");
                            String query = "SELECT segundo,minuto,qc,qf,pergunta,questaoID,resposta,materia,submateria,dificuldade FROM questoes WHERE questaoID = " + id_questao + "";//pegas as infos de todas as questoes do questionario de pin=question_id
                            try {
                                ResultSet rs = stmt.executeQuery(query);
                                while (rs.next()) {
                                    String pergunta = rs.getString("pergunta");
                                    String materia = rs.getString("materia");
                                    String submateria = rs.getString("submateria");
                                    String dificuldade = rs.getString("dificuldade");
                                    int questaoID = rs.getInt("questaoID");
                                    String resposta = rs.getString("resposta");
                                    int qc1 = rs.getInt("qc");
                                    int qf1 = rs.getInt("qf");
                                    int counter1 = rs.getInt("segundo");
                                    int counter2 = rs.getInt("minuto");
                                    int counter3 = counter2 * 60 + counter1;

                                    out.println("PROX_QUESTAO_HEURISTICA" + pergunta + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + questaoID + "‰" + resposta + "‰" + qc1 + "‰" + qf1 + "‰" + counter3);
                                    //System.out.println("PROX_QUESTAO_HEURISTICA" + pergunta + "‰" + materia + "‰" + submateria + "‰" + dificuldade + "‰" + questaoID + "‰" + resposta + "‰" + qc1 + "‰" + qf1 + "‰" + counter3);
                                    //out.println("ACABARAM_AS_QUESTOES");
                                }
                                //out.println("ACABARAM_AS_QUESTOES");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (input.substring(0, 23).equals("ASSERT_QUESTAO         ")) {

                            int questaoID = Integer.parseInt(input.substring(23));

                            String query1 = "SELECT assertiv,peso FROM assertivas WHERE questao_id = (" + questaoID + ")";
                            ResultSet rs1 = stmt.executeQuery(query1);
                            while (rs1.next()) {
                                String assertiva = rs1.getString("assertiv");
                                String peso = "" + rs1.getInt("peso");

                                out.println("PROX_ASSERT" + assertiva + "‰" + peso);
                            }
                            out.println("ACABARAM_AS_ASSERT");
                        } else if (input.substring(0, 24).equals("RESPONDER_OVERVIEW_ERROU")) {

                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +
                            int quinta = input.indexOf("‰", quarta + 1);
                            int sexta = input.indexOf("‰", quinta + 1);

                            String login = input.substring(24, primeira);
                            String materia = input.substring(primeira + 1, segunda);
                            String submateria = input.substring(segunda + 1, terceira);
                            String dificuldade = input.substring(terceira + 1, quarta);
                            Integer x = Integer.parseInt(input.substring(quarta + 1, quinta));
                            Integer qc1 = Integer.parseInt(input.substring(quinta + 1, sexta));
                            Integer qf1 = Integer.parseInt(input.substring(sexta + 1));

                            String query = "SELECT nome_materia,questoes_feitas,questoes_certas FROM overalunomateria WHERE user_aluno = ('" + login + "')";
                            ResultSet rs = stmt.executeQuery(query);

                            int cond = 0;
                            while (rs.next()) {//VAI ITERANDO OS OVERVIEWS DAS DIF MATERIAS DE UM DADO ALUNO---ESSE WHILE TODO EH SOH PARA ADD 1 AO OVERVIEW DO ALUNO NA MATERIA DA QUESTAO CORRENTE

                                String nome = rs.getString("nome_materia");
                                int qf = rs.getInt("questoes_feitas");
                                int qc = rs.getInt("questoes_certas");

                                if (nome.equals(materia)) {//SE JA TIVER RESPONDIDO ALGUMA QUESTAO DESSA MATERIA
                                    cond = 1;
                                    String query2 = "UPDATE overalunomateria SET questoes_feitas = " + (qf + 1) + " WHERE nome_materia = '" + materia + "' AND user_aluno = '" + login + "'";
                                    stmt2.executeUpdate(query2);
                                    System.out.println("UPDATE overalunomateria SET questoes_feitas = " + (qf + 1) + " WHERE nome_materia = '" + materia + "' AND user_aluno = '" + login + "'");
                                }
                            }

                            if (cond == 0) {//ALUNO AINDA NAO RESPONDEU NENHUMA QUESTAO DESSSA MATERIA--ESSE IF TODO EH SOH PARA ADD 1 OVERVIEW DO ALUNO DA MATERIA DA QUESTAO CORRENTE
                                String query3;
                                query3 = "INSERT INTO overalunomateria(user_aluno, nome_materia, questoes_feitas, questoes_certas) VALUES ('" + login + "','" + materia + "'," + 1 + "," + 0 + ")";
                                stmt5.executeUpdate(query3);
                            } //Fim overview materia

                            //overview Submateria
                            String query1 = "SELECT nome_submateria,questoes_faceis_feitas,questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas FROM overalunotopico WHERE user_aluno = ('" + login + "')";
                            ResultSet rs1 = stmt1.executeQuery(query1);

                            int cond1 = 0;
                            while (rs1.next()) {

                                String nome = rs1.getString("nome_submateria");
                                int qf = rs1.getInt("questoes_faceis_feitas");
                                int qc = rs1.getInt("questoes_faceis_certas");
                                int qf11 = rs1.getInt("questoes_medias_feitas");
                                int qc11 = rs1.getInt("questoes_medias_certas");
                                int qf2 = rs1.getInt("questoes_dificeis_feitas");
                                int qc2 = rs1.getInt("questoes_dificeis_certas");

                                if (nome.equals(submateria)) {//SE JAH TEM UM OVERVIEW DESSA SUBMATERIA 

                                    cond1 = 1;
                                    String query4 = "UPDATE overalunotopico SET questoes_faceis_feitas = " + (qf + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query6 = "UPDATE overalunotopico SET questoes_medias_feitas = " + (qf11 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query8 = "UPDATE overalunotopico SET questoes_dificeis_feitas = " + (qf2 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";

                                    if (dificuldade.equals("Facil")) {
                                        stmt4.executeUpdate(query4);
                                    } else if (dificuldade.equals("Medio")) {
                                        stmt6.executeUpdate(query6);
                                    } else if (dificuldade.equals("Dificil")) {
                                        stmt8.executeUpdate(query8);
                                    }
                                }
                            }

                            if (cond1 == 0) {//SE NAO TEM UM OVERVIEW COM ESSA SUBMATERIA

                                String query4 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 1 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + ")";
                                String query6 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 1 + "," + 0 + ")";
                                String query5 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 0 + "," + 0 + "," + 1 + "," + 0 + "," + 0 + "," + 0 + ")";

                                if (dificuldade.equals("Facil")) {
                                    stmt4.executeUpdate(query4);
                                } else if (dificuldade.equals("Medio")) {
                                    stmt5.executeUpdate(query5);
                                } else if (dificuldade.equals("Dificil")) {
                                    stmt6.executeUpdate(query6);
                                }
                            }
                            //fim overview submateria

                            // overview professor
                            String query4 = "UPDATE questoes SET qf = " + (qf1 + 1) + " WHERE questaoID = " + x + "";
                            if (qf1 + 1 >= 10) {

                                if (((double) (qc1) / (qf1 + 1)) > 0.6) {
                                    String query7 = "UPDATE questoes SET dificuldade = '" + "Facil" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);
                                } else if (((double) (qc1) / (qf1 + 1)) <= 0.4) {
                                    String query7 = "UPDATE questoes SET dificuldade = '" + "Dificil" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);

                                } else if ((double) (qc1) / (qf1 + 1) <= 0.6 && (double) (qc1 + 1) / (qf1 + 1) > 0.4) {
                                    String query7 = "UPDATE questoes SET dificuldade = '" + "Medio" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);
                                }
                            }
                            stmt4.executeUpdate(query4);

                        } else if (input.substring(0, 26).equals("RESPONDER_OVERVIEW_ACERTOU")) {

                            int primeira = input.indexOf("‰");//INDICE DA PRIMEIRA OCORRENCIA DO CH +
                            int segunda = input.indexOf("‰", primeira + 1);//INDICE DA SEGUNDA OCORRENCIA DO CH +
                            int terceira = input.indexOf("‰", segunda + 1);//INDICE DA TERCEIRA OCORRENCIA DO CH +
                            int quarta = input.indexOf("‰", terceira + 1);//INDICE DA QUARTA OCORRENCIA DO CH +
                            int quinta = input.indexOf("‰", quarta + 1);
                            int sexta = input.indexOf("‰", quinta + 1);

                            String login = input.substring(26, primeira);
                            String materia = input.substring(primeira + 1, segunda);
                            String submateria = input.substring(segunda + 1, terceira);
                            String dificuldade = input.substring(terceira + 1, quarta);
                            Integer x = Integer.parseInt(input.substring(quarta + 1, quinta));
                            Integer qc1 = Integer.parseInt(input.substring(quinta + 1, sexta));
                            Integer qf1 = Integer.parseInt(input.substring(sexta + 1));

                            // Overview Materia
                            String query = "SELECT nome_materia,questoes_feitas,questoes_certas FROM overalunomateria WHERE user_aluno = ('" + login + "')";

                            ResultSet rs = stmt.executeQuery(query);

                            int cond = 0;
                            while (rs.next()) {

                                String nome = rs.getString("nome_materia");
                                int qf = rs.getInt("questoes_feitas");
                                int qc = rs.getInt("questoes_certas");

                                if (nome.equals(materia)) {
                                    cond = 1;
                                    String query2 = "UPDATE overalunomateria SET questoes_feitas = " + (qf + 1) + " WHERE nome_materia = '" + materia + "' AND user_aluno = '" + login + "'";
                                    String query3 = "UPDATE overalunomateria SET questoes_certas = " + (qc + 1) + " WHERE nome_materia = '" + materia + "' AND user_aluno = '" + login + "'";
                                    stmt2.executeUpdate(query2);
                                    stmt3.executeUpdate(query3);
                                }
                            }

                            if (cond == 0) {
                                String query3 = "INSERT INTO overalunomateria(user_aluno, nome_materia, questoes_feitas, questoes_certas) VALUES ('" + login + "','" + materia + "'," + 1 + "," + 1 + ")";
                                stmt5.executeUpdate(query3);
                            }//Fim overview materia

                            // Overview Submateria
                            String query1 = "SELECT nome_submateria,questoes_faceis_feitas,questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas FROM overalunotopico WHERE user_aluno = ('" + login + "')";
                            ResultSet rs1 = stmt1.executeQuery(query1);

                            int cond1 = 0;
                            while (rs1.next()) {

                                String nome = rs1.getString("nome_submateria");
                                int qf = rs1.getInt("questoes_faceis_feitas");
                                int qc = rs1.getInt("questoes_faceis_certas");
                                int qf11 = rs1.getInt("questoes_medias_feitas");
                                int qc11 = rs1.getInt("questoes_medias_certas");
                                int qf2 = rs1.getInt("questoes_dificeis_feitas");
                                int qc2 = rs1.getInt("questoes_dificeis_certas");

                                if (nome.equals(submateria)) {

                                    cond1 = 1;
                                    String query4 = "UPDATE overalunotopico SET questoes_faceis_feitas = " + (qf + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query5 = "UPDATE overalunotopico SET questoes_faceis_certas = " + (qc + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query6 = "UPDATE overalunotopico SET questoes_medias_feitas = " + (qf11 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query7 = "UPDATE overalunotopico SET questoes_medias_certas = " + (qc11 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query8 = "UPDATE overalunotopico SET questoes_dificeis_feitas = " + (qf2 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    String query9 = "UPDATE overalunotopico SET questoes_dificeis_certas = " + (qc2 + 1) + " WHERE nome_submateria = '" + submateria + "' AND user_aluno = '" + login + "'";
                                    if (dificuldade.equals("Facil")) {
                                        stmt4.executeUpdate(query4);
                                        stmt5.executeUpdate(query5);
                                    } else if (dificuldade.equals("Medio")) {
                                        stmt6.executeUpdate(query6);
                                        stmt7.executeUpdate(query7);
                                    } else if (dificuldade.equals("Dificil")) {
                                        stmt8.executeUpdate(query8);
                                        stmt9.executeUpdate(query9);
                                    }
                                }
                            }

                            if (cond1 == 0) {

                                String query4 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 1 + "," + 1 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + ")";
                                String query6 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 1 + "," + 1 + ")";
                                String query5 = "INSERT INTO overalunotopico (user_aluno, nome_submateria, questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas) VALUES ('" + login + "','" + submateria + "'," + 0 + "," + 0 + "," + 1 + "," + 1 + "," + 0 + "," + 0 + ")";

                                if (dificuldade.equals("Facil")) {
                                    stmt4.executeUpdate(query4);
                                } else if (dificuldade.equals("Medio")) {
                                    stmt5.executeUpdate(query5);
                                } else if (dificuldade.equals("Dificil")) {
                                    stmt6.executeUpdate(query6);
                                }
                            }//Fim overview submateria

                            // overview professor
                            String query10 = "UPDATE questoes SET qf = " + (qf1 + 1) + " WHERE questaoID = " + x + "";
                            String query11 = "UPDATE questoes SET qc = " + (qc1 + 1) + " WHERE questaoID = " + x + "";

                            if (qf1 + 1 >= 10) {
                                String query7;
                                if (((double) (qc1 + 1) / (qf1 + 1)) > 0.6) {
                                    query7 = "UPDATE questoes SET dificuldade = '" + "Facil" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);
                                } else if (((double) (qc1 + 1) / (qf1 + 1)) <= 0.4) {
                                    query7 = "UPDATE questoes SET dificuldade = '" + "Dificil" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);
                                } else if (((double) (qc1 + 1) / (qf1 + 1)) <= 0.6 && ((qc1 + 1) / (qf1 + 1)) > 0.4) {
                                    query7 = "UPDATE questoes SET dificuldade = '" + "Medio" + "' WHERE questaoID = " + x + "";
                                    stmt10.executeUpdate(query7);
                                }
                            }
                            stmt4.executeUpdate(query10);
                            stmt5.executeUpdate(query11);
                            // fim overview professor

                        } else if (input.substring(0, 26).equals("OVERVIEW_MATERIA_ALUNO    ")) {

                            String login = input.substring(26);

                            String query = "SELECT nome_materia,questoes_feitas,questoes_certas FROM overalunomateria WHERE user_aluno = ('" + login + "')";
                            ResultSet rs = stmt.executeQuery(query);

                            int cond3 = 0;

                            while (rs.next()) {
                                cond3 = 1;
                                String materia = rs.getString("nome_materia");
                                int qf = rs.getInt("questoes_feitas");
                                int qc = rs.getInt("questoes_certas");

                                out.println("OVER_MATERIA" + materia + "‰" + qc + "‰" + qf);
                            }
                            if (cond3 == 0) {
                                out.println("NAO_RESPONDEU_AINDA");
                            } else {
                                out.println("FIM_OVERVIEW             ");
                            }
                        } else if (input.substring(0, 26).equals("OVERVIEW_TOPICO_ALUNO     ")) {

                            String login = input.substring(26, input.indexOf("‰"));
                            String submateria = input.substring(input.indexOf("‰") + 1);

                            String query = "SELECT questoes_faceis_feitas, questoes_faceis_certas,questoes_medias_feitas,questoes_medias_certas,questoes_dificeis_feitas,questoes_dificeis_certas FROM overalunotopico WHERE nome_submateria = ('" + submateria + "') AND user_aluno = ('" + login + "') ";
                            ResultSet rs = stmt.executeQuery(query);

                            int cond3 = 0;

                            while (rs.next()) {
                                cond3 = 1;

                                int qf = rs.getInt("questoes_faceis_feitas");
                                int qc = rs.getInt("questoes_faceis_certas");
                                int qf1 = rs.getInt("questoes_medias_feitas");
                                int qc1 = rs.getInt("questoes_medias_certas");
                                int qf2 = rs.getInt("questoes_dificeis_feitas");
                                int qc2 = rs.getInt("questoes_dificeis_certas");

                                out.println("OVER_TOPICO" + qf + "‰" + qc + "‰" + qf1 + "‰" + qc1 + "‰" + qf2 + "‰" + qc2);
                            }
                            if (cond3 == 0) {
                                out.println("NAO_RESPONDEU_AINDA");
                            } else {
                                out.println("FIM_OVERVIEW             ");
                            }
                        } else if (input.substring(0, 26).equals("OVERVIEW_PROFESSOR        ")) {

                            int pin = Integer.parseInt(input.substring(26));

                            String query = "SELECT dificuldade,qf,qc FROM questoes WHERE question_id = (" + pin + ")";
                            ResultSet rs = stmt.executeQuery(query);

                            int cond3 = 0;

                            while (rs.next()) {
                                cond3 = 1;

                                String dificuldade = rs.getString("dificuldade");
                                int qf = rs.getInt("qf");
                                int qc = rs.getInt("qc");

                                out.println("OVER_ALUNOS" + dificuldade + "‰" + qf + "‰" + qc);
                            }
                            if (cond3 == 0) {//NA VDD TAH SOH CHECANDO SE O QUESTIONARIO TEM QUESTOES CADASTRADAS
                                out.println("NINGUEM_RESPONDEU");
                            } else {
                                out.println("FIM_OVERVIEW             ");
                            }

                        } else if (input.substring(0, 26).equals("ABORTAR_QUESTIONARIO      ")) {

                            int qtd1 = Integer.parseInt(input.substring(26));

                            if (qtd1 != 1) {
                                String query = "DELETE FROM questionario\n"
                                        + " WHERE pin = (SELECT x.id\n"
                                        + "                         FROM (SELECT MAX(t.pin) AS id \n"
                                        + "                                 FROM questionario t) x);";
                                stmt.executeUpdate(query);

                                String query2 = "SET SQL_SAFE_UPDATES = 0";
                                stmt2.executeQuery(query2);

                                String query3 = "DELETE FROM assertivas\n"
                                        + "	    WHERE question_id = (SELECT x.id FROM (SELECT MAX(t.question_id) AS id FROM assertivas t) x);";
                                stmt3.executeUpdate(query3);

                                String query4 = "DELETE FROM questoes\n"
                                        + "	    WHERE question_id = (SELECT x.id FROM (SELECT MAX(t.question_id) AS id FROM questoes t) x);";
                                stmt4.executeUpdate(query4);
                            }
                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                ///ACOES EXECUTADAS INDEPENDENTE DO TRY
                ////////////
                ///////
            }
            try {
                socket.close();
            } catch (IOException e) {

            }
        }///RUN TERMINA AQUI
    }
}
