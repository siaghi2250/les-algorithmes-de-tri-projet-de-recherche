package vue.components.chart;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import modele.SortingModel;

/**
 * Affiche le nom de l'opération en cours dans le coin supérieur droit du graphique.
 * La couleur du texte correspond à celle des barres mises en évidence,
 * garantissant la cohérence visuelle avec BarColorResolver.
 */
public class OperationOverlayPainter {

    private static final Font OVERLAY_FONT = new Font("Arial", Font.BOLD, 14);

     /**
     * Dessine l'opération courante en haut à droite.
     *
     * @param g     contexte graphique
     * @param model modèle contenant l'état et l'opération
     * @param size  dimensions du composant
     */
    public void draw(Graphics2D g, SortingModel model, Dimension size) {
        String op = model.getCurrentOperation();
        if (op == null || op.isEmpty()) return;

        g.setFont(OVERLAY_FONT);
        g.setColor(OperationColors.resolve(op));
        g.drawString(op.toUpperCase(), size.width - 150, 30);
    }
}