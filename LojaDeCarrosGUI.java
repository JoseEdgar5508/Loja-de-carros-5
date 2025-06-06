package projeto.bd.poo.meu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        //painel.add(new JLabel());
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
                
                // Adicionando ao banco de dados
                Conectarbd.sql.inserirDadosCarros(marca, modelo, ano, preco, cor, placa, chassi, status.toString());

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

                String telefone;
                try {
                    //Filtro para pegar apenas números do telefone EM STRING
                    telefone = telefoneStr.replaceAll("[^0-9]", "");
                    
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

                // Adicionando ao banco de dados
                Conectarbd.sql.inserirDadosClientes(nome, email, telefone, cidade, cpfInput, cepInput, estadoFormatado);

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

    // 1) Preencha comboBoxCarros (supondo que já exista carroComboBoxModel populado)
    JComboBox<Carro> comboBoxCarros = new JComboBox<>(this.carroComboBoxModel);

    JTextField campoCpfCliente = new JTextField();
    JTextField campoData       = new JTextField();
    JTextField campoValor      = new JTextField();
    JTextField campoMetodo     = new JTextField();
    JTextField campoObs        = new JTextField();
    JButton btnRegistrar       = new JButton("Registrar Venda");

    painel.add(new JLabel("Selecione o Carro:"));
    painel.add(comboBoxCarros);
    painel.add(new JLabel("CPF do Cliente:"));
    painel.add(campoCpfCliente);
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

    clientes.clear();
    //clientes.addAll(Conectarbd.sql.selecionarTodosClientes());

    btnRegistrar.addActionListener(e -> {
        // Seleção de carro
        Carro carroSelecionado = (Carro) comboBoxCarros.getSelectedItem();
        if (carroSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um carro.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Busca do CPF informado
        String cpfInformado = campoCpfCliente.getText().trim();
        if (cpfInformado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo CPF do cliente não pode ficar vazio.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Remove pontos e hifens caso o usuário os informe
        cpfInformado = cpfInformado.replaceAll("[^0-9]", "");

        // Procura o cliente na lista por CPF
        Cliente clienteSelecionado = null;
        for (Cliente c : clientes) {
            String cpfLimpo = c.getcpf().replaceAll("[^0-9]", "");
            if (cpfLimpo.equals(cpfInformado)) {
                clienteSelecionado = c;
                break;
            }
        }
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado com este CPF.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação da data no formato AAAA-MM-DD
        String dataVenda = campoData.getText().trim();
        if (!dataVenda.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato da data inválido. Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conversão do valor
        double valorVenda;
        try {
            valorVenda = Double.parseDouble(campoValor.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor da venda inválido. Digite um número válido.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do método de pagamento
        String metodoPagamento = campoMetodo.getText().trim();
        if (metodoPagamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O método de pagamento não pode ficar vazio.", "Erro na Venda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String observacoes = campoObs.getText().trim();

        // Cria o objeto Venda e adiciona na lista local (se quiser)
        Venda novaVenda = new Venda(carroSelecionado, clienteSelecionado, dataVenda, valorVenda, metodoPagamento, observacoes);
        vendas.add(novaVenda);

        // Insere no banco de dados
        Conectarbd.sql.inserirDadosVendas(
            carroSelecionado.getCarroID(),
            clienteSelecionado.getClienteID(),
            dataVenda,
            valorVenda,
            metodoPagamento,
            observacoes
        );
        JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    });

    return painel;
}

    private JPanel criarAbaConsultas() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnVerCarros = new JButton("Ver Todos os Carros");
        JButton btnVerCarrosDisponiveis = new JButton("Ver Carros Disponíveis");
        JButton btnVerClientes = new JButton("Ver Clientes");
        JButton btnVerVendas = new JButton("Ver Vendas");
        JButton btnVerVendasPorCliente = new JButton("Ver Vendas por Cliente");
        JButton btnVerVendasPorCarro = new JButton("Ver Vendas por Carro");
        JButton btnVerVendasPorData = new JButton("Ver Vendas por Data");
        JButton btnVendasPorValor = new JButton("Ver Vendas por Valor");
        JButton btnCarrosPorModelo = new JButton("Ver Carros por Modelo");
        JButton btnCarrosPorCor = new JButton("Ver Carros por Cor");
        JButton btnCarrosPorAno = new JButton("Ver Carros por Ano");
        JButton btnAtualizarStatus = new JButton("Atualizar Status do Carro");
        JButton btnDeletarVenda = new JButton("Deletar Venda");
        JButton btnDeletarCarro = new JButton("Deletar Carro");


        btnVerCarros.addActionListener(e -> {
            //if (carros.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum carro cadastrado.");
            //    return;
            //}
            //StringBuilder sb = new StringBuilder("Lista de Carros:\n");
            // for (int i = 0; i < carros.size(); i++) {
            //     sb.append(i).append(": ").append(carros.get(i)).append("\n");
            // }

            // Usando o método de seleção do banco de dados para pegar os carros
            String texto = Conectarbd.sql.selecionarTodosCarros(); // Pegando todos os carros do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnVerCarrosDisponiveis.addActionListener(e -> {
            //if (carros.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum carro cadastrado.");
            //    return;
            //}
            //StringBuilder sb = new StringBuilder("Lista de Carros:\n");
            // for (int i = 0; i < carros.size(); i++) {
            //     sb.append(i).append(": ").append(carros.get(i)).append("\n");
            // }

            // Usando o método de seleção do banco de dados para pegar os carros
            String texto = Conectarbd.sql.selecionarCarrosDisponíveis(); // Pegando todos os carros do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerClientes.addActionListener(e -> {
            //if (clientes.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.");
            //    return;
            //}
            //Antes:
            // StringBuilder sb = new StringBuilder("Lista de Clientes:\n");
            // for (int i = 0; i < clientes.size(); i++) {
            //     sb.append(i).append(": ").append(clientes.get(i)).append("\n");
            // }

            // Usando o método de seleção do banco de dados para pegar os clientes
            String texto = Conectarbd.sql.selecionarTodosClientes(); // Pegando todos os clientes do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Clientes Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerVendas.addActionListener(e -> {
            //if (vendas.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            //    return;
            //}
            //StringBuilder sb = new StringBuilder("Lista de Vendas:\n");
            // for (Venda v : vendas) {
            //     sb.append(v).append("\n\n");
            // }

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarTodasVendas(); // Pegando todas as vendas do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Registradas", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerVendasPorCliente.addActionListener(e -> {
            //if (vendas.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarVendasPorCliente(); // Pegando todas as vendas do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Por Cliente", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerVendasPorCarro.addActionListener(e -> {
            //if (vendas.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarVendasPorCarro(); // Pegando todas as vendas do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Por Carro", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVerVendasPorData.addActionListener(e -> {
            //if (vendas.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarVendasPorData(); // Pegando todas as vendas do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Por Data", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVendasPorValor.addActionListener(e -> {
            //if (vendas.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhuma venda registrada.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarVendasPorValor(); // Pegando todas as vendas do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Vendas Por Valor", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCarrosPorModelo.addActionListener(e -> {
            //if (carros.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum carro registrado.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarCarrosPorModelo(); // Pegando todos os carros do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Por Modelo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCarrosPorCor.addActionListener(e -> {
            //if (carros.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum carro registrado.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarCarrosPorCor(); // Pegando todos os carros do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Por Cor", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCarrosPorAno.addActionListener(e -> {
            //if (carros.isEmpty()) {
            //    JOptionPane.showMessageDialog(this, "Nenhum carro registrado.");
            //    return;
            //}

            // Usando o método de seleção do banco de dados para pegar as vendas
            String texto = Conectarbd.sql.selecionarCarrosPorAno(); // Pegando todos os carros do banco de dados

            JTextArea areaTexto = new JTextArea(texto);
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(550, 300));
            JOptionPane.showMessageDialog(this, scroll, "Carros Por Ano", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnAtualizarStatus.addActionListener(e -> {
            // 1) Cria um painel temporário para exibir no diálogo
            JPanel painelDialog = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField idField     = new JTextField();
            JTextField statusField = new JTextField();

            painelDialog.add(new JLabel("ID da Venda:"));
            painelDialog.add(idField);
            painelDialog.add(new JLabel("Novo Status:"));
            painelDialog.add(statusField);

            // 2) Exibe o diálogo modal com OK e Cancelar
            int resultado = JOptionPane.showConfirmDialog(
                this,                  // componente pai (pode ser um JFrame ou JDialog)
                painelDialog,          // o painel com os campos
                "Atualizar Status",    // título da janela
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // 3) Se o usuário clicou em OK, prossegue com validações e atualização
            if (resultado == JOptionPane.OK_OPTION) {
                String idText     = idField.getText().trim();
                String novoStatus = statusField.getText().trim();

                // Validação: campos não podem estar vazios
                if (idText.isEmpty() || novoStatus.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos corretamente.",
                        "Erro de Atualização",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Validação: ID deve ser inteiro positivo
                int idVenda;
                try {
                    idVenda = Integer.parseInt(idText);
                    if (idVenda <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "ID da Venda deve ser um número inteiro positivo.",
                        "Erro de Atualização",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // 4) Chama o método que faz a atualização no banco
                Conectarbd.sql.atualizarStatusCarro(idVenda, novoStatus);

                // 5) Confirmação de sucesso
                JOptionPane.showMessageDialog(
                    this,
                    "Status atualizado com sucesso!",
                    "Atualização de Status",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        btnDeletarVenda.addActionListener(e -> {
            // 1) Cria um painel temporário para pedir o ID da venda a ser deletada
            JPanel painelDialog = new JPanel(new GridLayout(1, 2, 5, 5));
            JTextField idField = new JTextField();

            painelDialog.add(new JLabel("ID da Venda:"));
            painelDialog.add(idField);

            // 2) Exibe o diálogo para o usuário informar o ID
            int resultado = JOptionPane.showConfirmDialog(
                this,                  // componente pai
                painelDialog,          // o painel com o campo de ID
                "Deletar Venda",       // título da janela
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // 3) Se o usuário clicou em OK, valida entrada e pergunta confirmação
            if (resultado == JOptionPane.OK_OPTION) {
                String idText = idField.getText().trim();

                // Validação: campo não pode estar vazio
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Preencha o campo de ID corretamente.",
                        "Erro de Exclusão",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Validação: ID deve ser inteiro positivo
                int idVenda;
                try {
                    idVenda = Integer.parseInt(idText);
                    if (idVenda <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "ID da Venda deve ser um número inteiro positivo.",
                        "Erro de Exclusão",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // 4) Pergunta confirmação antes de excluir
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir a venda de ID " + idVenda + "?",
                    "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // 5) Chama o método que faz a deleção no banco
                    Conectarbd.sql.deletarVenda(idVenda);

                    // 6) Confirmação de sucesso
                    JOptionPane.showMessageDialog(
                        this,
                        "Venda deletada com sucesso!",
                        "Deletar Venda",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
                // se o usuário escolher “No”, não faz nada e retorna
            }
        });

        btnDeletarCarro.addActionListener(e -> {
            // 1) Cria um painel temporário para pedir o ID do carro a ser deletado
            JPanel painelDialog = new JPanel(new GridLayout(1, 2, 5, 5));
            JTextField idField = new JTextField();

            painelDialog.add(new JLabel("ID do Carro:"));
            painelDialog.add(idField);

            // 2) Exibe o diálogo para o usuário informar o ID
            int resultado = JOptionPane.showConfirmDialog(
                this,                  // componente pai
                painelDialog,          // o painel com o campo de ID
                "Deletar Carro",       // título da janela
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // 3) Se o usuário clicou em OK, valida entrada e pergunta confirmação
            if (resultado == JOptionPane.OK_OPTION) {
                String idText = idField.getText().trim();

                // Validação: campo não pode estar vazio
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Preencha o campo de ID corretamente.",
                        "Erro de Exclusão",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Validação: ID deve ser inteiro positivo
                int idCarro;
                try {
                    idCarro = Integer.parseInt(idText);
                    if (idCarro <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "ID do Carro deve ser um número inteiro positivo.",
                        "Erro de Exclusão",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // 4) Pergunta confirmação antes de excluir
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir o carro de ID " + idCarro + "?",
                    "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // 5) Chama o método que faz a deleção no banco
                    Conectarbd.sql.deletarCarro(idCarro);

                    // 6) Confirmação de sucesso
                    JOptionPane.showMessageDialog(
                        this,
                        "Carro deletado com sucesso!",
                        "Deletar Carro",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
                // se o usuário escolher “No”, não faz nada e retorna
            }
        });

        painel.add(btnVerCarros);
        painel.add(btnVerCarrosDisponiveis);
        painel.add(btnVerClientes);
        painel.add(btnVerVendas);
        painel.add(btnVerVendasPorCliente);
        painel.add(btnVerVendasPorCarro);
        painel.add(btnVerVendasPorData);
        painel.add(btnVendasPorValor);
        painel.add(btnCarrosPorModelo);
        painel.add(btnCarrosPorCor);
        painel.add(btnCarrosPorAno);
        painel.add(btnAtualizarStatus);
        painel.add(btnDeletarVenda);
        painel.add(btnDeletarCarro);

        return painel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LojaDeCarrosGUI::new);
    }
}