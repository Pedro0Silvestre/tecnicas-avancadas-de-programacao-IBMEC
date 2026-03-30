package main.java.br.edu.ibmec.cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/*
Atualmente a classe sistema atua como uma god class, para nos adequarmos ao principio da responsabilidade unica
vamos mapear quais funcionalidades ela executa agora mas que pdoeriam ser melhor distribuidas

- 1: gerenciamento de pedidos: Adiciona pedidos, busca por ID, cancela pedidos e integra com o Db,
- 2:
 */

public class Sistema {

    //instancia o leitor de entrada, nossa lista de pedidos e o banco para persistencia
    Scanner leitor = new Scanner(System.in);
    GerenciadorPedidos gerenciadorPedidos = new GerenciadorPedidos();
    CalculadoraPreco calculadoraPreco = new CalculadoraPreco();

    //metodo que roda o sistema
    public void run() {
        int operacao = -1;

        //meno do sistema UI
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

    //cria novo cliente, um novo pedido e adiciona na lista do cliente, passar para construtor em Cliente
    public void novoPedido() {
        //criação do cliente em Cliente
        System.out.println("Nome cliente:");
        String nomeCliente = leitor.nextLine();

        int tipoCliente = 0;
        ImplementarDesconto regraDesconto = null;
        while(tipoCliente == 0) {
            System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
            try {
                tipoCliente = Integer.parseInt(leitor.nextLine());
                regraDesconto = switch (tipoCliente) {
                    case 2 -> new DescontoPremium();
                    case 3 -> new DescontoVip();
                    default -> new DescontoComum();
                };
            } catch (NumberFormatException e) {
                System.out.println("Insira um numero valido");
            } catch (Exception e) {
                System.out.println("tipo errado, insira um numero valido");
            }
        }

        int idCliente = gerenciadorPedidos.getPedidos().size() + 1;
        Cliente cliente = new Cliente(idCliente, nomeCliente, tipoCliente, regraDesconto);

        //criacao do novo pedido
        int idPedido = gerenciadorPedidos.getPedidos().size() + 1;
        List<Item> itensPedido = new ArrayList<>();
        Pedido pedido = new Pedido(idPedido,cliente,itensPedido);


        //cria o item para adicionar ao pedido
        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String nomeItem = leitor.nextLine();
            double precoItem = -1;

        while (precoItem < 0) {
            System.out.println("Preco item:");

            try {
                precoItem = Double.parseDouble(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Preco invalido insira um valor numerico valido");
                precoItem = -1;
            }
            catch (Exception e) {
                System.out.println("preco do item invalido");
                precoItem = -1;
            }
        }

            System.out.println("Quantidade:");
            int quantidadeItem = -1;
            while (quantidadeItem < 0) {
                try {
                    quantidadeItem = Integer.parseInt(leitor.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Quantidade deve ser um número válido!");
                    quantidadeItem = -1;
                } catch (Exception e) {
                    quantidadeItem = -1;
                }
            }

            Item item = new Item(nomeItem, precoItem, quantidadeItem);
            pedido.getItens().add(item);

            System.out.println("Adicionar mais item? s/n");
            continua = leitor.nextLine();
        }

        pedido.setTotal(calculadoraPreco.calcularTotalPedido(pedido));
        gerenciadorPedidos.salvarNovoPedido(pedido);

            System.out.println("Pedido criado com sucesso");
            System.out.println("Id: " + pedido.getId());
            System.out.println("Cliente: " + pedido.getCliente().getNome());
            System.out.println("Total: " + pedido.getTotal());

            if (pedido.getTotal() > 500) {
                System.out.println("Pedido importante!!!");
            }
        }

    //lista os pedidos do cliente
    public void listarPedidos() {
        List<Pedido> pedidos = gerenciadorPedidos.ListarPedidos();

            if (pedidos.isEmpty()) {
                System.out.println("sem pedidos");
            } else {
                for (Pedido pedido : pedidos) {
                    System.out.println("---------------");
                    System.out.println("id: " + pedido.getId());
                    System.out.println("cliente: " + pedido.getCliente().getNome());
                    System.out.println("email: " + pedido.getCliente().getEmail());
                    System.out.println("tipo: " + pedido.getCliente().getTipo());
                    System.out.println("status: " + pedido.getStatus());
                    System.out.println("total: " + pedido.getTotal());
                    System.out.println("itens:");
                    for (int j = 0; j < pedido.getItens().size(); j++) {
                        Item it = pedido.getItens().get(j);
                        System.out.println(it.getNome() + " - " + it.getQuantidade() + " - " + it.getPreco());
                    }
                }
            }
        }

        //pelo id do pedido mosta os detalhes do pedido
        public void buscarPedido () {
            System.out.println("Digite o id:");
            int id = Integer.parseInt(leitor.nextLine());

            Optional<Pedido> pedidoOptional = gerenciadorPedidos.buscarPedido(id);

            if (pedidoOptional.isPresent()) {
                    System.out.println("Pedido encontrado");
                    Pedido pedido = pedidoOptional.get();
                    System.out.println("id: " + pedido.getId());
                    System.out.println("cliente: " + pedido.getCliente().getNome());
                    System.out.println("status: " + pedido.getStatus());
                    System.out.println("total: " + pedido.getTotal());

                    double subtotal = 0;
                    for (int j = 0; j < pedido.getItens().size(); j++) {
                        subtotal = subtotal + (pedido.getItens().get(j).getPreco() * pedido.getItens().get(j).getQuantidade());
                    }
                    System.out.println("subtotal calculado novamente: " + subtotal);

                    if (pedido.getCliente().getTipo() == 1) {
                        System.out.println("cliente comum");
                    } else if (pedido.getCliente().getTipo() == 2) {
                        System.out.println("cliente premium");
                    } else if (pedido.getCliente().getTipo() == 3) {
                        System.out.println("cliente vip");
                    } else {
                        System.out.println("cliente desconhecido");
                    }

                    for (int j = 0; j < pedido.getItens().size(); j++) {
                        Item item = pedido.getItens().get(j);
                        System.out.println("item " + (j + 1) + ": " + item.getNome() + " / " + item.getQuantidade() + " / " + item.getPreco());
                    }
                }

            else {
                System.out.println("Pedido nao encontrado!");
            }
        }

        //gera o relatiorio
        public void gerarRelatorio() {
            Relatorio r = new Relatorio();
            r.gerarRelatorio(gerenciadorPedidos.getPedidos());
        }

        // cancela um pedido pelo id
        public void cancelarPedido() {
            System.out.println("Digite id do pedido");
            int id = Integer.parseInt(leitor.nextLine());

            boolean cancelado = gerenciadorPedidos.cancelarPedido(id);

            if(cancelado) {
                System.out.println("PEDIDO CANCELADO!");
            }
            else {
                System.out.println("pedido nao existe");
            }

        }
    }

