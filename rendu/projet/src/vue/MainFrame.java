package vue;

import javax.swing.*;
import java.awt.*;
import vue.panels.VisualizationPanel;
import controleur.SortingController;
import modele.SortingModel;

/**
 * Fenêtre principale de l'application de visualisation des algorithmes de tri.
 * Affiche le panneau de visualisation en temps réel.
 * Dimensions : 1200×800 px, minimum 1000×700 px, centrée à l'écran.
 */
public class MainFrame extends JFrame {

    /**panneau de visulisation principal */
    private final VisualizationPanel visualizationPanel;

    /**
     * Construit la fenêtre principale avec le panneau de visualisation.
     *
     * @param model      le modèle central contenant l'état du tableau et les métriques
     * @param controller le contrôleur gérant les interactions utilisateur et le tri
     */
    public MainFrame(SortingModel model, SortingController controller) {
        visualizationPanel = new VisualizationPanel(model, controller);

        setLayout(new BorderLayout());
        add(visualizationPanel, BorderLayout.CENTER);

        setTitle("Visualiseur d'Algorithmes de Tri");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
    }

     /**
     * Retourne le panneau de visualisation principal.
     *
     * @return le {@link VisualizationPanel} affiché dans la fenêtre
     */
    public VisualizationPanel getVisualizationPanel() {
        return visualizationPanel;
    }
}