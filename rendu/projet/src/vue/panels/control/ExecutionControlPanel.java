package vue.panels.control;

import javax.swing.*;
import java.awt.*;
import controleur.SortingController;
import modele.SortingModel;

/**
 * Panneau de contrôle de l'exécution du tri : Démarrer, Pause, Reset, Nouveau.
 *
 * La machine d'état des boutons suit ces transitions :
 *   - En repos     : Démarrer + Reset + Nouveau actifs, Pause inactif
 *   - En tri       : seul Pause est actif
 *   - En pause     : Pause (Reprendre) + Reset + Nouveau actifs
 *   - Fin naturelle: Reset + Nouveau actifs, Démarrer inactif (reset requis)
 */
public class ExecutionControlPanel extends JPanel {

     /**
     * Construit le panneau et initialise les quatre boutons avec leurs actions.
     *
     * @param model      le modèle central contenant l'état du tableau et les métriques
     * @param controller le contrôleur qui gère les actions utilisateur
     */
    public ExecutionControlPanel(SortingModel model, SortingController controller) {
        setLayout(new GridLayout(1, 4, 5, 5));

        JButton start    = new JButton("▶ Démarrer");
        JButton pause    = new JButton("⏸ Pause");
        JButton reset    = new JButton("⟲ Reset");
        JButton generate = new JButton("Nouveau");

        applyIdleState(start, pause, reset, generate);

        //Démarrer 
        start.addActionListener(e -> {
            applySortingState(start, pause, reset, generate);
            controller.startSorting(() -> {
                // Fin naturelle du tri : Reset et Nouveau disponibles, pas Démarrer
                applyFinishedState(start, pause, reset, generate);
            });
        });

        //Pause / Reprendre
        pause.addActionListener(e -> {
            controller.togglePause();
            if (model.isPaused()) {
                pause.setText("▶ Reprendre");
                reset.setEnabled(true);
                generate.setEnabled(true);
            } else {
                pause.setText("⏸ Pause");
                reset.setEnabled(false);
                generate.setEnabled(false);
            }
        });

        //Reset 
        reset.addActionListener(e -> {
            controller.resetArray();
            applyIdleState(start, pause, reset, generate);
        });

        //Nouveau tableau
        generate.addActionListener(e -> {
            controller.generateNewArray();
            applyIdleState(start, pause, reset, generate);
        });

        add(start);
        add(pause);
        add(reset);
        add(generate);
    }

    /** En repos : prêt à démarrer. */
    private void applyIdleState(JButton start, JButton pause, JButton reset, JButton generate) {
        start.setEnabled(true);
        pause.setEnabled(false);
        pause.setText("⏸ Pause");
        reset.setEnabled(true);
        generate.setEnabled(true);
    }

    /** En cours de tri : seul Pause est disponible. */
    private void applySortingState(JButton start, JButton pause, JButton reset, JButton generate) {
        start.setEnabled(false);
        pause.setEnabled(true);
        pause.setText("⏸ Pause");
        reset.setEnabled(false);
        generate.setEnabled(false);
    }

    /** Fin naturelle du tri : Reset et Nouveau disponibles, pas Démarrer. */
    private void applyFinishedState(JButton start, JButton pause, JButton reset, JButton generate) {
        start.setEnabled(false);
        pause.setEnabled(false);
        pause.setText("⏸ Pause");
        reset.setEnabled(true);
        generate.setEnabled(true);
    }
}