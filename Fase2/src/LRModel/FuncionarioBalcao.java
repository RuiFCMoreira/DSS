package LRModel;

import java.io.Serializable;

public class FuncionarioBalcao implements Serializable {
    private String username;
    private String password;
    private int rececoesEq;
    private int entregasEq;


    public FuncionarioBalcao(String username, String password, int rececoesEq, int entregasEq){
        this.username = username;
        this.password = password;
        this.rececoesEq = rececoesEq;
        this.entregasEq = entregasEq;
    }


    // getters e setters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getRececoesEq() {
        return this.rececoesEq;
    }

    public int getEntregasEq() {
        return this.entregasEq;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRececoesEq(int rececoesEq) {
        this.rececoesEq = rececoesEq;
    }

    public void setEntregasEq(int entregasEq) {
        this.entregasEq = entregasEq;
    }



    public void incrementaRececao() {
        setRececoesEq(this.rececoesEq + 1);
    }

    public void incrementaEntregas(){
        setEntregasEq(this.entregasEq+1);
    }
}

