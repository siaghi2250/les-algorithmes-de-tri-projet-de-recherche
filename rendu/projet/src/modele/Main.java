package modele;

import javax.swing.SwingUtilities;
import modele.SortingModel;
import controleur.SortingController;
import vue.MainFrame;

/** Point d'entrée de l'application de visualisation des algorithmes de tri. */
public class Main {

     /**
     * Point d'entrée de l'application.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingModel      model      = new SortingModel();
            SortingController controller = new SortingController(model);
            MainFrame         frame      = new MainFrame(model, controller);
            frame.setVisible(true);
        });
    }
}