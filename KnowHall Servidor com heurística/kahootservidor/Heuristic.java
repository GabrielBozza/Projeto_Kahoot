/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kahootservidor;
  
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Vector;
import java.sql.*;

/**
 *
 * @author g-boz
 */
public class Heuristic {

    public static int determinaProximaQuestao(String id_aluno, String inMateria) throws SQLException {
        // para um aluno x
        // função heurística para um tópico H é Quantas Erradas H  / Quantas Feitas H -
        // Quantas X errou de H / Quantas X fez de H
        HashSet<String> topicos = new HashSet<>();
        Connection conn = kahootservidor.ConexaoBD.getInstance().sqlConnection;
        Statement stmt = conn.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT submateria FROM questoes WHERE materia = '" + inMateria + "'");
        // pegando todas as submaterias de uma determinada materia
        while ( rs.next() ) {
            String submateria = rs.getString("submateria");
            topicos.add(submateria);
            //System.out.println(submateria);
        }

        Vector measures = new Vector();
        for(String submateria : topicos) {
            rs = stmt.executeQuery("SELECT sum(qc), sum(qf) FROM questoes\n" +
                    "        GROUP BY submateria\n" +
                    "        HAVING submateria = '" + submateria + "';");
            int qch = 0, qfh = 0, qeh = 0, qchaluno = 0, qfhaluno = 0, qehaluno = 0;
            // pegando o número de questões feitas e questões certas de um tópico
            while(rs.next()) {
                qch = rs.getInt("sum(qc)");
                qfh = rs.getInt("sum(qf)");
                qeh = qfh - qch;
            }
            rs = stmt.executeQuery("SELECT (sum(questoes_faceis_certas) + sum(questoes_medias_certas) + sum(questoes_dificeis_certas)), \n" +
                    "(sum(questoes_faceis_feitas) + sum(questoes_medias_feitas) + sum(questoes_dificeis_feitas)) FROM overalunotopico\n" +
                    "WHERE user_aluno ='" + id_aluno + "' and nome_submateria = '" + submateria + "'\n" +
                    "GROUP BY user_aluno;");

            // pegando o número de questões feitas e questões certas de um tópico para o aluno
            while(rs.next()) {
                qchaluno = rs.getInt("(sum(questoes_faceis_certas) + sum(questoes_medias_certas) + sum(questoes_dificeis_certas))");
                qfhaluno = rs.getInt("(sum(questoes_faceis_feitas) + sum(questoes_medias_feitas) + sum(questoes_dificeis_feitas))");
                qehaluno = qfhaluno - qchaluno;
            }
            if(qfh == 0 || qfhaluno == 0) continue;
            //System.out.println("Creating measure for the topic " + submateria);
            //System.out.println("qeh, qfh, qehaluno, qfhaluno " + qeh + " " + qfh + " " + qehaluno + " " + qfhaluno);
            double tx1 = (double) qeh/ (double) qfh, tx2 = (double) qehaluno/ (double) qfhaluno;
            double med = tx1 - tx2;
            //System.out.println("med " + med);
            MedidaClass newElement = new MedidaClass(med, submateria);
            measures.add(newElement);
        }

        Collections.sort(measures, new Comparator<MedidaClass>() {
            public int compare(MedidaClass a, MedidaClass b) {
                if (a.valFuncHeur == b.valFuncHeur) return 0;
                if (a.valFuncHeur < b.valFuncHeur) return 1;
                return -1;
            }
        });

        for(int i = 0; i < measures.size(); i++) {
            MedidaClass aux = (MedidaClass) measures.get(i);
            //System.out.println(aux.topico + " " + aux.valFuncHeur);
        }

        MedidaClass melhorOpcao = (MedidaClass) measures.get(0);
        String topicoEscolhido = melhorOpcao.topico;
        //System.out.println("topico escolhido: " + topicoEscolhido);
        rs = stmt.executeQuery("SELECT questaoID FROM questoes\n" +
                "        WHERE submateria = '" + topicoEscolhido + "';");
        // pegando os IDs das questões daquele tópico
        Vector ids = new Vector();
        while(rs.next()) {
            int id = rs.getInt("questaoID");
            //System.out.println(id);
            ids.add(id);
        }

        int pos = (int) (Math.random() * (ids.size()));
        int idEscolhido = (int) ids.get(pos);
        //System.out.println("pos, idEscolhido " + pos + ", " + idEscolhido);
        return idEscolhido;
    }

}