package vue.panels.control;

import javax.swing.*;
import java.awt.GridLayout;
import controleur.SortingController;

/**
 * Panneau de sélection pour le tri et le type de désordre.
 * 
 * <p>Contient deux JComboBox : 
 * <ul>
 *   <li>sélection de l'algorithme de tri (5 choix)</li>
 *   <li>sélection du mode de désordre (4 choix)</li>
 * </ul>
 * Chaque modification fait appel immédiat au {@link SortingController}.
 */
public class AlgorithmSelectionPanel extends JPanel {

    private static final String[] ALGORITHM_NAMES = {
        "Bubble Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Counting Sort"
    };

    private static final String[] DISORDER_MODES = {
        "Aléatoire", "Début", "Milieu", "Fin"
    };

    /**
     * Construit le panneau et associe les JComboBox au contrôleur.
     *
     * @param controller contrôleur qui reçoit les événements de sélection
     */
    public AlgorithmSelectionPanel(SortingController controller) {
        setLayout(new GridLayout(2, 2, 5, 5));

        JComboBox<String> algorithmBox = new JComboBox<>(ALGORITHM_NAMES);
        JComboBox<String> disorderBox  = new JComboBox<>(DISORDER_MODES);

        algorithmBox.addActionListener(e ->
            controller.selectAlgorithm(algorithmBox.getSelectedIndex())
        );
        disorderBox.addActionListener(e ->
            controller.setDisorderType(disorderBox.getSelectedIndex() + 1)
        );

        add(new JLabel("Algorithme :"));
        add(algorithmBox);
        add(new JLabel("Type de désordre :"));
        add(disorderBox);
    }
}