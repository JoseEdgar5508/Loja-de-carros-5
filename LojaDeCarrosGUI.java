package projeto.bd.poo.meu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import projeto.bd.poo.meu.StatusCarro;

public class LojaDeCarrosGUI extends JFrame {
    private final ArrayList<Carro> carros = new ArrayList<>();
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Venda> vendas = new ArrayList<>();

    private DefaultComboBoxModel<Carro> carroComboBoxModel;

    public LojaDeCarrosGUI() {
        setTitle("Sistema da Loja de Carros");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.carroComboBoxModel = new DefaultComboBoxModel<>();

        JTabbedPane abas = new JTabbedPane();
        abas.add("Cadastro de Carros", criarAbaCarros());
        abas.add("Cadastro de Clientes", criarAbaClientes());
        abas.add("Registrar Venda", criarAbaVendas());
        abas.add("Consultas", criarAbaConsultas());

        add(abas);
        setVisible(true);
    }

    private JPanel criarAbaCarros() {
        JPanel painel = new JPanel(new GridLayout(9, 2, 5, 5));

        JTextField campoModelo = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoAno = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoCor = new JTextField();
        JTextField campoPlaca = new JTextField();
        JTextField campoChassi = new JTextField();
        // Substitua JTextField campoStatus por JComboBox<StatusCarro>
        JComboBox<StatusCarro> comboStatus = new JComboBox<>(StatusCarro.values()); // Popula com os valores do enum

        JButton btnCadastrar = new JButton("Cadastrar Carro");

        painel.add(new JLabel("Modelo:")); painel.add(campoModelo);
        painel.add(new JLabel("Marca:")); painel.add(campoMarca);
        painel.add(new JLabel("Ano:")); painel.add(campoAno);
        painel.add(new JLabel("Preço:")); painel.add(campoPreco);
        painel.add(new JLabel("Cor:")); painel.add(campoCor);
        painel.add(new JLabel("Placa:")); painel.add(campoPlaca);
        painel.add(new JLabel("Chassi:")); painel.add(campoChassi);
        painel.add(new JLabel("Status:")); painel.add(comboStatus); // Adiciona o JComboBox
        painel.add(new JLabel());
        painel.add(btnCadastrar);

        btnCadastrar.addActionListener(e -> {
            try {
                String modelo = campoModelo.getText();
                String marca = campoMarca.getText();
                int ano = Integer.parseInt(campoAno.getText());
                double preco = Double.parseDouble(campoPreco.getText());
                String cor = campoCor.getText();
                String placa = campoPlaca.getText();
                String chassi = campoChassi.getText();
                // Pega o status selecionado do JComboBox
                StatusCarro status = (StatusCarro) comboStatus.getSelectedItem();

                if (modelo.trim().isEmpty() || marca.trim().isEmpty() || cor.trim().isEmpty() ||
                        placa.trim().isEmpty() || chassi.trim().isEmpty() || status == null /* Verifica se algo foi selecionado */) {
                    JOptionPane.showMessageDialog(this,
                            "Todos os campos de carro são obrigatórios!",
                            "Erro de Entrada",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // O construtor de Carro agora espera um StatusCarro
                Carro carro = new Carro(modelo, marca, ano, preco, cor, placa, chassi, status);
                carros.add(carro);
                if (this.carroComboBoxModel != null) { // Verifica se o modelo do combobox de vendas existe
                    this.carroComboBoxModel.addElement(carro);
                }


                JOptionPane.showMessageDialog(this, "Carro cadastrado com sucesso!");

                campoModelo.setText("");
                campoMarca.setText("");
                campoAno.setText("");
                campoPreco.setText("");
                campoCor.setText("");
                campoPlaca.setText("");
                campoChassi.setText("");
                comboStatus.setSelectedIndex(0); // Define para o primeiro status (ou -1 para nenhum)

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados numéricos do carro (ano ou preço). Verifique e tente novamente.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        return painel;
    }

    private JPanel criarAbaClientes() {
        JPanel painel = new JPanel(new GridLayout(9, 2, 5, 5));
        JTextField campoNome = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoCPF = new JTextField();
        JTextField campoCEP = new JTextField();
        JTextField campoEstado = new JTextField();
        JTextField campoCidade = new JTextField();
        JButton btnCadastrar = new JButton("Cadastrar Cliente");

        painel.add(new JLabel("Nome:")); painel.add(campoNome);
        painel.add(new JLabel("Telefone:")); painel.add(campoTelefone);
        painel.add(new JLabel("Email:")); painel.add(campoEmail);
        painel.add(new JLabel("CPF:")); painel.add(campoCPF);
        painel.add(new JLabel("CEP:")); painel.add(campoCEP);
        painel.add(new JLabel("Estado (Sigla, ex: SP):")); painel.add(campoEstado);
        painel.add(new JLabel("Cidade:")); painel.add(campoCidade);
        painel.add(new JLabel());
        painel.add(btnCadastrar);

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = campoNome.getText();
                String telefoneStr = campoTelefone.getText();
                String email = campoEmail.getText();
                String cpfInput = campoCPF.getText();
                String cepInput = campoCEP.getText();
                String estadoInput = campoEstado.getText();
                String cidade = campoCidade.getText();

                if (nome.trim().isEmpty() || telefoneStr.trim().isEmpty() || email.trim().isEmpty() ||
                        cpfInput.trim().isEmpty() || cepInput.trim().isEmpty() || estadoInput.trim().isEmpty() ||
                        cidade.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Todos os campos de cliente são obrigatórios!",
                            "Erro de Entrada",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int telefone;
                try {
                    telefone = Integer.parseInt(telefoneStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Telefone inválido. Deve ser um número.",
                            "Erro de Formato",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!estadoInput.matches("[a-zA-Z]{2}")) {
                    JOptionPane.showMessageDialog(this,
                            "Sigla do Estado inválida. Deve conter exatamente 2 letras (ex: SP, RJ).",
                            "Erro de Formato",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String estadoFormatado = estadoInput.toUpperCase();

                String cpfApenasNumeros = cpfInput.replaceAll("[^0-9]", "");
                if (cpfApenasNumeros.length() != 11) {
                    JOptionPane.showMessageDialog(this,
                            "CPF inválido. Deve conter 11 dígitos numéricos.",
                            "Erro de Formato",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String cepApenasNumeros = cepInput.replaceAll("[^0-9]", "");
                if (cepApenasNumeros.length() != 8) {
                    JOptionPane.showMessageDialog(this,
                            "CEP inválido. Deve conter 8 dígitos numéricos.",
                            "Erro de Formato",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cliente cliente = new Cliente(
                        nome, telefone, email,
                        cpfInput,
                        cepInput,
                        estadoFormatado,
                        cidade
                );
                clientes.add(cliente);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

                campoNome.setText("");
                campoTelefone.setText("");
                campoEmail.setText("");
                campoCPF.setText("");
                campoCEP.setText("");
                campoEstado.setText("");
                campoCidade.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Ocorreu um erro inesperado ao cadastrar cliente: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        return painel;
    }

    private JPanel criarAbaVendas() {
        JPanel painel = new JPanel(new GridLayout(7, 2, 5, 5));

        JComboBox<Carro> comboBoxCarros = new JComboBox<>(this.carroComboBoxModel);

        JTextField campoIndiceCliente = new JTextField();
        JTextField campoData = new JTextField();
        JTextField campoValor = new JTextField();
        JTextField campoMetodo = new JTextField();
        JTextField campoObs = new JTextField();
        JButton btnRegistrar = new JButton("Registrar Venda");

        painel.add(new JLabel("Selecione o Carro:"));
        painel.add(comboBoxCarros);
        painel.add(new JLabel("Cliente (CPF.):"));
        painel.add(campoIndiceCliente);
        painel.add(new JLabel("Data da Venda (AAAA-MM-DD):"));
        painel.add(campoData);
        painel.add(new JLabel("Valor da Venda:"));
        painel.add(campoValor);
        painel.add(new JLabel("Método de Pagamento:"));
        painel.add(campoMetodo);
        painel.add(new JLabel("Observações:"));
        painel.add(campoObs);
        painel.add(new JLabel());
        painel.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            try {
                Carro carroSelecionado = (Carro) comboBoxCarros.getSelectedItem();

                if (carroSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecione um carro.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String indiceClienteStr = campoIndiceCliente.getText();
                Cliente clienteSelecionado = null;

                if (indiceClienteStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo do cliente não pode estar vazio.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int clienteIdx = Integer.parseInt(indiceClienteStr);
                    if (clienteIdx >= 0 && clienteIdx < clientes.size()) {
                        clienteSelecionado = clientes.get(clienteIdx);
                    } else {
                        JOptionPane.showMessageDialog(this, "Índice do cliente inválido.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Referência do cliente inválida. Use o índice numérico.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dataVenda = campoData.getText();
                if (!dataVenda.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Formato da data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double valorVenda = Double.parseDouble(campoValor.getText());
                String metodoPagamento = campoMetodo.getText();
                String observacoes = campoObs.getText();

                if (metodoPagamento.trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "O método de pagamento não pode estar vazio.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Venda novaVenda = new Venda(carroSelecionado, clienteSelecionado, dataVenda, valorVenda, metodoPagamento, observacoes);
                vendas.add(novaVenda);

                JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");

                comboBoxCarros.setSelectedIndex(-1);
                campoIndiceCliente.setText("");
                campoData.setText("");
                campoValor.setText("");
                campoMetodo.setText("");
                campoObs.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar venda: Verifique os campos numéricos (Valor, Índice do Cliente).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao registrar venda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        return painel;
    }

    private JPanel criarAbaConsultas() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnVerCarros = new JButton("Ver Carros");
        JButton btnVerClientes = new JButton("Ver Clientes");
        JButton btnVerVendas = new JButton("Ver Vendas");

        btnVerCarros.addActionListener(e -> {
            if (carros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum carro cadastrado.");
                return;
            }
            StringBuilder sb = new StringBuilder("Lista de Carros:\n");
            for (int i = 0; i < carros.size(); i++) {
                sb.append(i).append(": ").append(carros.get(i)).append("\n");
            }
            JTextArea areaTexto = new JTextArea(sb.toString());
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerClientes.addActionListener(e -> {
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.");
                return;
            }
            StringBuilder sb = new StringBuilder("Lista de Clientes:\n");
            for (int i = 0; i < clientes.size(); i++) {
                sb.append(i).append(": ").append(clientes.get(i)).append("\n");
            }
            JTextArea areaTexto = new JTextArea(sb.toString());
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Clientes Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerVendas.addActionListener(e -> {
            if (vendas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
                return;
            }
            StringBuilder sb = new StringBuilder("Lista de Vendas:\n");
            for (Venda v : vendas) {
                sb.append(v).append("\n\n");
            }
            JTextArea areaTexto = new JTextArea(sb.toString());
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Registradas", JOptionPane.INFORMATION_MESSAGE);
        });

        painel.add(btnVerCarros);
        painel.add(btnVerClientes);
        painel.add(btnVerVendas);

        return painel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LojaDeCarrosGUI::new);
    }
}