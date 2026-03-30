package main.java.br.edu.ibmec.cleancode;

public class CalculadoraFrete {

    public double calcularFrete(double valorComDesconto) {

         if(valorComDesconto < 100) {
            return 25.0;

        } else if(valorComDesconto< 300) {
            return 15.0;

        }
         return 0.0;
    }
}
