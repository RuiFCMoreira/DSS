import LRController.GestFuncionarioBalcao.GestFuncionarioBalcao;
import LRController.GestGestor.GestGestor;
import LRController.GestTecnico.GestTecnico;
import LRModel.LojaReparacoesModel;
import LRView.UserInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main {
        public static void main(String[] args)  {

            LojaReparacoesModel model = new LojaReparacoesModel();
            GestGestor gestGestor = new GestGestor(model);
            GestFuncionarioBalcao gestFuncionarioBalcao = new GestFuncionarioBalcao(model);
            GestTecnico gestTecnico = new GestTecnico(model);

            UserInterface view = new UserInterface(gestFuncionarioBalcao,gestGestor,gestTecnico);
            view.run();

        }


    }


