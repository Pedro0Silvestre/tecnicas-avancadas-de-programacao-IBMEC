package main.java.br.edu.ibmec.cleancode;

import java.util.List;

public class Relatorio {

    public void gerarRelatorio(List<Pedido> pedidoList) {
        System.out.println("======= RELATORIO =======");

        int quantidade = 0;
        double soma = 0;
        int cancelados = 0;
        int comuns = 0;
        int premiums = 0;
        int vips = 0;

        for (Pedido pedido : pedidoList) {
            quantidade++;

            soma = soma + pedido.getTotal();

            if (pedido.getStatus().equals("CANCELADO")) {
                cancelados++;
            }

            if (pedido.getCliente().getTipo() == 1) {
                comuns++;
            } else if (pedido.getCliente().getTipo() == 2) {
                premiums++;
            } else if (pedido.getCliente().getTipo() == 3) {
                vips++;
            }

            System.out.println("Pedido id: " + pedido.getId() + " - Cliente: " + pedido.getCliente().getNome() + " - Preco: " + pedido.getTotal() + " - Status: " + pedido.getStatus());

            for (int j = 0; j < pedido.getItens().size(); j++) {
                Item item = pedido.getItens().get(j);
                System.out.println("   item: " + item.getNome() + " quantidade: " + item.getQuantidade() + " preco: " + item.getPreco());
            }
        }

        System.out.println("--------------------");
        System.out.println("quantidade pedidos: " + quantidade);
        System.out.println("valor total: " + soma);
        System.out.println("cancelados: " + cancelados);
        System.out.println("clientes comuns: " + comuns);
        System.out.println("clientes premium: " + premiums);
        System.out.println("clientes vip: " + vips);

        if (quantidade > 0) {
            System.out.println("media: " + (soma / quantidade));
        } else {
            System.out.println("media: 0");
        }

        if (soma > 1000) {
            System.out.println("resultado muito bom");
        } else if (soma > 500) {
            System.out.println("resultado ok");
        } else {
            System.out.println("resultado fraco");
        }
    }
}
