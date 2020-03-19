package kahootservidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author g-boz
 */
public class ConexaoBD {

    public static ConexaoBD conexao = null;//objeto estático para guardar uma instância de minha conexão

    public static ConexaoBD getInstance() {
        try {
            //verifica se ja existe uma conexão. Isso é feito verificando
            //se há algum objeto atribuido à conexao ou se a conexao sql
            //atribuída à ele está fechada.
            if (conexao == null || conexao.sqlConnection.isClosed()) {
                conexao = new ConexaoBD(); //cria uma nova conexão caso não exista uma                               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexao;
    }

    public Connection sqlConnection;//cria um objeto Connection chamado sqlConnection

    private ConexaoBD() //construtor para inicializar a conexão
    {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();//cria uma nova instancia utilizando o driver que foi adicionado à biblioteca atraves do arquivo .jarà biblioteca atraves do arquivo .jar
            String textoConexao = "jdbc:mysql://172.16.36.196/sys?user=newuser&password=LabProg2019Pumba*";
            sqlConnection = DriverManager.getConnection(textoConexao);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement createStatement() {
        throw new UnsupportedOperationException("Ainda nao suportado.");
    }

}
