package modele.sorting;

import modele.SortingModel;

/**
 * Tri par dénombrement (Counting Sort).
 *
 * Compte les occurrences de chaque valeur, puis reconstruit le tableau trié.
 * Non comparatif : efficace quand les valeurs sont des entiers dans un
 * intervalle [0, max] de taille raisonnable.
 *
 * Restriction : accepte uniquement des entiers positifs ou nuls.
 * Lève IllegalArgumentException si une valeur négative est détectée.
 *
 * Complexité : O(n + max). Espace auxiliaire : O(max). Stable, non en place.
 */
public class CountingSort extends AbstractSort {
    
     /**
     * Construit un tri par dénombrement.
     *
     * @param model modèle de visualisation
     */
    public CountingSort(SortingModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "CountingSort";
    }

    /**
     * Lance le tri par dénombrement.
     *
     * @param array tableau à trier
     */
    @Override
    protected void sortImpl(int[] array) {
        countingSort(array);
    }

    /**
     * Tri par dénombrement en trois étapes :
     *   1. Recherche du maximum (et validation des valeurs non négatives).
     *   2. Comptage des occurrences.
     *   3. Reconstruction du tableau trié.
     * @param array tableau à trier
     */
    private void countingSort(int[] array) {
        if (array.length <= 1) return;

        // Étape 1 : recherche du max et validation des valeurs
        int max = read(array, 0);
        for (int i = 1; i < array.length; i++) {
            int val = read(array, i);
            if (val < 0) {
                throw new IllegalArgumentException(
                    "CountingSort n'accepte que les entiers positifs ou nuls. Valeur trouvée : " + val
                );
            }
            if (val > max) max = val;
        }

        // Étape 2 : comptage des occurrences
        int[] count = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            count[read(array, i)]++;
        }

        // Étape 3 : reconstruction du tableau trié
        int writeIndex = 0;
        for (int value = 0; value <= max; value++) {
            for (int j = 0; j < count[value]; j++) {
                write(array, writeIndex++, value);
            }
        }
    }
}