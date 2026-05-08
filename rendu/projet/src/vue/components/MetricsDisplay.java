package vue.components;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import modele.SortingModel;
import modele.event.EcouteurModele;

/**
 * Panneau affichant les métriques du tri en temps réel : comparaisons, accès, échanges, temps.
 * S'abonne au modèle et se met à jour automatiquement à chaque notification.
 */
public class MetricsDisplay extends JPanel implements EcouteurModele {

    /** Modèle observé pour récupérer l'état du tri et les métriques. */
    private final SortingModel model;

    /** Affiche le nom de l'algorithme actuellement sélectionné. */
    private final JLabel algorithmNameLabel;

    /** Affiche le nombre de comparaisons effectuées. */
    private final JLabel comparisonsValue;

    /** Affiche le nombre d'accès aux éléments du tableau. */
    private final JLabel accessesValue;

    /** Affiche le nombre d'échanges effectués. */
    private final JLabel swapsValue;

    /** Affiche le temps écoulé du tri en millisecondes. */
    private final JLabel timeValue;

    /**
     * Construit le panneau et s'abonne au modèle.
     * @param model modèle de tri à observer
     */
    public MetricsDisplay(SortingModel model) {
        this.model = model;
        model.ajouterEcouteur(this);

        algorithmNameLabel = new JLabel("");
        algorithmNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        algorithmNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font valueFont = new Font("Arial", Font.BOLD, 24);
        comparisonsValue = createValueLabel(valueFont);
        accessesValue    = createValueLabel(valueFont);
        swapsValue       = createValueLabel(valueFont);
        timeValue        = createValueLabel(valueFont);

        setupLayout();
        refresh();
    }

    private JLabel createValueLabel(Font font) {
        JLabel label = new JLabel("0");
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new CompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Métriques en Temps Réel"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        add(algorithmNameLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1, 4, 10, 10));
        grid.setOpaque(false);
        grid.add(buildMetricCard("Comparaisons", comparisonsValue, new Color(255, 152, 0)));
        grid.add(buildMetricCard("Accès",        accessesValue,    new Color(33,  150, 243)));
        grid.add(buildMetricCard("Échanges",     swapsValue,       new Color(76,  175,  80)));
        grid.add(buildMetricCard("Temps",        timeValue,        new Color(156,  39, 176)));
        add(grid, BorderLayout.CENTER);
    }

    /** Construit une carte métrique avec bordure colorée, titre et valeur. */
    private JPanel buildMetricCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        valueLabel.setForeground(color);

        card.add(titleLabel,  BorderLayout.NORTH);
        card.add(valueLabel,  BorderLayout.CENTER);
        return card;
    }

    /** Met à jour tous les labels depuis le modèle. */
    private void refresh() {
        algorithmNameLabel.setText(
            model.getCurrentSort() != null
                ? model.getCurrentSort().getName()
                : "Aucun algorithme sélectionné"
        );

        comparisonsValue.setText(formatNumber(model.getCurrentComparisons()));
        accessesValue.setText(formatNumber(model.getCurrentAccesses()));
        swapsValue.setText(formatNumber(model.getCurrentSwaps()));

        double timeMs = model.getCurrentTime() / 1_000_000.0;
        timeValue.setText(String.format("%.2f ms", timeMs));
    }

    private String formatNumber(long number) {
        return String.format("%,d", number);
    }

    @Override
    public void modeleMiseAJour(Object source) {
        refresh();
    }
}