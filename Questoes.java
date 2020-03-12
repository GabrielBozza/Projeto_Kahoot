/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author silve
 */
public class Questoes {

    private String pergunta;
    private int questaoID;
    private String resposta;
    private String materia;
    private String dificuldade;
    private String submateria;
    private int tempo_total;//em segundos
    private int qct;
    private int qft;
    private int qc;
    private int qf;
    private int qc1;
    private int qf1;
    private int qc2;
    private int qf2;
    private String d;

    Questoes(int qc, int qf, String dificuldade) {
        this.qct = qc;
        this.qft = qf;
        this.dificuldade = dificuldade;
    }

    Questoes(int qc, int qf, int qc1, int qf1, int qc2, int qf2) {
        this.qc = qc;
        this.qf = qf;
        this.qc1 = qc1;
        this.qf1 = qf1;
        this.qc2 = qc2;
        this.qf2 = qf2;
    }

    Questoes(String pergunta, int questaoID, String resposta, String materia, String submateria, String dificuldade, int qc, int qf, int tempo_total) {
        this.qct = qc;
        this.qft = qf;
        this.tempo_total = tempo_total;
        this.pergunta = pergunta;
        this.questaoID = questaoID;
        this.resposta = resposta;
        this.materia = materia;
        this.submateria = submateria;
        this.dificuldade = dificuldade;
    }

    Questoes(String pergunta, int questaoID, String resposta) {
        this.pergunta = pergunta;
        this.questaoID = questaoID;
        this.resposta = resposta;
    }

    Questoes(String materia, int qc, int qf) {
        this.materia = materia;
        this.qc = qc;
        this.qf = qf;
    }

    Questoes(String submateria, int qc, int qf, String d) {
        this.materia = materia;
        this.qc = qc;
        this.qf = qf;
        this.d = d;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    /**
     * @return the questaoID
     */
    public int getQuestaoID() {
        return questaoID;
    }

    public int getTempoTotal() {
        return tempo_total;
    }

    /**
     * @param questaoID the questaoID to set
     */
    public void setQuestaoID(int questaoID) {
        this.questaoID = questaoID;
    }

    /**
     * @return the resposta
     */
    public String getResposta() {
        return resposta;
    }

    /**
     * @param resposta the resposta to set
     */
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the submateria
     */
    public String getSubmateria() {
        return submateria;
    }

    /**
     * @param submateria the submateria to set
     */
    public void setSubmateria(String submateria) {
        this.submateria = submateria;
    }

    /**
     * @return the qc
     */
    public int getQc() {
        return qc;
    }

    /**
     * @param qc the qc to set
     */
    public void setQc(int qc) {
        this.qc = qc;
    }

    /**
     * @return the qf
     */
    public int getQf() {
        return qf;
    }

    /**
     * @param qf the qf to set
     */
    public void setQf(int qf) {
        this.qf = qf;
    }

    /**
     * @return the qc1
     */
    public int getQc1() {
        return qc1;
    }

    /**
     * @param qc1 the qc1 to set
     */
    public void setQc1(int qc1) {
        this.qc1 = qc1;
    }

    /**
     * @return the qf1
     */
    public int getQf1() {
        return qf1;
    }

    /**
     * @param qf1 the qf1 to set
     */
    public void setQf1(int qf1) {
        this.qf1 = qf1;
    }

    /**
     * @return the qc2
     */
    public int getQc2() {
        return qc2;
    }

    /**
     * @param qc2 the qc2 to set
     */
    public void setQc2(int qc2) {
        this.qc2 = qc2;
    }

    /**
     * @return the qf2
     */
    public int getQf2() {
        return qf2;
    }

    /**
     * @param qf2 the qf2 to set
     */
    public void setQf2(int qf2) {
        this.qf2 = qf2;
    }

    /**
     * @return the d
     */
    public String getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(String d) {
        this.d = d;
    }

    /**
     * @return the dificuldade
     */
    public String getDificuldade() {
        return dificuldade;
    }

    /**
     * @param dificuldade the dificuldade to set
     */
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    /**
     * @return the qct
     */
    public int getQct() {
        return qct;
    }

    /**
     * @param qct the qct to set
     */
    public void setQct(int qct) {
        this.qct = qct;
    }

    /**
     * @return the qft
     */
    public int getQft() {
        return qft;
    }

    /**
     * @param qft the qft to set
     */
    public void setQft(int qft) {
        this.qft = qft;
    }

}
