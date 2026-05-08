package modele.sorting;

import modele.SortingModel;

/**
 * Tri fusion (Merge Sort).
 *
 * Divise récursivement le tableau en deux moitiés, les trie indépendamment,
 * puis les fusionne. Approche "diviser pour régner".
 *
 * Complexité : O(n log n) dans tous les cas.
 * Non en place (tableau auxiliaire O(n)), stable, parallélisable.
 */
public class MergeSort extends AbstractSort {

    /**
     * Construit un tri fusion.
     *
     * @param model modèle de visualisation
     */
    public MergeSort(SortingModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "MergeSort";
    }


    /**
     * Lance un tri fusion.
     *
     * @param array le tableau à trier
     */
    @Override
    protected void sortImpl(int[] array) {
        if (array == null || array.length <= 1) return;
        mergeSort(array, 0, array.length - 1);
    }

    /**
     * Trie récursivement array[left..right] par division puis fusion.
     *
     * @param array tableau
     * @param left index de début
     * @param right index de fin
     */
    private void mergeSort(int[] array, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    /**
     * Fusionne les deux sous-tableaux triés array[left..mid] et array[mid+1..right].
     * Utilise des tableaux temporaires pour la fusion.
     * 
     * @param array tableau
     * @param left début du premier sous-tableau
     * @param mid fin du premier sous-tableau
     * @param right fin du second sous-tableau
     */
    private void merge(int[] array, int left, int mid, int right) {
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        int[] leftCopy  = new int[leftSize];
        int[] rightCopy = new int[rightSize];

        for (int i = 0; i < leftSize;  i++) leftCopy[i]  = read(array, left + i);
        for (int j = 0; j < rightSize; j++) rightCopy[j] = read(array, mid + 1 + j);

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            setCompareIndices(left + i, mid + 1 + j);
            if (isLess(leftCopy[i], rightCopy[j]) || leftCopy[i] == rightCopy[j]) {
                write(array, k, leftCopy[i++]);
            } else {
                write(array, k, rightCopy[j++]);
            }
            k++;
        }

        while (i < leftSize)  write(array, k++, leftCopy[i++]);
        while (j < rightSize) write(array, k++, rightCopy[j++]);
    }
}