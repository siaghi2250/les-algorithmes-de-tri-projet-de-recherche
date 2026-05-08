package modele.sorting;

import modele.SortingModel;

/**
 * Tri à bulles avec optimisation d'arrêt anticipé.
 *
 * Parcourt le tableau en comparant les paires adjacentes et en les échangeant si nécessaire.
 * S'arrête dès qu'un passage complet se fait sans aucun échange.
 *
 * Complexité : O(n) meilleur cas (tableau trié), O(n²) cas moyen et pire cas.
 * En place, non stable (les échanges peuvent perturber l'ordre relatif des égaux).
 */
public class BubbleSort extends AbstractSort {


    /**
     * Construit un tri à bulles.
     *
     * @param model modèle de visualisation
     */
    public BubbleSort(SortingModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "BubbleSort";
    }

    /**
     * Implémente le tri à bulles.
     *
     * @param array tableau à trier
     */
    @Override
    public void sortImpl(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                setCompareIndices(j, j + 1);
                if (isLess(read(array, j + 1), read(array, j))) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break; // tableau déjà trié
        }
    }
}