package LRModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoOrcamento implements Serializable {
    private String id;
    private float custoTotal;
    private String nomeCliente;
    private String contacto;
    private String email;
    private LocalDate dataPedido;
    private String descricao;
    private List<Passo> planoTrabalho;
    private String idTecnico;
    private LocalDate dateOrcamentoRealizado;
    private boolean aprovado;
    private boolean conlusaoReparacao;


    public PedidoOrcamento(String id, String nomeCliente, String contacto, String email, LocalDate data, String descricao, List<Passo> plano) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.contacto = contacto;
        this.email = email;
        this.dataPedido = data;
        this.descricao = descricao;
        this.planoTrabalho = plano;
        this.idTecnico = "";
        this.aprovado = false;
        this.conlusaoReparacao = false;
        this.dateOrcamentoRealizado = null;
        this.custoTotal = 0;
    }

    public PedidoOrcamento(String nomeCliente, String contacto, String email, String nif, String descricao, LocalDate data) {
        this.nomeCliente = nomeCliente;
        this.contacto = contacto;
        this.email = email;
        this.id = nif;
        this.dataPedido = data;
        this.descricao = descricao;
        this.planoTrabalho = new ArrayList<>();
        this.idTecnico = "";
        this.aprovado = false;
        this.conlusaoReparacao = false;
        this.custoTotal = 0;
    }

    // getters e setters
    public String getId() {
        return this.id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public String getContacto() {
        return this.contacto;
    }

    public String getEmail() {
        return this.email;
    }


    public List<Passo> getPlanoTrabalho() {
        List<Passo> res = new ArrayList<>();
        for (Passo p : this.planoTrabalho) {
            res.add(p);
        }
        return res;
    }


    public boolean isAprovado() {
        return aprovado;
    }

    public boolean isConlusaoReparacao() {
        return conlusaoReparacao;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public LocalDate getDateOrcamentoRealizado() {
        return this.dateOrcamentoRealizado;
    }

    public LocalDate getDataPedido() {
        return this.dataPedido;
    }

    public String getDescricaoPedido() {
        return this.descricao;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlanoTrabalho(List<Passo> planoTrabalho) {
        for (Passo p : planoTrabalho) {
            this.planoTrabalho.add(p);
        }
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setAprovado(boolean confirmacaoReparacao) {
        this.aprovado = confirmacaoReparacao;
    }

    public void setConlusaoReparacao(boolean conlusaoReparacao) {
        this.conlusaoReparacao = conlusaoReparacao;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public float getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(float custoTotal) {
        this.custoTotal = custoTotal;
    }

    public void setDateOrcamentoRealizado(LocalDate dateOrcamentoRealizado) {
        this.dateOrcamentoRealizado = dateOrcamentoRealizado;
    }




    public void adicionaPasso(float custoPrevisto, LocalTime tempoPrevisto, String descricao, boolean concluido) {
        Passo p = new Passo(custoPrevisto, tempoPrevisto, descricao, concluido);
        this.planoTrabalho.add(p);
    }


    public long getTempoTotalPrevisto() {
        long total = 0;
        for (Passo p : this.planoTrabalho) {
            total = total + p.getTempoPrevisto().getHour()*60 + p.getTempoPrevisto().getMinute();
        }
        return total;
    }


    //minutos
    public long getTempoTotalGasto() {
        long total = 0;
        for (Passo p : this.planoTrabalho) {
            total = total + p.getTempo().getHour()*60 + p.getTempo().getMinute();
        }
        return total;
    }

    public float getCustoTotalPrevisto() {
        float custo = 0;
        for (Passo p : this.getPlanoTrabalho()) {
            custo = custo + p.getCustoPrevisto();
        }
        return custo;
    }



}