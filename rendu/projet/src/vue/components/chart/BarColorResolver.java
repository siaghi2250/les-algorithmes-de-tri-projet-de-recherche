package vue.components.chart;

import java.awt.Color;
import modele.SortingModel;

/**
 * Résout la couleur d'affichage de chaque barre selon son rôle dans l'opération courante.
 * Les barres actives (indices mis en évidence) prennent la couleur de l'opération ;
 * les autres sont affichées en gris clair.
 */
public class BarColorResolver {

    private static final Color INACTIVE_COLOR = new Color(180, 180, 190);

    /**
     * Retourne la couleur de la barre à l'indice donné selon l'état du modèle.
     * @param index indice de l'élément dans le tableau
     * @param model modèle de tri fournissant les indices mis en évidence et l'opération courante
     * @return couleur de la barre
     */
    public Color resolve(int index, SortingModel model) {
        if (index == model.getHighlightedIndex1() || index == model.getHighlightedIndex2()) {
            return OperationColors.resolve(model.getCurrentOperation());
        }
        return INACTIVE_COLOR;
    }
}