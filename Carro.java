package projeto.bd.poo.meu;

public class Carro {
    private static int contadorID = 1;
    private int carroID;
    private String modelo;
    private String marca;
    private int ano;
    private double preco;
    private String cor;
    private String placa;
    private String chassi;
    private StatusCarro status; // Alterado de String para StatusCarro

    public Carro(String modelo, String marca, int ano, double preco, String cor, String placa, String chassi, StatusCarro status) {
        this.carroID = contadorID++;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.cor = cor;
        this.placa = placa;
        this.chassi = chassi;
        this.status = status; // Atribui o valor do enum
    }

    public StatusCarro getStatus() {
        return status;
    }

    public void setStatus(StatusCarro status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + carroID + " | " + modelo + " - " + marca + " (" + ano + ") R$" + preco +
                " | Cor: " + cor + " | Placa: " + placa + " | Chassi: " + chassi + " | Status: " + status.toString(); // Usa o toString() do enum
    }
}