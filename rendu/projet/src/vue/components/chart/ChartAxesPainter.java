package vue.components.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Dessine les axes X et Y du graphique en barres.
 * Les marges correspondent à celles utilisées par BarLayoutCalculator.
 */
public class ChartAxesPainter {

    private static final int MARGIN = 40;

    /**
     * Dessine les axes X et Y.
     *
     * @param g   contexte graphique 2D
     * @param size dimension du composant
     * @param max valeur maximale du tableau (non utilisée ici mais pour cohérence)
     */
    public void draw(Graphics2D g, Dimension size, int max) {
        g.setColor(Color.DARK_GRAY);
        // Axe Y
        g.drawLine(MARGIN, MARGIN, MARGIN, size.height - MARGIN);
        // Axe X
        g.drawLine(MARGIN, size.height - MARGIN, size.width - MARGIN, size.height - MARGIN);
    }
}