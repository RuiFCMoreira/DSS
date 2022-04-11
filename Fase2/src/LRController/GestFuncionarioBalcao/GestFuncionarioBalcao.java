package LRController.GestFuncionarioBalcao;

import LRModel.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;



public class GestFuncionarioBalcao implements IGestFuncionarioBalcao {
    ILojaReparacoesModel model;



    public GestFuncionarioBalcao(ILojaReparacoesModel model){
        this.model = model;
    }

    public boolean loginFuncionarioBalcao(String username, String password) {
        if (!this.model.containsFuncionario(username)) return false;

        FuncionarioBalcao f = this.model.getFuncionarioBalcao(username);
        String fPass = f.getPassword();
        return password.equals(fPass);
    }





    public void registarPedidoOrcamento(String nomeCliente, String contacto, String nif, String email, String descricao, LocalDate data){
        PedidoOrcamento po = new PedidoOrcamento(nomeCliente,contacto,email,nif,descricao,data);
        model.adicionaPedidoOrcamento(po);
    }

    //Caso não exista disponiblidade devolve null, Caso exista devolve o username do tecnico que realizará o serviço

    public String verificaDisponiblidadeExpresso(){

        List<Tecnico> tecnicos = this.model.getTecnicos();
        String username = null;

        for (Tecnico t : tecnicos) {

            if (t.getOcupado() == false) {
                username = t.getUsername();
                t.setOcupado(true);

                break;
            }
        }
        return username;
    }



    public String registarServicoExpresso(String nif,String contacto,String descricao) {

        String username = verificaDisponiblidadeExpresso();

        if(username != null){
            PedidoExpresso pe = new PedidoExpresso(contacto, nif, username, descricao);
            model.adicionaPedidoExpresso(pe);
        }
        return username;
    }

    public List<String> getNomeFuncionarios() { return this.model.getNomeFuncionarios();}

    public List<FuncionarioBalcao> getFuncionarios() {
        return this.model.getListaDeFuncionarios();
    }

    public void registarConfirmacaoOrcamento(String nif,boolean confirm){
        PedidoOrcamento po = this.model.getPedidoOrcamento(nif);
        po.setAprovado(confirm);
    }

    public void registarEntregaEquipamentoPeloCliente(String nif,String idFuncionarioBalcao){
        Entrega e = new Entrega(nif,idFuncionarioBalcao);
        model.adicionaEntregaPeloCliente(e);
        FuncionarioBalcao fb = model.getFuncionarioBalcao(idFuncionarioBalcao);
        fb.incrementaRececao();
    }


    public boolean registarRecolhaEquipamentoePagamento(String nif,String idFuncionarioBalcao){

        Entrega e = this.model.getEntrega(nif);
        if(e == null) return false;

        if(e.getProntoParaRecolha() == null) return  false;

        e.setRecolhido(true);
        e.setPago(true);
        FuncionarioBalcao fb = model.getFuncionarioBalcao(idFuncionarioBalcao);
        fb.incrementaEntregas();

        return true;
    }



    public void arquivarPedidosNaoAprovados(){

        LocalDate now = LocalDate.now();

        List<PedidoOrcamento> pedidos = this.model.getListaPedidosOrcamento();

        for(PedidoOrcamento p : pedidos){

            if(!p.isAprovado()){

                LocalDate or = p.getDateOrcamentoRealizado();
                if(or != null){
                    long daysBetween = ChronoUnit.DAYS.between(or, now);
                    if(daysBetween >= 30) {
                        String id = p.getId();
                        this.model.removePedido(id);
                        colocarProntoParaRecolha(p.getId());
                    }
                }

            }

        }
    }


    public void darBaixaEquipamentosNaoRecolhidos(){

        LocalDate now = LocalDate.now();

        List<Entrega> entregas = this.model.getListaEntregas();

        for(Entrega e : entregas){

            if(e.isRecolhido()) {
                LocalDate or = e.getProntoParaRecolha();
                long daysBetween = ChronoUnit.DAYS.between(or, now);
                if (daysBetween >= 90) {
                    String id = e.getNif();
                    this.model.removeEntrega(id);
                }
            }
        }
    }


    public void colocarProntoParaRecolha(String nif){
        this.model.getEntrega(nif).setProntoParaRecolha(LocalDate.now());
    }

    public void saveFiles(){
        this.model.saveData("saves");
    }

}
