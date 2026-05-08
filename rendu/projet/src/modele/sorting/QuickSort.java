package modele.sorting;

import java.util.Random;
import modele.SortingModel;

/**
 * Tri rapide (Quick Sort) avec pivot aléatoire.
 *
 * Partitionne récursivement le tableau autour d'un pivot choisi aléatoirement,
 * ce qui réduit le risque d'atteindre le pire cas O(n²).
 *
 * Complexité : O(n log n) en moyenne, O(n²) pire cas (rare avec pivot aléatoire).
 * En place, non stable. Espace auxiliaire O(log n) pour la pile d'appels.
 */
public class QuickSort extends AbstractSort {

    /** Générateur de nombres aléatoires pour le choix du pivot. */
    private final Random random = new Random();

     /**
     * Construit un tri rapide.
     *
     * @param model modèle de visualisation
     */
    public QuickSort(SortingModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "QuickSort";
    }

    /**
     * Lance le tri rapide.
     *
     * @param array tableau à trier
     */
    @Override
    public void sortImpl(int[] array) {
        quicksort(array, 0, array.length - 1);
    }

    /** Trie récursivement le sous-tableau array[low..high]. 
     *
     * @param array tableau
     * @param low index de début
     * @param high index de fin
     */
    private void quicksort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quicksort(array, low, pivotIndex - 1);
            quicksort(array, pivotIndex + 1, high);
        }
    }

    /**
     * Partitionne array[low..high] autour d'un pivot aléatoire.
     * @param array tableau
     * @param low index de début
     * @param high index de fin
     * @return l'indice définitif du pivot après partitionnement.
     */
    private int partition(int[] array, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        swap(array, pivotIndex, high);
        int pivot = read(array, high);

        int insertionPoint = low - 1;
        for (int j = low; j < high; j++) {
            setCompareIndices(j, high);
            if (isLessOrEqual(read(array, j), pivot)) {
                insertionPoint++;
                swap(array, insertionPoint, j);
            }
        }
        swap(array, insertionPoint + 1, high);
        return insertionPoint + 1;
    }

    /**
     * Retourne true si a est inférieur ou égale à b. Même comportement que isLess mais inclut l'égalité.
     * Nécessaire pour le partitionnement du Quick Sort.
     * @param a première valeur
     * @param b seconde valeur
     * @return true si a est inférieur ou égal à b, false sinon
     */
    protected boolean isLessOrEqual(int a, int b) {
        if (Thread.currentThread().isInterrupted()) return false;
        updateTime();
        this.nbrComparisons++;
        if (lastCompareIndex1 != -1 && lastCompareIndex2 != -1) {
            model.updateVisualization(lastCompareIndex1, lastCompareIndex2, "compare");
        }
        return a <= b;
    }
}