package vue.panels.control;

import javax.swing.*;
import java.awt.GridLayout;
import controleur.SortingController;
import modele.SortingModel;

/**
 * Panneau de configuration du tableau de tri.
 *
 * <p>Permet de régler :
 * <ul>
 *   <li>la taille du tableau (curseur 10-300)</li>
 *   <li>le pourcentage de désordre (curseur 0-100%)</li>
 * </ul>
 * 
 * <p>Les labels se mettent à jour en temps réel lors du glissement du curseur.
 * Le {@link SortingController} n'est notifié qu'au relâchement du curseur
 * (évite des régénérations trop fréquentes du tableau).
 */
public class ArraySettingsPanel extends JPanel {

    /**
     * Construit le panneau et associe les sliders au modèle et au contrôleur.
     *
     * @param model      modèle contenant l'état du tableau
     * @param controller contrôleur qui sera notifié des changements après relâchement
     */
    public ArraySettingsPanel(SortingModel model, SortingController controller) {
        setLayout(new GridLayout(2, 2, 5, 5));

        JLabel sizeLabel     = new JLabel("Taille : " + model.getArraySize());
        JSlider sizeSlider   = new JSlider(10, 300, model.getArraySize());

        JLabel disorderLabel  = new JLabel("Désordre : " + model.getDisorderPercentage() + " %");
        JSlider disorderSlider = new JSlider(0, 100, model.getDisorderPercentage());

        sizeSlider.addChangeListener(e -> {
            int value = sizeSlider.getValue();
            sizeLabel.setText("Taille : " + value);
            if (!sizeSlider.getValueIsAdjusting()) {
                controller.setArraySize(value);
            }
        });

        disorderSlider.addChangeListener(e -> {
            int value = disorderSlider.getValue();
            disorderLabel.setText("Désordre : " + value + " %");
            if (!disorderSlider.getValueIsAdjusting()) {
                controller.setDisorderPercentage(value);
            }
        });

        add(sizeLabel);
        add(sizeSlider);
        add(disorderLabel);
        add(disorderSlider);
    }
}