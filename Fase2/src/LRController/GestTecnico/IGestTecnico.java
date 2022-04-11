package LRController.GestTecnico;


import LRModel.PedidoOrcamento;
import LRModel.Tecnico;

import java.time.LocalTime;
import java.util.List;

public interface IGestTecnico {
    public void registarPasso(String nif, float custo, LocalTime tempoPrevisto, String descricao, boolean concluido);

    public float assinalarExecucaoPasso(String nif, LocalTime tempo, float custo);

    public String determinaEquipamentoMaisUrgente();

    public String determinaEquipamentoMaisAntigo() ;

    public String getInfoPedido(String nif);

    public boolean loginTecnico(String username, String password);

    public List<String> getNomeTecnicos();

    public void registarConclusaoReparacao(String nif);

    public void registarConclusaoExpresso(String nif);

    public List<Tecnico> getTecnicos();

    public void saveFiles();

    public String getEmailOrcamento(String nif);

    public String getNomeOrcamento(String nif);

    public Float getCustoTotalPrevisto(String nif);


    public long getPrazoMaximo(String nif);

    public void colocarProntoParaRecolha(String nif);

    public String proximoPassoExecutarString(String nif);

    public void colocarReparacaoAEsperaDeAprovacao(String nif);

    public long getTempoPrevistoOrcamento(String nif);


}