import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//ARQUIVO DE CONEXÃO COM O BD
public class conectarbd {
    private static final String URL  = "jdbc:mysql://localhost:3306/NOME DO BANCO"; // URL do banco de dados
    // Substitua "NOME DO BANCO" pelo nome do seu banco de dados
    private static final String USER = "root"; // User padrão do XAMPP
    private static final String PASS = "";  // Senha padrão do XAMPP
    
    //Iniciar conexão
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
    //Fechar a conexão
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}