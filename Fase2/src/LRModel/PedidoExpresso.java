package LRModel;

import java.io.Serializable;

public class PedidoExpresso implements Serializable {
    private String contacto;
    private String nif;
    private String idTecnico;
    private String descricao;
    private boolean concluido;

    public PedidoExpresso (String contacto, String nif, String idTecnico, String descricao) {
        this.contacto = contacto;
        this.nif = nif;
        this.idTecnico = idTecnico;
        this.descricao = descricao;
        this.concluido = false;
    }

    // getters e setters
    public String getContacto(){
        return this.contacto;
    }

    public String getNif() {
        return this.nif;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}
