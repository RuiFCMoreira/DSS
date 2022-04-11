package LRModel;

import java.io.Serializable;
import java.time.LocalDate;

public class Entrega implements Serializable {
    private String nif;
    private String idfuncionarioBalcao;
    private LocalDate prontoParaRecolha;
    private boolean recolhido;
    private boolean pago;

    public Entrega (String nif, String idfuncionarioBalcao){
        this.nif = nif;
        this.idfuncionarioBalcao = idfuncionarioBalcao;
        this.recolhido = false;
        this.pago = false;
        this.prontoParaRecolha = null;
    }

    public String getNif() {
        return this.nif;
    }

    public String getIdfuncionarioBalcao() {
        return this.idfuncionarioBalcao;
    }

    public boolean isRecolhido() {
        return this.recolhido;
    }

    public boolean isPago(){
        return this.pago;
    }

    public LocalDate getProntoParaRecolha() {
        return prontoParaRecolha;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setIdfuncionarioBalcao(String idfuncionarioBalcao) {
        this.idfuncionarioBalcao = idfuncionarioBalcao;
    }

    public void setRecolhido(boolean entregue) {
        this.recolhido = entregue;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public void setProntoParaRecolha(LocalDate d){
        this.prontoParaRecolha = d;
    }


}
