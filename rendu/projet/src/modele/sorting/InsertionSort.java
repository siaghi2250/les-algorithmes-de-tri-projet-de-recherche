package modele.sorting;

import modele.SortingModel;

/**
 * Tri par insertion.
 *
 * Pour chaque élément, le décale vers la gauche jusqu'à sa position correcte
 * dans la partie déjà triée, comme on insère une carte dans une main de jeu.
 *
 * Complexité : O(n) meilleur cas (tableau trié), O(n²) cas moyen et pire cas.
 * En place, stable.
 */
public class InsertionSort extends AbstractSort {
    
    /**
     * Construit un tri par insertion.
     *
     * @param model modèle de visualisation
     */
    public InsertionSort(SortingModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "InsertionSort";
    }
    /**
     * Implémente le tri par insertion.
     *
     * @param array tableau à trier
     */
    @Override
    protected void sortImpl(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int valueToInsert = read(array, i);
            int j = i - 1;

            while (j >= 0) {
                setCompareIndices(j + 1, j);
                if (!isLess(valueToInsert, read(array, j))) break;
                write(array, j + 1, read(array, j)); // décale vers la droite
                j--;
            }
            write(array, j + 1, valueToInsert);
        }
    }
}