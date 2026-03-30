package main.java.br.edu.ibmec.cleancode;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private double total;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido(int id, Cliente cliente, List<Item> itens) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.status = "NOVO";
    }

    public double calcularPreco() {
        double precoPedido = 0;
        for (Item item : itens) {
            precoPedido = precoPedido + (item.getPreco() * item.getQuantidade());
        }
        return precoPedido;
    }

    public void listarPedido() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.getNome());
        for (Item item : itens) {
            System.out.println(item.getNome());
        }
        System.out.println(total);
    }


}
