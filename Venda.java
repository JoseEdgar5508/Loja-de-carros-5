public class Venda {
    private static int contadorID = 1;
    private int vendaID;
    private Carro carro;
    private Cliente cliente;
    private String data;
    private double valor;
    private String metodoPagamento;
    private String observacoes;

    public Venda(Carro carro, Cliente cliente, String data, double valor, String metodoPagamento, String observacoes) {
        this.vendaID = contadorID++;
        this.carro = carro;
        this.cliente = cliente;
        this.data = data;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "ID Venda: " + vendaID + "\nCarro: " + carro + "\nCliente: " + cliente +
                "\nData: " + data + "\nValor: R$" + valor +
                "\nMétodo de Pagamento: " + metodoPagamento +
                "\nObservações: " + observacoes;
    }
}
