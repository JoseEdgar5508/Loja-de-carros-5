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
    private String status;
    //FALTA COLOCAR O NOME DO CARRO!!! 
    //Adicionar variáveis como tipo de carro e categoria, talvez.
    // Exemplo: Tipo de carro (sedã, SUV, etc.)
    // Exemplo: Categoria do carro (popular, luxuoso, esportivo, etc.)

    public Carro(String modelo, String marca, int ano, double preco, String cor, String placa, String chassi, String status) {
        this.carroID = contadorID++;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.cor = cor;
        this.placa = placa;
        this.chassi = chassi;
        this.status = status;
        
    }

    @Override
    public String toString() {
        return "ID: " + carroID + " | " + modelo + " - " + marca + " (" + ano + ") R$" + preco +
                " | Cor: " + cor + " | Placa: " + placa + " | Chassi: " + chassi + " | Status: " + status;
    }
}