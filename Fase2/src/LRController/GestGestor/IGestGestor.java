package LRController.GestGestor;

import LRModel.*;

import java.util.*;

public interface IGestGestor {
    public boolean loginGestor(String username, String password);

    public Gestor getGestor(String id);

    public boolean existeGestor(String idGestor);

    public boolean adicionarGestor(String username, String password);

    public boolean adicionarFuncionarioBalcao(String username, String password);

    public boolean adicionarTecnico(String username, String password);

    public boolean removerGestor(String username);

    public boolean removerFuncionarioBalcao(String username);

    public boolean removerTecnico(String username);

    public List<String> getListagem1 ();

    public List<String> parseToDisplayListagem1 (Map<String, AbstractMap.SimpleEntry<Integer, Integer>> numReparacoesPorTecnico, Map<String, Double> duracaoMediaReparacoesProgramadas, Map<String, Float> desvio);

    public List<String> getListagem2();

    public List<String> getListagem3();


    public List<String> parseToDisplayListagem3 (Map<String, AbstractMap.SimpleEntry<List<List<Passo>>, List<String>>> listaIntervencoes);

    public List<String> getNomeGestores();

    public List<Gestor> getGestores();

    public void saveFiles();

}
