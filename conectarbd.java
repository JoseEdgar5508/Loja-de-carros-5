package projeto.bd.poo.meu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//ARQUIVO DE CONEXÃO COM O BD
public class Conectarbd {
    private static final String URL = "jdbc:mysql://localhost:3306/projetobdpoo";
    private static final String USER = "root"; // User padrão do XAMPP
    private static final String PASS = ""; // Senha padrão do XAMPP

    // Iniciar conexão
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Fechar a conexão
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public class sql {
        // AQUI ESTARÃO AS QUERYS FEITAS NO BD

        // Criação das tabelas - se ainda não existirem
        public static void createVendas() {
            String sql = "CREATE TABLE IF NOT EXISTS vendas (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idCarro INT NOT NULL, " +
                    "idCliente INT NOT NULL, " +
                    "data DATE NOT NULL, " +
                    "valor DECIMAL(10, 2) NOT NULL, " +
                    "metodoPagamento VARCHAR(50) NOT NULL, " +
                    "observacoes TEXT, " +
                    "FOREIGN KEY (idCarro) REFERENCES carros(id), " + // Referência ao id do carro
                    "FOREIGN KEY (idCliente) REFERENCES clientes(id)" + // Referência ao id do cliente
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'vendas' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void createCarros() {
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nomeCarro VARCHAR(100) NOT NULL, " +
                    "marca VARCHAR(50) NOT NULL, " +
                    "modelo VARCHAR(50) NOT NULL, " +
                    "ano DATE NOT NULL, " +
                    "preco DECIMAL(10, 2) NOT NULL, " +
                    "cor VARCHAR(30) NOT NULL, " +
                    "placa VARCHAR(10) NOT NULL, " +
                    "chassi VARCHAR(20) NOT NULL, " +
                    "status VARCHAR(20) NOT NULL" + // se o carro tem avaria ou não
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'carros' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void createClientes() {
            String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "telefone VARCHAR(15), " + // Formato: (00) 00000-0000
                    "cidade VARCHAR(50) NOT NULL," +
                    "cpf VARCHAR(14) NOT NULL, " + // Formato: 000.000.000-00
                    "cep VARCHAR(10) NOT NULL, " + // Formato: 00000-000
                    "estado VARCHAR(2) NOT NULL " + // Sigla do estado (ex: SP, RJ, MG, etc.)
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'clientes' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Inserção de dados de EXEMPLO
        public static void inserirDadosExemplo() {
            try (Connection conn = getConnection()) {
                // Inserir dados de exemplo na tabela 'carros'
                String insertCarro = "INSERT INTO carros (marca, modelo, ano, preco, cor, placa, chassi, status) VALUES " +
                        "('Toyota', 'Corolla', 2020, 90000.00, 'Prata', 'ABC1234', '1HGBH41JXMN109186', 'Disponível'), " +
                        "('Honda', 'Civic', 2019, 85000.00, 'Preto', 'XYZ5678', '1HGCM82633A123456', 'Disponível')";
                conn.createStatement().executeUpdate(insertCarro);

                // Inserir dados de exemplo na tabela 'clientes'
                String insertCliente = "INSERT INTO clientes (nome, email, telefone, cidade, cpf, cep, estado) VALUES " +
                        "('João Silva', 'joao.silva@example.com', '123456789', 'São Paulo', '123.456.789-00', '01234-567', 'SP'), " +
                        "('Maria Souza', 'maria.souza@example.com', '987654321', 'Rio de Janeiro', '987.654.321-00', '76543-210', 'RJ')";
                conn.createStatement().executeUpdate(insertCliente);
                
                // Inserir dados de exemplo na tabela 'vendas'
                String insertVenda = "INSERT INTO vendas (idCarro, idCliente, data, valor, metodoPagamento, observacoes) VALUES " +
                        "(1, 1, '2023-01-01', 90000.00, 'Cartão de Crédito', 'Venda realizada com sucesso'), " +
                        "(2, 2, '2023-01-02', 85000.00, 'Dinheiro', 'Venda realizada com sucesso')";
                conn.createStatement().executeUpdate(insertVenda);
                System.out.println("Dados de exemplo inseridos com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Inserindo dados que o usuário pode inserir - Vendas
        public static void inserirDadosVendas(int idCarro, int idCliente, String data, Float valor, String metodoPagamento, String observacoes) {
            try (Connection conn = getConnection()) {
                // Inserir dados de exemplo na tabela 'vendas'
                String insertVenda = String.format("INSERT INTO vendas (idCarro, idCliente, data, valor, metodoPagamento, observacoes) VALUES " +
                        "(%d, %d, '%s', %.2f, '%s', '%s')",
                        idCarro, idCliente, data, valor, metodoPagamento, observacoes);
                conn.createStatement().executeUpdate(insertVenda);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Inserindo dados que o usuário pode inserir - Clientes
        public static void inserirDadosClientes(String nome, String email, String telefone, String cidade, String cpf, String cep, String estado) {
            try (Connection conn = getConnection()) {
                // Inserir dados de exemplo na tabela 'clientes'
                String insertCliente = String.format("INSERT INTO clientes (nome, email, telefone, cidade, cpf, cep, estado) VALUES " +
                        "('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        nome, email, telefone, cidade, cpf, cep, estado);
                conn.createStatement().executeUpdate(insertCliente);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //Inserindo dados que o usuário pode inserir - Carros
        public static void inserirDadosCarros(String marca, String modelo, int ano, double preco, String cor, String placa, String chassi, String status) {
            try (Connection conn = getConnection()) {
                // Inserir dados de exemplo na tabela 'carros'
                String insertCarro = String.format("INSERT INTO carros (marca, modelo, ano, preco, cor, placa, chassi, status) VALUES " +
                        "('%s', '%s', %d, %.2f, '%s', '%s', '%s', '%s')",
                        marca, modelo, ano, preco, cor, placa, chassi, status);
                conn.createStatement().executeUpdate(insertCarro);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //Selects que podem ser importantes:

        // Selecionar todos os carros no estoque (tabela carros)
        public static void selecionarTodosCarros() {
            String sql = "SELECT * FROM carros";
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("ID: %d | %s - %s (%d) R$%.2f | Cor: %s | Placa: %s | Chassi: %s | Status: %s%n",
                            rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                            rs.getInt("ano"), rs.getDouble("preco"), rs.getString("cor"),
                            rs.getString("placa"), rs.getString("chassi"), rs.getString("status"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Selecionar todos os clientes cadastrados (tabela clientes)
        public static void selecionarTodosClientes() {
            String sql = "SELECT * FROM clientes";
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("ID: %d | %s - %s - %s | CPF: %s | Endereço: %s, %s - %s%n",
                            rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"),
                            rs.getString("email"), rs.getString("cpf"), rs.getString("cep"),
                            rs.getString("cidade"), rs.getString("estado"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Selecionar todas as vendas realizadas (tabela vendas)
        public static void selecionarTodasVendas() {
            String sql = "SELECT * FROM vendas";
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("ID: %d | Carro ID: %d | Cliente ID: %d | Data: %s | Valor: R$%.2f | Método de Pagamento: %s | Observações: %s%n",
                            rs.getInt("id"), rs.getInt("idCarro"), rs.getInt("idCliente"),
                            rs.getDate("data"), rs.getFloat("valor"), rs.getString("metodoPagamento"),
                            rs.getString("observacoes"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Selecionar vendas por cliente (tabela vendas) - saber se tem clientes que compraram mais de um carro
        public static void selecionarVendasPorCliente() {
            String sql = """
                    SELECT
                        c.clienteID,
                        c.nome                  AS nomeCliente,
                        COUNT(v.idCliente)      AS totalCompras
                    FROM
                        vendas v
                    INNER JOIN cliente c
                            ON v.idCliente = c.clienteID
                    GROUP BY
                        c.clienteID,
                        c.nome
                    ORDER BY
                        totalCompras DESC
                    LIMIT 10; -- Limitar ao top 10
                        """; 
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Cliente ID: " + rs.getInt("clienteID") +
                        " | Nome: " + rs.getString("nomeCliente") +
                        " | Total de Vendas: " + rs.getInt("totalCompras"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Selecionar vendas por nome do carro (tabela vendas) - saber se tem carros que foram vendidos mais de uma vez
        public static void selecionarVendasPorCarro() {
            String sql = """
                SELECT
                    c.nomeCarro         AS nomeCarro,
                    COUNT(*)            AS totalVendas
                FROM
                    vendas v
                    INNER JOIN carro c
                        ON v.idCarro = c.carroID
                GROUP BY
                    c.nomeCarro
                ORDER BY
                    totalVendas DESC;
                LIMIT 10 -- Limitar ao top 10
                    """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Carro: " + rs.getString("nomeCarro") + 
                        " | Total de Vendas: " + rs.getInt("totalVendas"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Selecionar vendas por data (tabela vendas) - saber se tem vendas em um período específico
        public static void selecionarVendasPorData() {
            String sql = """
                    SELECT
                        DATE(v.data) AS dataVenda,
                        COUNT(*)      AS totalVendas
                    FROM
                        vendas v
                    GROUP BY
                        DATE(v.data)
                    ORDER BY
                        dataVenda
                    LIMIT 10; -- Limitar ao top 10
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Data da Venda: " + rs.getDate("dataVenda") +
                        " | Total de Vendas: " + rs.getInt("totalVendas"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        

        // Selecionar vendas por valor (tabela vendas) - talvez uma média de preço dos carros vendidos
        public static void selecionarVendasPorValor() {
            String sql = """
                    SELECT
                        AVG(valor) AS mediaValor,
                        MIN(valor) AS menorValor,
                        MAX(valor) AS maiorValor
                    FROM
                        vendas;
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Média de Valor: R$" + rs.getDouble("mediaValor") +
                        " | Menor Valor: R$" + rs.getDouble("menorValor") +
                        " | Maior Valor: R$" + rs.getDouble("maiorValor"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Selecionar carros por marca (tabela carros) - saber a marca mais vendida
        public static void selecionarCarrosPorMarca() {
            String sql = """
                    SELECT
                        marca,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        marca
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Marca: " + rs.getString("marca") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Selecionar carros por modelo (tabela carros)- saber o modelo mais vendido
        public static void selecionarCarrosPorModelo() {
            String sql = """
                    SELECT
                        modelo,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        modelo
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Modelo: " + rs.getString("modelo") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Selecionar carros por ano (tabela carros) - saber se tem carros de um ano específico
        public static void selecionarCarrosPorAno() {
            String sql = """
                    SELECT
                        ano,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        ano
                    ORDER BY
                        ano DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Ano: " + rs.getInt("ano") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Selecionar carros por cor (tabela carros) - saber se tem carros de uma cor específica
        public static void selecionarCarrosPorCor() {
            String sql = """
                    SELECT
                        cor,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        cor
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Cor: " + rs.getString("cor") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //SELECTS PRA VER DEPOIS:
        // Selecionar carros por tipo (tabela carros) - se são sedãs, SUVs, etc. - VER DEPOIS 
        // Selecionar carros por categoria (tabela carros) - se são populares, luxuosos, esportivos, etc. - VER DEPOIS
        // Selecionar vendas por método de pagamento (tabela vendas) - ver depois
        // Selecionar carros por status (tabela carros) - se são comprados carros usados ou novos - VER DEPOIS
        // Selecionar clientes por cidade (tabela clientes) - saber se tem clientes de uma cidade específica
        // Selecionar clientes por estado (tabela clientes) - saber se tem clientes de um estado específico
        //INNER JOIN para relacionar as tabelas:
    }
}