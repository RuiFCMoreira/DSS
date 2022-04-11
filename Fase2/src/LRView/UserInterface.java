package LRView;

import LRController.GestFuncionarioBalcao.IGestFuncionarioBalcao;
import LRController.GestGestor.IGestGestor;
import LRController.GestTecnico.IGestTecnico;

import javax.management.relation.RelationServiceNotRegisteredException;
import java.awt.image.ReplicateScaleFilter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Interface do projeto.
 */
public class UserInterface {
    private IGestFuncionarioBalcao gestFuncionarioBalcao;
    private IGestGestor gestGestor;
    private IGestTecnico gestTecnico;

    private String username;

    // Scanner para leitura
    private Scanner scin;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public UserInterface(IGestFuncionarioBalcao f, IGestGestor g, IGestTecnico t) {
        this.gestFuncionarioBalcao = f;
        this.gestGestor = g;
        this.gestTecnico = t;
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println(ANSI_BLUE + "Bem vindo ao Sistema de Gestão de Reparações!" + ANSI_RESET);
        System.out.println("A remover pedidos não aprovados com mais de 30 dias");
        this.gestFuncionarioBalcao.arquivarPedidosNaoAprovados();
        System.out.println("A remover equipamentos não levantados há mais de 90 dias");
        this.gestFuncionarioBalcao.darBaixaEquipamentosNaoRecolhidos();
        menuPrincipal();
        System.out.println(ANSI_BLUE + "Até breve..." + ANSI_RESET);
        this.gestGestor.saveFiles();
    }

    /**
     * Menu Principal - Efetuar login
     *
     * Transições possíveis:
     *      Operações sobre Gestores
     *      Operações sobre Técnicos de Reparação
     *      Operações sobre Funcionários de Balcão
     *
     */
    private void menuPrincipal() {
        Menu menuPrincipal = new Menu(new String[]{
                "Operações sobre Gestores",
                "Operações sobre Funcionários de Balcão",
                "Operações sobre Técnicos de Reparação"

        });

        menuPrincipal.setHandler(1, this::gestaoGestor);
        menuPrincipal.setHandler(2, this::gestaoFuncionarioBalcao);
        menuPrincipal.setHandler(3, this::gestaoTecnicoReparacao);

        menuPrincipal.run();
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Operações sobre Gestor
     *
     * Transições possíveis:
     *       Adicionar Gestor
     *       Remover Gestor
     *       Adicionar Funcionário de Balcão
     *       Remover Funcionário de Balcão
     *       Adicionar Técnico de Reparação
     *       Remover Técnico de Reparação
     *       Listar Gestores
     *       Listar Funcionários de Balcão
     *       Listar Técnicos de Reparação
     *       Consultar Listagem
     */
    private void gestaoGestor() {

        boolean correct_password = false;

        while(!correct_password) {


            System.out.println(ANSI_YELLOW + "Insira o seu username: " + ANSI_RESET);
            username = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira a sua password: " + ANSI_RESET);
            String password = scin.nextLine();
            correct_password = this.gestGestor.loginGestor(username, password);
            if(!correct_password) System.out.println(ANSI_RED + "Dados Log in inválidos" + ANSI_RESET);

            this.username = username;
        }
                Menu menuGestor = new Menu(new String[]{
                        "Adicionar Gestor",
                        "Remover Gestor",
                        "Adicionar Funcionário de Balcão",
                        "Remover Funcionário de Balcão",
                        "Adicionar Técnico de Reparação",
                        "Remover Técnico de Reparação",
                        "Listar Gestores",
                        "Listar Funcionários de Balcão",
                        "Listar Técnicos de Reparação",
                        "Consultar Listagem"
                });

                menuGestor.setHandler(1, this::adicionarGestor);
                menuGestor.setHandler(2, this::removerGestor);
                menuGestor.setHandler(3, this::adicionarFuncionario);
                menuGestor.setHandler(4, this::removerFuncionario);
                menuGestor.setHandler(5, this::adicionarTecnico);
                menuGestor.setHandler(6, this::removerTecnico);
                menuGestor.setHandler(7, this::listarGestores);
                menuGestor.setHandler(8, this::listarFuncionarios);
                menuGestor.setHandler(9, this::listarTecnicos);
                menuGestor.setHandler(10, this::consultarListagem);

                menuGestor.run();

    }

    /**
     *  Estado - Adicionar Gestor
     */
    private void adicionarGestor() {
    try {
        System.out.println(ANSI_YELLOW + "Id do novo Gestor: " + ANSI_RESET);
        String idGestor = scin.nextLine();
        System.out.println(ANSI_YELLOW + "Password do novo Gestor: " + ANSI_RESET);
        String pw = scin.nextLine();
        if ((this.gestGestor.adicionarGestor(idGestor, pw))) { // Função booleana pra averiguar se o Gestor com este ID exist
          System.out.println(ANSI_GREEN + "Gestor adicionado." + ANSI_RESET);
        } else {
          System.out.println(ANSI_RED + "Gestor já existe!" + ANSI_RESET);
        }
       } catch (NullPointerException e) {
        System.out.println(e.getMessage());
     }
    }

    /**
     *  Estado - Remover Gestor
     */
    private void removerGestor(){
    try{
        System.out.println(ANSI_YELLOW + "Id do Gestor: " + ANSI_RESET);
        String idGestor = scin.nextLine();
        if((this.gestGestor.removerGestor(idGestor))) {
            System.out.println(ANSI_GREEN + "Gestor removido!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Gestor não existe!" + ANSI_RESET);
        }
    } catch (NullPointerException e) {
        System.out.println(e.getMessage());
     }
    }

    /**
     *  Estado - Adicionar Funcionário de Balcõa
     */
    private void adicionarFuncionario() {
        try {
            System.out.println(ANSI_YELLOW + "Id do novo Funcionário de Balcão: " + ANSI_RESET);
            String idFuncionario = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Password do novo Funcionario de Balcão: " + ANSI_RESET);
            String pw = scin.nextLine();
            if ((this.gestGestor.adicionarFuncionarioBalcao(idFuncionario, pw))) {
                System.out.println(ANSI_GREEN + "Funcionário de Balcão adicionado." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Funcionário de Balcão já existe!" + ANSI_RESET);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Remover Funcionário de Balcão
     */
    private void removerFuncionario() {
        try{
            System.out.println(ANSI_YELLOW + "Id do Funcionário de Balcão: " + ANSI_RESET);
            String idFuncionario = scin.nextLine();
            if((this.gestGestor.removerFuncionarioBalcao(idFuncionario))) {
                System.out.println(ANSI_GREEN + "Funcionário de Balcão removido!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Funcionário de Balcão não existe!" + ANSI_RESET);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Adicionar Técnico de Reparação
     */
    private void adicionarTecnico() {
        try {
            System.out.println(ANSI_YELLOW + "Id do novo Técnico de Reparações: " + ANSI_RESET);
            String idTecnico = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Password do novo Técnico de Reparações: " + ANSI_RESET);
            String pw = scin.nextLine();
            if (this.gestGestor.adicionarTecnico(idTecnico, pw)) {
                System.out.println(ANSI_GREEN + "Técnico de Reparações adicionado." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Técnico de Reparações já existe!" + ANSI_RESET);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Remover Técnico de Reparação
     */
    private void removerTecnico() {
        try{
            System.out.println(ANSI_YELLOW + "Id do Técnico de Reparações: " + ANSI_RESET);
            String idTecnico = scin.nextLine();
            if((this.gestGestor.removerTecnico(idTecnico))) {
                System.out.println(ANSI_GREEN + "Funcionário de Balcão removido!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Funcionário de Balcão não existe!" + ANSI_RESET);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Listar Gestores
     */
    private void listarGestores() {
        try {
            System.out.println(this.gestGestor.getNomeGestores().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Listar Funcionários de Balcão
     */
    private void listarFuncionarios() {
        try {
            System.out.println(this.gestFuncionarioBalcao.getNomeFuncionarios().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     *  Estado - Listar Técnicos de Reparação
     */
    private void listarTecnicos() {
        try {
            System.out.println(this.gestTecnico.getNomeTecnicos().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Estado - Consultar Listagem
     */
    private void consultarListagem() {
        try {
            System.out.println("Número da listagem a consultar: ");
            System.out.println("1 - Por técnico: \n" +
                                  "                   - número de reparações normais/expresso \n" +
                                  "                   - a duração média das reparações normais \n" +
                                  "                   - média dos desvio em relação às durações previstas \n");

            System.out.println("2 - Recepções e entregas de equipamentos realizadas por funcionário balcão\n");
            System.out.println("3 - Todas as intervenções (passos de reparação e reparações expresso) realizadas por técnico.\n");

            int listNumber = readOptionInt(3);

            if(listNumber == 1) {
                System.out.println(gestGestor.getListagem1().toString());
            } else if (listNumber == 2) {
                System.out.println(gestGestor.getListagem2().toString());
            } else if (listNumber == 3){
                System.out.println(gestGestor.getListagem3().toString());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Operações sobre o Funcionario de Balcao
     *
     *  Transições possíveis:
     *      Registar pedido de orçamento
     *      Registar entrega do equipamento pelo cliente
     *      Registar serviço expresso
     *      Registar conclusão de um pedido
     *      Registar confirmação da reparação
     *
     */
    private void gestaoFuncionarioBalcao() {
        boolean correct_password = false;
        while(!correct_password) {
            System.out.println(ANSI_YELLOW + "Insira o seu username: " + ANSI_RESET);
            username = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira a sua password: " + ANSI_RESET);
            String password = scin.nextLine();
            correct_password = this.gestFuncionarioBalcao.loginFuncionarioBalcao(username, password);
            if(!correct_password) System.out.println(ANSI_RED + "Dados Log in inválidos" + ANSI_RESET);

        }
        this.username = username;
            Menu menuFuncionario = new Menu(new String[]{
                    "Registar pedido de orçamento",
                    "Registar entrega do equipamento pelo cliente",
                    "Registar serviço expresso",
                    "Registar confirmação do Orçamento",
                    "Registar recolha do equipamento por parte do cliente"
            });

            menuFuncionario.setHandler(1, this::registarPedidoOrcamento);
            menuFuncionario.setHandler(2, this::registarEntregaEquipamentoPeloCliente);
            menuFuncionario.setHandler(3, this::registaServicoExpresso);
            menuFuncionario.setHandler(4, this::registarConfirmacaoDoOrcamento);
            menuFuncionario.setHandler(5,this::registarRecolhaEquipamento);

            menuFuncionario.run();
    }

    /**
     *  Estado - Registar pedido de orçamento
     */
    private void registarPedidoOrcamento() {
        try {
            System.out.println(ANSI_YELLOW + "Insira nome do Cliente: " + ANSI_RESET);
            String nomeCliente = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira o contacto: " + ANSI_RESET);
            String contacto = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira o NIF: " + ANSI_RESET);
            String nif = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira o email: " + ANSI_RESET);
            String email = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira a descricao do Pedido:" + ANSI_RESET);
            String descricao = scin.nextLine();
            LocalDate ldt = LocalDate.now();
            this.gestFuncionarioBalcao.registarPedidoOrcamento(nomeCliente, contacto, nif, email,descricao,ldt);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Estado - Registar entrega do equipamento pelo cliente
     */
    private void registarEntregaEquipamentoPeloCliente() {
        try {
            System.out.println(ANSI_YELLOW + "Insira o NIF: " + ANSI_RESET);
            String nif = scin.nextLine();
            this.gestFuncionarioBalcao.registarEntregaEquipamentoPeloCliente(nif, username);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Registar serviço expresso
     */
    private void registaServicoExpresso() {
        try {
            System.out.println(ANSI_YELLOW + "Insira o NIF:" + ANSI_RESET);
            String nif = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira o contacto: " + ANSI_RESET);
            String contacto = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira a descricao" + ANSI_RESET);
            String descricao = scin.nextLine();

            String tecnico = this.gestFuncionarioBalcao.registarServicoExpresso(nif, contacto,descricao);

            if(tecnico == null) System.out.println(ANSI_RED + "Não existe disponibilidade para realizar o serviço expresso" + ANSI_RESET);
            else{
                System.out.println(ANSI_GREEN + "O Serviço expresso foi atribuido ao tecnico  " + tecnico + ANSI_RESET);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Estado - Registar confirmação da reparação
     */
    private void registarConfirmacaoDoOrcamento() {
        try {
            System.out.println(ANSI_YELLOW +"Insira o NIF:" + ANSI_RESET);
            String nif = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira '1' se aceitou ou '2' se rejeitou " + ANSI_RESET); // ver no fim se isto de ler o boolean funciona
            int op = readOptionInt(2);

            boolean b ;
            b = op == 1;
            this.gestFuncionarioBalcao.registarConfirmacaoOrcamento(nif,b);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    public void registarRecolhaEquipamento(){

        System.out.println(ANSI_YELLOW + "Insira o NIF:" + ANSI_RESET);
        String nif = scin.nextLine();

        if(!this.gestFuncionarioBalcao.registarRecolhaEquipamentoePagamento(nif,this.username))
            System.out.println(ANSI_RED + "O equipamento não está pronto para ser recolhido" + ANSI_RESET);

    }




    /**
     *  Estado - Operações sobre o Tecnico de Reparacao
     *
     *  Transições para:
     *       Registar plano de trabalho reparação
     *       Assinalar execução de passo
     *       Determina equipamento mais urgente
     *       Registar plano de trabalho para a reparação
     *       Assinalar a execução de um passo
     *       Determina equipamento mais urgente
     *
     */
    private void gestaoTecnicoReparacao() {
        boolean correct_password = false;
        while(!correct_password) {
            System.out.println(ANSI_YELLOW + "Insira o seu username: " + ANSI_RESET);
            username = scin.nextLine();
            System.out.println(ANSI_YELLOW + "Insira a sua password: " + ANSI_RESET);
            String password = scin.nextLine();
            correct_password = this.gestTecnico.loginTecnico(username, password);
            if(!correct_password) System.out.println(ANSI_RED + "Dados Log in inválidos"  + ANSI_RESET);

        }

            Menu menuTecnico = new Menu(new String[]{
                    "Registar plano de trabalho reparação",
                    "Assinalar execução dos passos",
                    "Determina equipamento mais urgente",
                    "Registar conclusão da Reparação"
            });

            menuTecnico.setHandler(1, this::registaPlanoTrabRep);
            menuTecnico.setHandler(2, this::assinalaExecucaoPassos);
            menuTecnico.setHandler(3, this::determinaEquipamentoMaisUrgente);
            menuTecnico.setHandler(4, this::registarConclusaoDaReparacao);

            menuTecnico.run();
        }


    /**
     *  Estado - Registar de trabalho reparação
     */
    private void registaPlanoTrabRep() {
        try {
            String nif = this.gestTecnico.determinaEquipamentoMaisAntigo();
            if (nif == null){
                System.out.println(ANSI_RED + "Não existe nenhum equipamento para efectuar o plano de trabalhos"  + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + "O NIF do equipamento mais antigo é:" + nif  + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Descrição do Pedido: " + this.gestTecnico.getInfoPedido(nif) + ANSI_RESET);

                System.out.println(ANSI_YELLOW + "Quando tiver analisado a descrição do pedido e o equipamento, registe os passos de trabalho: "  + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Para sair escreva quit." + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "Se o equipamento puder ser reparado digite 1, caso contrário digite 2: " + ANSI_RESET);
                int reparavel = readOptionInt(2);
                if (reparavel == 1) {
                    String line = "";
                    while (!line.equals("quit")) {
                        System.out.println(ANSI_YELLOW + "Insira o custo: " + ANSI_RESET);
                        float custo = readOptionFloat(1000);
                        System.out.println(ANSI_YELLOW + "Agora insira o tempo previsto ->" + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Insira a hora: " + ANSI_RESET);
                        int hora = readOptionInt(23);
                        System.out.println(ANSI_YELLOW + "Insira o minuto: " + ANSI_RESET);
                        int min = readOptionInt(59);
                        LocalTime lt = LocalTime.of(hora, min, 0, 0);
                        System.out.println(ANSI_YELLOW + "Insira a descrição do passo: " + ANSI_RESET);
                        String descricao = scin.nextLine();
                        this.gestTecnico.registarPasso(nif, custo, lt, descricao, false);
                        System.out.println(ANSI_PURPLE + "Se desejar terminar escreva 'quit' ou se desejar continuar prima ENTER" + ANSI_RESET);
                        line = scin.nextLine();
                    }
                        String email = this.gestTecnico.getEmailOrcamento(nif);
                        String nome = this.gestTecnico.getNomeOrcamento(nif);
                        long tempoPrevisto = this.gestTecnico.getTempoPrevistoOrcamento(nif);
                        long prazoMaximo = this.gestTecnico.getPrazoMaximo(nif);
                        Float custo = this.gestTecnico.getCustoTotalPrevisto(nif);

                        System.out.println(ANSI_PURPLE + "O seguinte orçamento deve ser enviado ao cliente: " + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Tempo previsto : " + parseMinutosToDisplay(tempoPrevisto) + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Prazo máximo : " + parseMinutosToDisplay(prazoMaximo) + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Custo total : " + custo + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Nome -> " + nome + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "Email -> " + email + ANSI_RESET);

                } else if(reparavel == 2) {
                    String email = this.gestTecnico.getEmailOrcamento(nif);
                    String nome = this.gestTecnico.getNomeOrcamento(nif);
                    System.out.println(ANSI_PURPLE+ "Deve informar o cliente que não possível reparar o equipamento e que pode levantá-lo: " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW +"Email->" + email + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Nome->" + nome + ANSI_RESET);
                    this.gestTecnico.colocarProntoParaRecolha(nif);
                }
                }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Assinalar execução de passo
     */
    private void assinalaExecucaoPassos() {
        try {
            boolean pausa = false;
            boolean valorSuperior = false;

            System.out.println(ANSI_YELLOW + "Insira o NIF associado à reparação:" + ANSI_RESET);
            String nif = scin.nextLine();

            float custoPrevisto = this.gestTecnico.getCustoTotalPrevisto(nif);

           while (!pausa && !valorSuperior) {

               String descricao = this.gestTecnico.proximoPassoExecutarString(nif);
               if (descricao.equals("Nao existem mais passos.\n")) break;
               System.out.println(descricao);

               System.out.println(ANSI_YELLOW + "Insira o custo: " + ANSI_RESET);
               float custo = readOptionFloat(1000);
               System.out.println(ANSI_YELLOW + "Agora insira o tempo gasto ->" + ANSI_RESET);
               System.out.println(ANSI_YELLOW + "Insira a hora: " + ANSI_RESET);
               int hora = readOptionInt(23);
               System.out.println(ANSI_YELLOW + "Insira o minuto: " + ANSI_RESET);
               int min = readOptionInt(59);
               LocalTime lt = LocalTime.of(hora, min, 0, 0);
               float novoCusto = this.gestTecnico.assinalarExecucaoPasso(nif, lt, custo);

               if(novoCusto > 1.2 * custoPrevisto) valorSuperior = true;

               System.out.println(ANSI_YELLOW + "Deseja colocar a reparação em pausa? (digite y)" + ANSI_RESET);
               String pausaS = scin.nextLine();

               if(pausaS.equals("y")) pausa = true;

           }

           if(valorSuperior){
               this.gestTecnico.colocarReparacaoAEsperaDeAprovacao(nif);

               String email = this.gestTecnico.getEmailOrcamento(nif);
               String nome = this.gestTecnico.getNomeOrcamento(nif);


               System.out.println(ANSI_YELLOW + "A reparação ultrapassou em 120% o valor previsto inicialmente é necessário informar o cliente  : " +nif + ANSI_RESET);
               System.out.println(ANSI_YELLOW + "Nome -> "+ nome + ANSI_RESET);
               System.out.println(ANSI_YELLOW + "Email -> "+ email + ANSI_RESET);
           }



           } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Determina equipamento mais urgente
     */
    private void determinaEquipamentoMaisUrgente() {
        try {
            String nif = this.gestTecnico.determinaEquipamentoMaisUrgente();
            if (nif != null)
                System.out.println(ANSI_YELLOW + "O NIF do equipamento mais urgente é: " + nif + ANSI_RESET);
            else
                System.out.println(ANSI_RED + "Não existem equipamentos para serem reparados." + ANSI_RESET);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Determina equipamento mais urgente
     */
    private void determinaEquipamentoMaisAntigo() {
        try {
            System.out.println(this.gestTecnico.determinaEquipamentoMaisAntigo());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registarConclusaoDaReparacao() {
        try {
            System.out.println(ANSI_YELLOW + "Insira 1 para serviço normal e 2 para serviço expresso" + ANSI_RESET);

            int op = readOptionInt(2);

            System.out.println(ANSI_YELLOW + "Insira o NIF:" + ANSI_RESET);
            String nif = scin.nextLine();


            if(op == 1) this.gestTecnico.registarConclusaoReparacao(nif);
            else this.gestTecnico.registarConclusaoExpresso(nif);


            this.gestTecnico.colocarProntoParaRecolha(nif);

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    /** Ler uma opção válida */

    private int readOptionInt(int opcoes) {
        int op = -1;

        while(op == -1){

            System.out.print(ANSI_YELLOW + "Opção: " + ANSI_RESET);
            try {
                String line = this.scin.nextLine();
                op = Integer.parseInt(line);
            }
            catch (NumberFormatException e) { // Não foi inscrito um int
                op = -1;
            }
            if (op<0 || op> opcoes) {
                System.out.println(ANSI_RED + "Opção Inválida!!!" + ANSI_RESET);
                op = -1;
            }
        }

        return op;

    }


    private float readOptionFloat(float opcoes) {
        float op = -1;

        while(op == -1){

            System.out.print(ANSI_YELLOW + "Opção: " + ANSI_RESET);
            try {
                String line = this.scin.nextLine();
                op = Float.parseFloat(line);
            }
            catch (NumberFormatException e) { // Não foi inscrito um int
                op = -1;
            }
            if (op<0 || op> opcoes) {
                System.out.println(ANSI_RED + "Opção Inválida!!!" + ANSI_RESET);
                op = -1;
            }
        }


        return op;

    }


    public  String parseMinutosToDisplay(long minutos){

            long horas = minutos/60;

            long min = minutos - horas*60;

            String res = "Horas: " + horas + " Minutos: " + min;

            return res;
    }


}