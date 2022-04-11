package LRModel;

import java.io.*;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class LojaReparacoesModel implements ILojaReparacoesModel {

    private Map<String, Gestor> gestores;
    private Map<String, FuncionarioBalcao> funcionariosDoBalcao;
    private Map<String, Tecnico> tecnicos;
    private Map<String, PedidoOrcamento> pedidos;
    private Map<String, PedidoExpresso> pedidosExpressos;
    private Map<String, Entrega> entregas;



    public LojaReparacoesModel() {

        this.gestores = new HashMap<>();
        this.funcionariosDoBalcao = new HashMap<>();
        this.tecnicos = new HashMap<>();
        this.pedidos = new HashMap<>();
        this.pedidosExpressos = new HashMap<>();
        this.entregas = new HashMap<>();


        try {

            File gestores = new File("saves/gestores.txt"); //alterar pasta possivelmente
            if (gestores.exists()) loadGestores("saves");
            else this.gestores = new HashMap<>();


            File funcionarios = new File("saves/funcionarios.txt"); //alterar pasta possivelmente
            if (funcionarios.exists()) loadFuncionarios("saves");
            else this.funcionariosDoBalcao = new HashMap<>();


            File tecnicos = new File("saves/tecnicos.txt"); //alterar pasta possivelmente
            if (tecnicos.exists()) loadTecnicos("saves");
            else this.tecnicos = new HashMap<>();


            File pedidos = new File("saves/pedidos.txt"); //alterar pasta possivelmente
            if (pedidos.exists()) loadPedidos("saves");
            else this.pedidos = new HashMap<>();


            File pedidosExpresso = new File("saves/pedidos-expresso.txt"); //alterar pasta possivelmente
            if (pedidosExpresso.exists()) loadPedidosExpresso("saves");
            else this.pedidosExpressos = new HashMap<>();


            File entregas = new File("saves/entregas.txt"); //alterar pasta possivelmente
            if (entregas.exists()) loadEntregas("saves");
            else this.entregas = new HashMap<>();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //gestores

    public boolean containsGestor(String username){
        return this.gestores.containsKey(username);
    }


    public Gestor getGestor(String username){
        return this.gestores.get(username);
    }

    public boolean adicionarGestor(Gestor g){

        if(this.gestores.containsKey(g.getUsername())) return false;

        this.gestores.put(g.getUsername(),g);

        return true;
    }

    public boolean removeGestor(String username){

        if(!this.gestores.containsKey(username)) return false;

        this.gestores.remove(username);

        return true;

    }

    public List<String> getNomeGestores() {
        return this.getListaDeGestores().stream().map(Gestor::getUsername).collect(Collectors.toList());
    }

    public List<Gestor> getListaDeGestores() {
        return this.gestores.values().stream().collect(Collectors.toList());
    }

    //funcionariosBalcao

    public boolean containsFuncionario(String username){
        return this.funcionariosDoBalcao.containsKey(username);
    }


    public FuncionarioBalcao getFuncionarioBalcao(String username){
        return this.funcionariosDoBalcao.get(username);
    }

    public List<String> getNomeFuncionarios() {
        return this.getListaDeFuncionarios().stream().map(FuncionarioBalcao::getUsername).collect(Collectors.toList());
    }

    public List<FuncionarioBalcao> getListaDeFuncionarios() {
        return this.funcionariosDoBalcao.values().stream().collect(Collectors.toList());
    }

    public boolean adicionarFuncionario(FuncionarioBalcao f){

        if(this.funcionariosDoBalcao.containsKey(f.getUsername())) return false;

        this.funcionariosDoBalcao.put(f.getUsername(),f);

        return true;
    }

    public boolean removeFuncionario(String username){

        if(!this.funcionariosDoBalcao.containsKey(username)) return false;

        this.funcionariosDoBalcao.remove(username);

        return true;

    }



    //tecnicos


    public boolean containsTecnico(String username){
        return this.tecnicos.containsKey(username);
    }

    public Tecnico getTecnico(String username){
        return this.tecnicos.get(username);
    }

    public List<String> getNomeTecnicos() {
        return this.getTecnicos().stream().map(Tecnico::getUsername).collect(Collectors.toList());
    }

    public List<Tecnico> getTecnicos(){
        return  this.tecnicos.values().stream().collect(Collectors.toList());
    }

    public boolean adicionarTecnico(Tecnico t){

        if(this.tecnicos.containsKey(t.getUsername())) return false;

        this.tecnicos.put(t.getUsername(),t);

        return true;
    }

    public boolean removeTecnico(String username){

        if(!this.tecnicos.containsKey(username)) return false;

        this.tecnicos.remove(username);

        return true;

    }

    //pedidos

    public boolean containsPedido(String id) { return this.pedidos.containsKey(id); }

    public PedidoOrcamento getPedidoOrcamento(String id) { return this.pedidos.get(id); }

    public List<PedidoOrcamento> getListaPedidosOrcamento() { // mais recente fica à cabeça
        List<PedidoOrcamento> pedidos = new ArrayList<>();
        for (PedidoOrcamento p : this.pedidos.values()) {
            if (p.getPlanoTrabalho().size() == 0)
                pedidos.add(p);
        }
        return pedidos.stream().sorted(Comparator.comparing(PedidoOrcamento::getDataPedido).reversed())
                .collect(Collectors.toList());
    }

    public List<PedidoOrcamento> getListaPedidosAceites() { // mais recente fica à cabeça
        List<PedidoOrcamento> pedidos = new ArrayList<>();
        for (PedidoOrcamento p : this.pedidos.values()) {
            if (p.isAprovado()) pedidos.add(p);
        }
        return pedidos.stream().sorted(Comparator.comparing(PedidoOrcamento::getDataPedido).reversed())
                .collect(Collectors.toList());
    }

    public List<PedidoOrcamento> getListaPedidosRealizados() { // mais recente fica à cabeça
        List<PedidoOrcamento> pedidos = new ArrayList<>();
        for (PedidoOrcamento p : this.pedidos.values()) {
            if (p.isConlusaoReparacao()) pedidos.add(p);
        }
        return pedidos.stream().sorted(Comparator.comparing(PedidoOrcamento::getDataPedido).reversed())
                .collect(Collectors.toList());
    }


    public void removePedido(String id){
        if(pedidos.containsKey(id))  this.pedidos.remove(id);

    }

    //pedidos-expresso

    public boolean containsPedidoExpresso(String nif) {
        return this.pedidosExpressos.containsKey(nif);
    }

    public PedidoExpresso getPedidoExpresso(String nif) {
        return this.pedidosExpressos.get(nif);
    }

    public List<PedidoExpresso> getListaPedidosExpresso() {
        List<PedidoExpresso> pedidosExpresso = new ArrayList<>();
        for(PedidoExpresso pe : this.pedidosExpressos.values()) {
            pedidosExpresso.add(pe);
        }
        return pedidosExpresso;
    }

    //Funções para persisntência dos dados

    public void loadGestores(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/gestores.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.gestores = (HashMap<String,Gestor>) ois.readObject();


        ois.close();
        fis.close();

    }

    public void loadFuncionarios(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/funcionarios.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.funcionariosDoBalcao = (HashMap<String,FuncionarioBalcao>) ois.readObject();


        ois.close();
        fis.close();

    }

    public void loadTecnicos(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/tecnicos.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.tecnicos = (HashMap<String,Tecnico>) ois.readObject();


        ois.close();
        fis.close();

    }

    public void loadPedidos(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/pedidos.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.pedidos = (HashMap<String,PedidoOrcamento>) ois.readObject();


        ois.close();
        fis.close();

    }

    public void loadPedidosExpresso(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/pedidos-expresso.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.pedidosExpressos = (HashMap<String,PedidoExpresso>) ois.readObject();


        ois.close();
        fis.close();

    }

    public void loadEntregas(String pasta) throws IOException, ClassNotFoundException {

        File toRead = new File(pasta + "/entregas.txt");
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);

        this.entregas = (HashMap<String,Entrega>) ois.readObject();


        ois.close();
        fis.close();

    }


    public void loadData(String pasta) throws IOException, ClassNotFoundException {

        loadTecnicos(pasta);
        loadFuncionarios(pasta);
        loadGestores(pasta);
        loadPedidos(pasta);
        loadPedidosExpresso(pasta);
        loadEntregas(pasta);
    }

    public void saveGestores(String pasta) throws IOException {

        File file = new File(pasta + "/gestores.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.gestores);
        oos.flush();
        oos.close();
        fos.close();


    }

    public void saveFuncionarios(String pasta) throws java.io.IOException {

        File file = new File(pasta + "/funcionarios.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.funcionariosDoBalcao);
        oos.flush();
        oos.close();
        fos.close();


    }

    public void saveTecnicos(String pasta) throws IOException {

        File file = new File(pasta + "/tecnicos.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.tecnicos);
        oos.flush();
        oos.close();
        fos.close();


    }


    public void savePedidos(String pasta) throws IOException {

        File file = new File(pasta + "/pedidos.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.pedidos);
        oos.flush();
        oos.close();
        fos.close();


    }

    public void savePedidosExpresso(String pasta) throws IOException {

        File file = new File(pasta + "/pedidos-expresso.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.pedidosExpressos);
        oos.flush();
        oos.close();
        fos.close();

    }

    public void saveEntregas(String pasta) throws IOException {

        File file = new File(pasta + "/entregas.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(this.entregas);
        oos.flush();
        oos.close();
        fos.close();

    }


    public void saveData(String pasta){


            File folder = new File(pasta);
            if(!folder.exists()) folder.mkdir();

        try {
            saveFuncionarios(pasta);
            saveGestores(pasta);
            saveTecnicos(pasta);
            savePedidos(pasta);
            savePedidosExpresso(pasta);
            saveEntregas(pasta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getEmailOrcamento(String nif){
        return this.pedidos.get(nif).getEmail();
    }

    public String getNomeOrcamento(String nif){
        return this.pedidos.get(nif).getNomeCliente();
    }

    public float getCustoTotalPrevisto(String nif){
        return this.pedidos.get(nif).getCustoTotalPrevisto();
    }

    public long getTempoPrevisto(String nif){
        return this.pedidos.get(nif).getTempoTotalPrevisto();
    }


    public void adicionaPedidoOrcamento(PedidoOrcamento po){
        this.pedidos.put(po.getId(),po);
    }

    public void adicionaPedidoExpresso(PedidoExpresso pe) {
        this.pedidosExpressos.put(pe.getNif(),pe);
    }

    public void adicionaEntregaPeloCliente(Entrega e) {
        this.entregas.put(e.getNif(),e);
    }

    public Entrega getEntrega(String nif) {
        return this.entregas.get(nif);
    }

    public List<Entrega> getListaEntregas(){
        return this.entregas.values().stream().collect(Collectors.toList());
    }

    public void removeEntrega(String nif){
        this.entregas.remove(nif);
    }
}
