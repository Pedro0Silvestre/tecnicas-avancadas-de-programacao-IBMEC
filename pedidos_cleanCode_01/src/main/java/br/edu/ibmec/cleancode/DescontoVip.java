package main.java.br.edu.ibmec.cleancode;

public class DescontoVip implements ImplementarDesconto{

    @Override
    public double calcularDesconto(double total) {
        return total * 0.85;
    }
}
