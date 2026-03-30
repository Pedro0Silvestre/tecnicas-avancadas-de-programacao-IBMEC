package main.java.br.edu.ibmec.cleancode;

public class DescontoPremium implements ImplementarDesconto{
    @Override
    public double calcularDesconto(double total) {
        if (total > 200) {
            total = total - (total * 0.10);
        } else {
            total = total - (total * 0.03);
        }
        return total;
    }
}
