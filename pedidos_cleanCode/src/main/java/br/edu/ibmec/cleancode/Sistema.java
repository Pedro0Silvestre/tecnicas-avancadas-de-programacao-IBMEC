package main.java.br.edu.ibmec.cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner leitor = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db database = new Db();

    public void run() {
        int operacao = -1;

        while (operacao != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                operacao = Integer.parseInt(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insira um número");
            } catch (Exception e) {
                System.out.println("erro");
                operacao = -1;
            }

            if (operacao == 1) {
                novoPedido();
            } else if (operacao == 2) {
                listarPedidos();
            } else if (operacao == 3) {
                buscarPedido();
            } else if (operacao == 4) {
                gerarRelatorio();
            } else if (operacao == 5) {
                cancelarPedido();
            } else if (operacao == 0) {
                System.out.println("fim");
            } else {
                System.out.println("opcao invalida");
            }
        }
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String nome = leitor.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipoCliente = 0;
        try {
            tipoCliente = Integer.parseInt(leitor.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Insira um numero valido");
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            tipoCliente = 1;
        }

        Cliente cliente = new Cliente();
        cliente.id = pedidos.size() + 1;
        cliente.nome = nome;
        cliente.tipo = tipoCliente;
        cliente.email = nome.replace(" ", "").toLowerCase() + "@email.com";

        Pedido pedido = new Pedido();
        pedido.id = pedidos.size() + 1;
        pedido.cliente = cliente;
        pedido.status = "NOVO";
        pedido.itens = new ArrayList<>();

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String nomeItem = leitor.nextLine();

            System.out.println("Preco item:");
            double precoItem;
            try {
                precoItem = Double.parseDouble(leitor.nextLine());
            } catch (Exception e) {
                System.out.println("preco do item invalido registrando como zero");
                precoItem = 0;
            }

            System.out.println("Quantidade:");
            int quantidade;
            try {
                quantidade = Integer.parseInt(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Quantidade deve ser um número válido registrando como 1");
                quantidade = 1;
            }
            catch (Exception e) {
                quantidade = 1;
            }

            Item item = new Item();
            item.nome = nomeItem;
            item.preco = precoItem;
            item.quantidade = quantidade;
            pedido.itens.add(item);

            System.out.println("Adicionar mais item? s/n");
            continua = leitor.nextLine();
        }

        pedido.total = getTotal(pedido, cliente);

            pedidos.add(pedido);
            database.salvar(pedido);

            System.out.println("Pedido criado com sucesso");
            System.out.println("Id: " + pedido.id);
            System.out.println("Cliente: " + pedido.cliente.nome);
            System.out.println("Total: " + pedido.total);

            if (pedido.total > 500) {
                System.out.println("Pedido importante!!!");
            }
        }

    private static double getTotal(Pedido pedido, Cliente cliente) {
        double total = 0;
        for (int i = 0; i < pedido.itens.size(); i++) {
            total = total + (pedido.itens.get(i).preco * pedido.itens.get(i).quantidade);
        }

        // regra de desconto
        if (cliente.tipo == 1) {
            if (total > 300) {
                total = total - (total * 0.05);
            }
        } else if (cliente.tipo == 2) {
            if (total > 200) {
                total = total - (total * 0.10);
            } else {
                total = total - (total * 0.03);
            }
        } else if (cliente.tipo == 3) {
            total = total - (total * 0.15);
        }

        // frete
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        } else {
            total = total + 0;
        }
        return total;
    }

    public void listarPedidos() {
            if (pedidos.isEmpty()) {
                System.out.println("sem pedidos");
            } else {
                for (Pedido pedido : pedidos) {
                    System.out.println("---------------");
                    System.out.println("id: " + pedido.id);
                    System.out.println("cliente: " + pedido.cliente.nome);
                    System.out.println("email: " + pedido.cliente.email);
                    System.out.println("tipo: " + pedido.cliente.tipo);
                    System.out.println("status: " + pedido.status);
                    System.out.println("total: " + pedido.total);
                    System.out.println("itens:");
                    for (int j = 0; j < pedido.itens.size(); j++) {
                        Item it = pedido.itens.get(j);
                        System.out.println(it.nome + " - " + it.quantidade + " - " + it.preco);
                    }
                }
            }
        }

        public void buscarPedido () {
            System.out.println("Digite o id:");
            int id = Integer.parseInt(leitor.nextLine());
            boolean achou = false;

            for (Pedido pedido : pedidos) {
                if (pedido.id == id) {
                    achou = true;
                    System.out.println("Pedido encontrado");
                    System.out.println("id: " + pedido.id);
                    System.out.println("cliente: " + pedido.cliente.nome);
                    System.out.println("status: " + pedido.status);
                    System.out.println("total: " + pedido.total);

                    double subtotal = 0;
                    for (int j = 0; j < pedido.itens.size(); j++) {
                        subtotal = subtotal + (pedido.itens.get(j).preco * pedido.itens.get(j).quantidade);
                    }
                    System.out.println("subtotal calculado novamente: " + subtotal);

                    if (pedido.cliente.tipo == 1) {
                        System.out.println("cliente comum");
                    } else if (pedido.cliente.tipo == 2) {
                        System.out.println("cliente premium");
                    } else if (pedido.cliente.tipo == 3) {
                        System.out.println("cliente vip");
                    } else {
                        System.out.println("cliente desconhecido");
                    }

                    for (int j = 0; j < pedido.itens.size(); j++) {
                        Item item = pedido.itens.get(j);
                        System.out.println("item " + (j + 1) + ": " + item.nome + " / " + item.quantidade + " / " + item.preco);
                    }
                }
            }

            if (!achou) {
                System.out.println("nao achou");
            }
        }

        public void gerarRelatorio() {
            Relatorio r = new Relatorio();
            r.gerarRelatorio(pedidos);
        }

        public void cancelarPedido() {
            System.out.println("Digite id do pedido");
            int id = Integer.parseInt(leitor.nextLine());

            for (Pedido pedido : pedidos) {
                if (pedido.id == id) {
                    if (pedido.status.equals("CANCELADO")) {
                        System.out.println("ja cancelado");
                    } else {
                        pedido.status = "CANCELADO";
                        System.out.println("cancelado");
                    }
                    return;
                }
            }

            System.out.println("pedido nao existe");
        }
    }

