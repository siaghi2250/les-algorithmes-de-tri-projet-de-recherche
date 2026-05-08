package vue.panels;

import javax.swing.*;
import java.awt.BorderLayout;
import modele.SortingModel;
import modele.event.EcouteurModele;
import controleur.SortingController;
import vue.components.MetricsDisplay;
import vue.components.chart.ArrayBarChart;
import vue.panels.control.ControlPanel;

/**
 * Panneau principal de visualisation des algorithmes de tri.
 *
 * <p>Disposé en {@link BorderLayout} :
 * <ul>
 *   <li>NORTH  : {@link ControlPanel} — tous les contrôles</li>
 *   <li>CENTER : {@link ArrayBarChart} — graphique du tableau avec couleurs d'opérations</li>
 *   <li>SOUTH  : {@link MetricsDisplay} — métriques en temps réel (comparaisons, accès, échanges, temps)</li>
 * </ul>
 * Remarques utiles :
 * <ul>
 *   <li>Les sous-panneaux sont des {@link EcouteurModele} et se mettent à jour automatiquement (au changement du modèle).</li>
 *   <li>Ce panneau s'abonne également au modèle pour déclencher un {@code repaint()} global.</li>
 *   <li>Se limite à la composition et à la disposition des composants ; aucune logique métier n'est incluse.</li>
 * </ul>

 */
public class VisualizationPanel extends JPanel implements EcouteurModele {

    /** Modèle observé pour récupérer l'état du tableau et les métriques. */
    private final SortingModel model;

    /** Panneau regroupant tous les panneaux de contrôles (algorithme, désordre, taille, vitesse, exécution). */
    private final ControlPanel controlPanel;

    /** Graphique en barres représentant l'état actuel du tableau et les opérations en cours. */
    private final ArrayBarChart barChart;

    /** Affichage des métriques en temps réel : comparaisons, accès, échanges, temps. */
    private final MetricsDisplay metricsDisplay;

    /**
     * Construit le panneau principal de visualisation.
     *
     * @param model      le modèle {@link SortingModel} du tableau et des métriques
     * @param controller le contrôleur {@link SortingController} pour les interactions
     */
    public VisualizationPanel(SortingModel model, SortingController controller) {
        this.model = model;
        model.ajouterEcouteur(this);

        controlPanel   = new ControlPanel(model, controller);
        barChart       = new ArrayBarChart(model);
        metricsDisplay = new MetricsDisplay(model);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(controlPanel, BorderLayout.NORTH);

        JPanel chartWrapper = new JPanel(new BorderLayout());
        chartWrapper.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Visualisation du Tableau"
        ));
        chartWrapper.add(barChart, BorderLayout.CENTER);
        add(chartWrapper, BorderLayout.CENTER);

        add(metricsDisplay, BorderLayout.SOUTH);
    }

    @Override
    public void modeleMiseAJour(Object source) {
        repaint();
    }
}