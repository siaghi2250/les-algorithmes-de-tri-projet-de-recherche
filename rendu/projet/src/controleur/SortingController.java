package controleur;

import modele.SortingModel;
import modele.sorting.*;
import javax.swing.SwingWorker;

/**
 * Contrôleur principal (pattern MVC) faisant le lien entre la vue et le modèle.
 *
 * Orchestre l'exécution des algorithmes dans un thread d'arrière-plan via SwingWorker.
 * Conserve une référence au worker courant pour pouvoir l'annuler proprement
 * lors d'un reset ou d'une nouvelle génération de tableau.
 */
public class SortingController {

    private final SortingModel model;
    private AbstractSort[] algorithms;
    private SwingWorker<Void, Void> currentWorker;

    /** Construit le contrôleur, initialise les algorithmes et sélectionne le premier.
     * 
     * @param model le modèle pour visualiser le tri 
     */
    public SortingController(SortingModel model) {
        this.model = model;
        initializeAlgorithms();
        selectAlgorithm(0);
    }
    
    /** Initialise la liste des algorithmes de tri disponibles. */
    private void initializeAlgorithms() {
        algorithms = new AbstractSort[]{
            new BubbleSort(model),
            new InsertionSort(model),
            new QuickSort(model),
            new MergeSort(model),
            new CountingSort(model)
        };
    }

    /**
     * Arrête proprement le tri en cours : libère la pause, marque le tri comme terminé
     * et interrompt le thread de tri via cancel(true).
     * Doit être appelé avant tout reset ou nouvelle génération.
     */
    private void stopCurrentSort() {
        model.setPaused(false);     // libère la boucle de pause
        model.setIsSorting(false);  // empêche de nouveaux sleeps
        if (currentWorker != null) {
            currentWorker.cancel(true); // interrompt le thread
            currentWorker = null;
        }
    }

    /** Génère un nouveau tableau (stoppe le tri en cours si nécessaire). */
    public void generateNewArray() {
        stopCurrentSort();
        model.generateNewArray();
    }

    /** Remet le tableau à son état d'origine (stoppe le tri en cours si nécessaire). */
    public void resetArray() {
        stopCurrentSort();
        model.resetArray();
    }

   
    /**
     * Sélectionne l'algorithme de tri par son indice dans la liste des algorithmes.
     * @param index indice de l'algorithme à sélectionner
     */
    public void selectAlgorithm(int index) {
        if (index >= 0 && index < algorithms.length) {
            model.setCurrentSort(algorithms[index]);
        }
    }

    /** @param size nouvelle taille du tableau */
    public void setArraySize(int size) { model.setArraySize(size); }

    /** @param pct nouveau pourcentage de désordre */
    public void setDisorderPercentage(int pct) { model.setDisorderPercentage(pct); }

    /** @param type nouveau type/mode de désordre */
    public void setDisorderType(int type) { model.setDisorderType(type); }

    /** @param speed nouvelle vitesse de visualisation en ms */
    public void setVisualizationSpeed(int speed) { model.setVisualizationSpeed(speed); }


    /** Inverse l'état de pause du tri en cours. */
    public void togglePause() {
        model.setPaused(!model.isPaused());
    }

    /**
     * Lance l'algorithme sélectionné dans un thread d'arrière-plan.
     * Tout tri précédent est stoppé avant le lancement.
     * @param onSortFinished callback exécuté sur l'EDT à la fin naturelle du tri (null si pas nécessaire)
     */
    public void startSorting(Runnable onSortFinished) {
        AbstractSort sort = (AbstractSort) model.getCurrentSort();
        if (sort == null) {
            System.err.println("Aucun algorithme sélectionné.");
            return;
        }

        stopCurrentSort();

        currentWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                model.setIsSorting(true);
                sort.sort(model.getCurrentArray());
                return null;
            }

            @Override
            protected void done() {
                model.setIsSorting(false);
                model.clearHighlights();
                if (!isCancelled() && onSortFinished != null) {
                    onSortFinished.run();  
                }
            }
        };
        currentWorker.execute();
    }

    /** Lance le tri sans callback de fin. */
    public void startSorting() {
        startSorting(null);
    }
}