package main.java.br.edu.ibmec.cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/* classe que faz o gerenciamento dos pedidos
1- adiciona na lista
2 - lista os pedidos
3 - busca pedido por id
 */
public class GerenciadorPedidos {

    private List<Pedido> pedidos = new ArrayList<>();
    private final Db database = new Db();

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    public void salvarNovoPedido(Pedido pedido) {
        //passar o id do pedido
        pedido.setId(pedidos.size() + 1);

        pedidos.add(pedido);
        database.salvar(pedido);
    }

    public List<Pedido> ListarPedidos() {
        return pedidos;
    }

    public Optional<Pedido> buscarPedido(int id) {

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return Optional.of(pedido);
            }
        }
        return Optional.empty();
    }

    public boolean cancelarPedido(int id) {
        for(Pedido pedido : pedidos) {

            if(pedido.getId() == id) {
                pedido.setStatus("CANCELADO");
                return true;
            }
        }

        return false;
    }
}
