package main.java.br.edu.ibmec.cleancode;

public class CalculadoraPreco {

    private final CalculadoraFrete calculadoraFrete = new CalculadoraFrete();


  public double calcularTotalPedido(Pedido pedido) {
      double subtotal = pedido.calcularPreco();

      double valorComDesconto = pedido.getCliente().getRegraDesconto().calcularDesconto(subtotal);

      double valorFrete = calculadoraFrete.calcularFrete(valorComDesconto);

      return valorComDesconto + valorFrete;


  }
}
