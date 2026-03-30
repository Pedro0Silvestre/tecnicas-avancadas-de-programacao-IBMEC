package main.java.br.edu.ibmec.cleancode;

public class DescontoComum implements ImplementarDesconto{

    @Override
    public double calcularDesconto(double total) {
        if (total > 300) {
            total = total - (total * 0.05);
        }
        return total;
    }
}
