package modele.sorting;

/**
 * Contrat commun à tous les algorithmes de tri.
 * Chaque implémentation doit trier un tableau en place et exposer
 * les métriques collectées lors de la dernière exécution.
 */
public interface Sort {

   /**
     * Trie le tableau en place.
     *
     * @param array tableau à trier
     */
    void sort(int[] array);

    /**
     * Retourne le nom de l'algorithme.
     *
     * @return nom de l'algorithme
     */
    String getName();

    /**
     * Retourne le nombre de comparaisons effectuées.
     *
     * @return nombre de comparaisons
     */
    long getNbrComparisons();

    /**
     * Retourne le nombre d'accès mémoire effectués.
     *
     * @return nombre d'accès mémoire
     */
    long getNbrAccesses();

    /**
     * Retourne le nombre d'échanges effectués.
     *
     * @return nombre d'échanges
     */
    long getNbrSwaps();

    /**
     * Retourne la durée du tri en nanosecondes.
     *
     * @return durée en nanosecondes
     */
    long getTimeNano();
}