package modele.sorting;

import modele.SortingModel;

/**
 * Classe de base pour tous les algorithmes de tri.
 *
 * Fournit le suivi des métriques (comparaisons, accès, échanges, temps)
 * et les opérations instrumentées read/write/swap/isLess qui notifient
 * le modèle de visualisation à chaque étape.
 *
 * Toutes les opérations vérifient l'interruption du thread : si le tri
 * est annulé (ex. reset), les opérations deviennent des no-ops immédiats,
 * ce qui empêche toute modification du tableau après annulation.
 *
 * Les sous-classes implémentent sortImpl() et getName().
 */
public abstract class AbstractSort implements Sort {

    
    /** Nombre de comparaisons effectuées. */
    protected long nbrComparisons;

    /** Nombre d'accès mémoire effectués. */
    protected long nbrAccesses;

    /** Nombre d'échanges effectués. */
    protected long nbrSwaps;

    /** Durée du tri en nanosecondes. */
    protected long timeNano;

    /** Instant de début du tri. */
    protected long startTime;

    /** Index du premier élément comparé. */
    protected int lastCompareIndex1 = -1;

    /** Index du second élément comparé. */
    protected int lastCompareIndex2 = -1;

    /** Modèle de visualisation associé. */
    protected SortingModel model;

    /**
     * Construit un tri avec son modèle de visualisation.
     *
     * @param model modèle utilisé pour notifier les mises à jour
     */
    public AbstractSort(SortingModel model) {
        this.model = model;
    }

    @Override
    public final void sort(int[] array) {
        resetMetrics();
        this.startTime = System.nanoTime();
        sortImpl(array);
        this.timeNano = System.nanoTime() - this.startTime;
    }

    /**
     * Implémente la logique de tri.
     *
     * @param array tableau à trier
     */
    protected abstract void sortImpl(int[] array);

    /** Remet tous les compteurs à zéro. */
    protected void resetMetrics() {
        this.nbrComparisons = 0;
        this.nbrAccesses    = 0;
        this.nbrSwaps       = 0;
        this.timeNano       = 0;
    }

    /**
     * Lit une valeur dans le tableau.
     *
     * @param array tableau source
     * @param index position à lire
     * @return valeur lue ou 0 si interrompu
     */
    protected int read(int[] array, int index) {
        this.nbrAccesses++;
        if (model != null) {
            model.updateVisualization(index, -1, "read");
        }
        return array[index];
    }

    /**
     * Écrit une valeur dans le tableau.
     *
     * @param array tableau cible
     * @param index position d'écriture
     * @param value valeur à écrire
     */
    protected void write(int[] array, int index, int value) {
        this.nbrAccesses++;
        if (model != null) {
            model.updateVisualization(index, -1, "write");
        }
        array[index] = value;
    }

    /**
     * Compare deux valeurs.
     *
     * @param a première valeur
     * @param b seconde valeur
     * @return true si a est inférieur à b, false sinon ou si interrompu
     */
    protected boolean isLess(int a, int b) {
        this.nbrComparisons++;
        if (model != null) {
            model.updateVisualization(-1, -1, "compare");
        }
        return a < b;
    }

    /**
     * Échange deux éléments du tableau.
     *
     * @param array tableau
     * @param i premier index
     * @param j second index
     */
    protected void swap(int[] array, int i, int j) {
        this.nbrSwaps++;
        if (model != null) {
            model.updateVisualization(i, j, "swap");
        }
        int tmp = read(array, i);
        write(array, i, read(array, j));
        write(array, j, tmp);
    }

    /**
     * Définit les indices utilisés pour la prochaine comparaison.
     *
     * @param i premier index
     * @param j second index
     */
    protected void setCompareIndices(int i, int j) {
        if (model != null) {
            model.updateVisualization(i, j, "compare");
        }
    }

    /** Met à jour le temps écoulé. */
    protected void updateTime() {
        timeNano = System.nanoTime() - startTime;
    }

    /** Vérifie si le thread est interrompu. */
    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    @Override public abstract String getName();
    @Override public long getNbrComparisons() { return nbrComparisons; }
    @Override public long getNbrAccesses()    { return nbrAccesses; }
    @Override public long getNbrSwaps()       { return nbrSwaps; }
    @Override public long getTimeNano()       { return timeNano; }
}