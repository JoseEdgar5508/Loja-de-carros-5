package projeto.bd.poo.meu;

public class Cliente {
    private static int contadorID = 1;
    private int clienteID;
    private String nome;
    private int telefone;
    private String email;
    private String cpf;
    private String cep;
    private String estado;
    private String cidade;

    public Cliente(String nome, int telefone, String email, String cpf, String cep, String estado, String cidade) {
        this.clienteID = contadorID++;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "ID: " + clienteID + " | " + nome + " - " + telefone + " - " + email +
                " | CPF: " + cpf + " | Endereço: " + cep + ", " + cidade + " - " + estado;
    }
    // TEM QUE CRIAR UMA FUNÇÃO PARA CADASTRAR O CLIENTE
}
