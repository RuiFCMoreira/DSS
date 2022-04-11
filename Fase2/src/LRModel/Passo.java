package LRModel;

import java.time.LocalTime;

public class Passo {
    private String descricao;
    private LocalTime tempo; // tempo gasto
    private LocalTime tempoPrevisto; // tempo previsto
    private float custoPrevisto;
    private float custoFinal;
    private boolean concluido;


    public Passo(float custoPrevisto,LocalTime tempoPrevisto,String descricao,boolean concluido){
        this.custoPrevisto = custoPrevisto;
        this.tempoPrevisto = tempoPrevisto;
        this.descricao = descricao;
        this.concluido = concluido;
    }

    /* Getters */
    public String getDescricao() { return this.descricao;}
    public LocalTime getTempo() { return this.tempo;}
    public LocalTime getTempoPrevisto() { return this.tempoPrevisto; }
    public float getCustoPrevisto() { return this.custoPrevisto;}
    public float getCustoFinal(){return  this.getCustoFinal();}
    public boolean getConcluido() { return this.concluido; }

    /* Setters */

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTempoPrevisto(LocalTime tempoPrevisto){ this.tempoPrevisto = tempoPrevisto;}
    public void setTempo(LocalTime tempo) { this.tempo = tempo; }
    public void setCustoPrevisto(float custoPrevisto) { this.custoPrevisto = custoPrevisto; }
    public void setCustoFinal(float custoFinal) { this.custoFinal = custoFinal; }
    public void setConcluido(boolean concluido) { this.concluido = true; }

}