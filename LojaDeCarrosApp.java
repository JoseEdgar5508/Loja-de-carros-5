package projeto.bd.poo.meu;

import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//ARQUIVO PRINCIPAL
public class LojaDeCarrosApp {
    public static void main(String[] args) {
        // Tentando conectar com o bd
        String sql = "SELECT id, nome FROM categoria";
        Connection conn = null;
        
        //Talvez eu precise usar depois:
        //Thread.sleep(2000); // Pausa por 2 segundos (2000 milissegundos)
        try {
            conn = Conectarbd.getConnection();
            System.out.println("Conexão bem-sucedida!");
            // RODAR O PROGRAMA AQUI - se a conexão for bem-sucedida
            
            // Iniciando a interface gráfica
            SwingUtilities.invokeLater(LojaDeCarrosGUI::new);

            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                // EXEMPLO DE EXECUÇÃO DE CONSULTA
                /*
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    System.out.printf("Categoria #%d: %s%n", id, nome);
                } */

                //Tentando criar tabelas e inserir dados de exemplo só para testar:
                //Conectarbd.sql.criarTudoEInserirDadosExemplo();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco: " + e.getMessage());
            e.printStackTrace();
        } finally { //VER UMA FORMA DE DESCONECTAR DO BD SÓ QUANDO O USUÁRIO SAIR
            try {
                Conectarbd.closeConnection(conn);
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }

    }
}
