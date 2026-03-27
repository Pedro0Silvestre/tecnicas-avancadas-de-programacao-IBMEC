package main.java.br.edu.ibmec.cleancode;

import java.util.ArrayList;
import java.util.List;

public class Db {

    public static List<Pedido> banco = new ArrayList<>();

    public void salvar(Pedido pedido) {
        try {
            banco.add(pedido);
            System.out.println("salvou no banco");
        } catch (Exception e) {
            System.out.println("erro ao salvar no banco");
        }
    }

    public List<Pedido> getAll() {

        return banco;
    }

    public Pedido getById(int id) {
        for (int i = 0; i < banco.size(); i++) {
            if (banco.get(i).id == id) {
                return banco.get(i);
            }
        }
        return null;
    }
}
