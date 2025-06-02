package projeto.bd.poo.meu;

public enum StatusCarro {
    DISPONIVEL("Disponível"),
    VENDIDO("Vendido"),
    RESERVADO("Reservado");

    private final String descricao;

    StatusCarro(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}