package vue.panels.control;

import javax.swing.*;
import controleur.SortingController;
import modele.SortingModel;

/**
 * Panneau de contrôle principal : agrège les quatre sous-panneaux
 * de configuration et d'exécution dans un layout vertical.
 *
 * Contenu (de haut en bas) :
 *   1. AlgorithmSelectionPanel — algorithme + type de désordre
 *   2. ArraySettingsPanel      — taille + pourcentage de désordre
 *   3. SpeedControlPanel       — vitesse de visualisation
 *   4. ExecutionControlPanel   — démarrer / pause / reset / nouveau
 *
 * Ce panneau ne contient aucune logique propre : il délègue tout aux sous-panneaux.
 */
public class ControlPanel extends JPanel {

     /**
     * Construit le panneau principal et initialise les quatre sous-panneaux.
     *
     * @param model      modèle central contenant le tableau et les métriques
     * @param controller contrôleur chargé de gérer les interactions utilisateur
     */
    public ControlPanel(SortingModel model, SortingController controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Contrôles"));

        add(new AlgorithmSelectionPanel(controller));
        add(new ArraySettingsPanel(model, controller));
        add(new SpeedControlPanel(model, controller));
        add(new ExecutionControlPanel(model, controller));
    }
}