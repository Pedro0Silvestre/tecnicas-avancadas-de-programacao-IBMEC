package main.java.br.edu.ibmec.cleancode;

public class Item {
    public String nome;
    public double preco;
    public int quantidade;

    public double x() {
        return preco * quantidade;
    }
}