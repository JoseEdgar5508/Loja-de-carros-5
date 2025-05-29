import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//ARQUIVO PRINCIPAL
public class LojaDeCarrosApp {
    public static void main(String[] args) {
        //Interface
        SwingUtilities.invokeLater(LojaDeCarrosGUI::new);
        
        //Tentando conectar com o bd
        String sql = "SELECT id, nome FROM categoria";
        Connection conn = null;

        try {
            conn = conectarbd.getConnection();
            System.out.println("Conexão bem-sucedida!");

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs       = stmt.executeQuery()) {

                while (rs.next()) {
                    int id      = rs.getInt("id");
                    String nome = rs.getString("nome");
                    System.out.printf("Categoria #%d: %s%n", id, nome);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conectarbd.closeConnection(conn);
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    
    }
}
