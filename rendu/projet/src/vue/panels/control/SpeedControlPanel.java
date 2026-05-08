package vue.panels.control;

import javax.swing.*;
import java.awt.BorderLayout;
import controleur.SortingController;
import modele.SortingModel;

/**
 * Panneau de réglage de la vitesse de visualisation (10 ms à 500 ms par opération).
 * Le contrôleur est notifié à chaque déplacement (pas seulement au relâchement)
 * car modifier la vitesse pendant le tri est une opération légère.
 */
public class SpeedControlPanel extends JPanel {


    /**
     * Construit le panneau avec le slider et le label associés au modèle et au contrôleur.
     *
     * @param model      le modèle central contenant l'état et les métriques
     * @param controller le contrôleur gérant les interactions utilisateur et le tri
     */
    public SpeedControlPanel(SortingModel model, SortingController controller) {
        setLayout(new BorderLayout(5, 5));

        JLabel  label  = new JLabel("Vitesse : " + model.getVisualizationSpeed() + " ms");
        JSlider slider = new JSlider(10, 500, model.getVisualizationSpeed());

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            label.setText("Vitesse : " + value + " ms");
            controller.setVisualizationSpeed(value);
        });

        add(label,  BorderLayout.WEST);
        add(slider, BorderLayout.CENTER);
    }
}