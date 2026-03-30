package main.java.br.edu.ibmec.cleancode;

/*
SI: Passamos a criação do cliente que antes estava em sistema para um construtor na classe Cliente
 */

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private int tipo; // 1 comum, 2 premium, 3 vip
    private ImplementarDesconto regraDesconto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public ImplementarDesconto getRegraDesconto() {
        return regraDesconto;
    }

    public void setRegraDesconto(ImplementarDesconto regraDesconto) {
        this.regraDesconto = regraDesconto;
    }

    public Cliente(int id, String nome, int tipo, ImplementarDesconto regraDesconto) {
        this.id = id;
        this.nome = nome;
        this.email = nome.replace(" ", "").toLowerCase() + "@email.com";
        this.tipo = tipo;
        this.regraDesconto = regraDesconto;

    }

    public String getTipoDesc() {
        if (tipo == 1) {
            return "comum";
        } else if (tipo == 2) {
            return "premium";
        } else if (tipo == 3) {
            return "vip";
        } else {
            return "outro";
        }
    }
}
