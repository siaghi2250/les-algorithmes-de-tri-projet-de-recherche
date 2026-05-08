package vue.components.chart;

import java.awt.Color;

/**
 * Palette de couleurs partagée pour les opérations de tri.
 * Utilisée par BarColorResolver et OperationOverlayPainter pour garantir
 * la cohérence visuelle entre les barres et le texte affiché.
 */
public class OperationColors {
    /**constructeur vide (inutilisé) */
    private OperationColors() {}

    /**
     * Retourne la couleur correspondant à une opération de tri.
     *
     * @param operation nom de l'opération ("compare", "swap", "read", "write", "sorted")
     * @return couleur associée ou gris si opération inconnue ou nulle
     */
    public static Color resolve(String operation) {
        if (operation == null) return Color.GRAY;
        return switch (operation) {
            case "compare"        -> new Color(255, 152, 0);   // orange
            case "swap"           -> new Color(50,  200,  50); // vert
            case "read", "access" -> new Color(33,  150, 243); // bleu
            case "write"          -> new Color(0,   188, 212); // cyan
            case "sorted"         -> new Color(156,  39, 176); // violet
            default               -> Color.GRAY;
        };
    }
}