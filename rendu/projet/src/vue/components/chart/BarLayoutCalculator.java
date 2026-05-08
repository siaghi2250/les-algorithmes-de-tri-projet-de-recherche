package vue.components.chart;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Calcule la position et les dimensions de chaque barre du graphique.
 * Gère automatiquement le cas où le nombre de barres est trop grand pour
 * qu'un espacement soit possible (les barres sont alors jointives, 1 px de large).
 */
public class BarLayoutCalculator {

    private static final int MARGIN  = 40;
    private static final int SPACING = 2;

    /**
     * Retourne le rectangle représentant la barre à l'indice donné.
     * La largeur s'adapte au nombre de barres et la hauteur est proportionnelle à value / max.
     *
     * @param index indice de la barre dans le tableau
     * @param value valeur de l'élément
     * @param max valeur maximale du tableau
     * @param size dimension totale du composant
     * @param count nombre total de barres
     * @return rectangle (x, y, largeur, hauteur) de la barre
     */
    public Rectangle compute(int index, int value, int max, Dimension size, int count) {
        int availableWidth  = size.width  - 2 * MARGIN;
        int availableHeight = size.height - 2 * MARGIN;

        // Largeur et espacement : si les barres ne tiennent pas avec SPACING, on supprime l'espacement
        int barWidth = (availableWidth - (count - 1) * SPACING) / count;
        int spacing;
        if (barWidth < 1) {
            barWidth = Math.max(1, availableWidth / count);
            spacing  = 0;
        } else {
            spacing = SPACING;
        }

        int barHeight = (max == 0) ? 0 : (int) ((double) value / max * availableHeight);
        int x = MARGIN + index * (barWidth + spacing);
        int y = size.height - MARGIN - barHeight;

        return new Rectangle(x, y, barWidth, barHeight);
    }
}