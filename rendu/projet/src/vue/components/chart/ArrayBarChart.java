package vue.components.chart;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import modele.SortingModel;
import modele.event.EcouteurModele;

/**
 * Composant graphique affichant le tableau de tri sous forme de barres verticales.
 *
 * Chaque barre représente un élément du tableau, sa hauteur étant proportionnelle
 * à sa valeur. La couleur des barres actives reflète l'opération en cours (compare,
 * swap, read, write). Les axes et l'overlay de texte sont dessinés par-dessus.
 *
 * S'abonne au modèle et se redessine automatiquement à chaque notification.
 */
public class ArrayBarChart extends JPanel implements EcouteurModele {

    /** Modèle observé pour récupérer l'état du tableau et les indices mis en évidence. */
    private final SortingModel model;

    /** Utilitaire pour calculer la position et les dimensions de chaque barre. */
    private final BarLayoutCalculator layout = new BarLayoutCalculator();

    /** Utilitaire pour déterminer la couleur de chaque barre selon l'opération en cours. */
    private final BarColorResolver colors = new BarColorResolver();

    /** Utilitaire pour dessiner les axes X et Y du graphique. */
    private final ChartAxesPainter axes = new ChartAxesPainter();

    /** Utilitaire pour afficher le nom de l'opération courante en overlay. */
    private final OperationOverlayPainter overlay = new OperationOverlayPainter();

    /**
     * Construit le composant et s'abonne au modèle pour recevoir les notifications.
     * @param model modèle de tri à visualiser
     */
    public ArrayBarChart(SortingModel model) {
        this.model = model;
        model.ajouterEcouteur(this);
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
    }

    /**
     * Dessine le tableau sous forme de barres verticales, avec couleurs et axes.
     * @param g contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] array = model.getCurrentArray();
        if (array == null || array.length == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int max = java.util.Arrays.stream(array).max().orElse(1);

        for (int i = 0; i < array.length; i++) {
            Rectangle bar = layout.compute(i, array[i], max, getSize(), array.length);
            g2d.setColor(colors.resolve(i, model));
            g2d.fill(bar);
        }

        axes.draw(g2d, getSize(), max);
        overlay.draw(g2d, model, getSize());
    }

    /**
     * Notifié par le modèle à chaque changement d'état.
     * Redessine le composant.
     * @param source source de la notification (le modèle)
     */
    @Override
    public void modeleMiseAJour(Object source) {
        repaint();
    }
}