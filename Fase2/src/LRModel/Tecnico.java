package LRModel;

import java.io.Serializable;

public class Tecnico implements Serializable {

    private String username;
    private String password;
    private boolean ocupado;

    /* Construtores */
    public Tecnico(String username, String password, boolean ocupado){
        this.username = username;
        this.password = password;
        this.ocupado = ocupado;
    }

    public Tecnico(Tecnico t){
        this.username = t.getUsername();
        this.password = t.getPassword();
        this.ocupado = t.getOcupado();
    }

    /* Getters e Setters */
    public String getUsername() { return this.username;}
    public String getPassword() { return this.password;}
    public boolean getOcupado() { return this.ocupado; }

    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password) { this.password = password;}
    public void setOcupado(boolean b){ this.ocupado = b; }
}
