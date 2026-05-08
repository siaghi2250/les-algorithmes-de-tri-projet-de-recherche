package modele;

import modele.event.AbstractModeleEcoutable;
import modele.sorting.Sort;
import modele.generateur.Generator;
import java.util.Arrays;

/**
 * Modèle principal de l'application de visualisation des algorithmes de tri.
 *
 * Centralise l'état du tableau, les paramètres de génération, le contrôle
 * de l'exécution (pause, arrêt, vitesse) et les métriques en temps réel.
 * Notifie les vues via le pattern Observer (AbstractModeleEcoutable).
 *
 * Valeurs par défaut : taille 50, désordre 50%, mode 1, vitesse 400 ms.
 */
public class SortingModel extends AbstractModeleEcoutable {

    /** Tableau de travail courant. */
    private int[] currentArray;

    /** Tableau original pour reset. */
    private int[] originalArray;

    /** Taille du tableau. */
    private int arraySize = 50;

    /** Pourcentage de désordre (0-100). */
    private int disorderPercentage = 50;

    /** Mode de désordre (1 à 4). */
    private int disorderMode = 1;

    /** Algorithme de tri courant. */
    private Sort currentSort;

    /** Indique si le tri est en cours. */
    private boolean isSorting;

    /** Indique si le tri est en pause. */
    private boolean isPaused;

    /** Vitesse de visualisation en millisecondes. */
    private int visualizationSpeed = 400;

    /** Indices mis en évidence lors de la visualisation. */
    private int highlightedIndex1 = -1;
    private int highlightedIndex2 = -1;

    /** Type d'opération en cours ("compare", "swap", etc.). */
    private String currentOperation = "";

    /** Métriques en temps réel. */
    private long currentComparisons;
    private long currentAccesses;
    private long currentSwaps;
    private long currentTime;

    /** Construit le modèle avec les valeurs par défaut et génère un premier tableau. */
    public SortingModel() {
        generateNewArray();
    }


    /**
     * Génère un nouveau tableau selon les paramètres courants.
     * L'original est conservé pour permettre un reset ultérieur.
     * Les métriques sont remises à zéro et les vues notifiées.
     */
    public void generateNewArray() {
        this.originalArray = this.originalArray = Generator.generateurTab(arraySize, disorderPercentage, disorderMode);
        this.currentArray  = Arrays.copyOf(originalArray, originalArray.length);
        resetMetrics();
        fireChangement();
    }

    /**
     * Remet le tableau de travail à l'état du tableau original.
     * Les métriques et les mises en évidence sont effacées, puis les vues notifiées.
     */
    public void resetArray() {
        this.currentArray = Arrays.copyOf(originalArray, originalArray.length);
        resetMetrics();
        clearHighlights();
        fireChangement();
    }

    /** Remet tous les compteurs de métriques à zéro. */
    private void resetMetrics() {
        this.currentComparisons = 0;
        this.currentAccesses    = 0;
        this.currentSwaps       = 0;
        this.currentTime        = 0;
    }

    /** Supprime les indices mis en évidence et réinitialise l'opération courante. */
    public void clearHighlights() {
        this.highlightedIndex1 = -1;
        this.highlightedIndex2 = -1;
        this.currentOperation  = "";
    }

    /**
     * Met à jour la visualisation pour une opération élémentaire du tri.
     *
     * Met à jour les indices mis en évidence et les métriques, notifie les vues,
     * puis applique la pause et le délai de vitesse.
     * Retourne immédiatement si le thread de tri a été interrompu (annulation).
     *
     * @param index1    premier indice impliqué dans l'opération
     * @param index2    second indice impliqué dans l'opération (ou -1)
     * @param operation type d'opération : "compare", "swap", "read", "write", "sorted"
     */
    public void updateVisualization(int index1, int index2, String operation) {
        if (Thread.currentThread().isInterrupted()) return;

        this.highlightedIndex1 = index1;
        this.highlightedIndex2 = index2;
        this.currentOperation  = operation;

        if (currentSort != null) {
            this.currentComparisons = currentSort.getNbrComparisons();
            this.currentAccesses    = currentSort.getNbrAccesses();
            this.currentSwaps       = currentSort.getNbrSwaps();
            this.currentTime        = currentSort.getTimeNano();
        }

        fireChangement();

        // Attente pendant la pause
        while (isPaused && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (Thread.currentThread().isInterrupted()) return;

        // Délai de visualisation
        if (isSorting && visualizationSpeed > 0) {
            try {
                Thread.sleep(visualizationSpeed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ── Getters & Setters 
    /** @return le tableau de travail courant */
    public int[] getCurrentArray() { return currentArray; }

    /** @return le tableau original */
    public int[] getOriginalArray() { return originalArray; }

    /** @return la taille du tableau */
    public int getArraySize() { return arraySize; }

    /** @return le pourcentage de désordre */
    public int getDisorderPercentage() { return disorderPercentage; }

    /** @return le mode de désordre */
    public int getDisorderMode() { return disorderMode; }

    /** @return la vitesse de visualisation en ms */
    public int getVisualizationSpeed() { return visualizationSpeed; }

    /** @return l'algorithme de tri courant */
    public Sort getCurrentSort() { return currentSort; }

    /** @return true si le tri est en cours */
    public boolean isSorting() { return isSorting; }

    /** @return true si le tri est en pause */
    public boolean isPaused() { return isPaused; }

    /** @return premier indice mis en évidence */
    public int getHighlightedIndex1() { return highlightedIndex1; }

    /** @return second indice mis en évidence */
    public int getHighlightedIndex2() { return highlightedIndex2; }

    /** @return opération courante ("compare", "swap", etc.) */
    public String getCurrentOperation() { return currentOperation; }

    /** @return nombre actuel de comparaisons */
    public long getCurrentComparisons() { return currentComparisons; }

    /** @return nombre actuel d'accès */
    public long getCurrentAccesses() { return currentAccesses; }

    /** @return nombre actuel d'échanges */
    public long getCurrentSwaps() { return currentSwaps; }

    /** @return temps écoulé actuel en nanosecondes */
    public long getCurrentTime() { return currentTime; }

    /** Définit la taille du tableau et génère un nouveau tableau.
     * @param size nouvelle taille */
    public void setArraySize(int size) {
        this.arraySize = size;
        generateNewArray();
    }

    /** Définit le pourcentage de désordre et génère un nouveau tableau.
     * @param percentage nouveau pourcentage */
    public void setDisorderPercentage(int percentage) {
        this.disorderPercentage = percentage;
        generateNewArray();
    }

    /** Définit le mode de désordre et génère un nouveau tableau.
     * @param mode nouveau mode */
    public void setDisorderMode(int mode) {
        this.disorderMode = mode;
        generateNewArray();
    }

    /** Alias pour compatibilité avec les panneaux existants. */
    /** @return le mode de désordre */
    public int getDisorderType() { return disorderMode; }

    /** @param type nouveau mode de désordre */
    public void setDisorderType(int type) { setDisorderMode(type); }

    /** @param speed nouvelle vitesse de visualisation en ms */
    public void setVisualizationSpeed(int speed) { this.visualizationSpeed = speed; }

    /** @param sort nouvel algorithme de tri */
    public void setCurrentSort(Sort sort) { this.currentSort = sort; }

    /** @param sorting true pour indiquer que le tri est en cours */
    public void setIsSorting(boolean sorting) { this.isSorting = sorting; }

    /** @param paused true pour mettre le tri en pause */
    public void setPaused(boolean paused) { this.isPaused = paused; }

    /** @param time temps actuel en nanosecondes */
    public void setCurrentTime(long time) { this.currentTime = time; }
}