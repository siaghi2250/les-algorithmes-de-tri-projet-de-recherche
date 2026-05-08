package modele;

import modele.generateur.Generator;
import modele.sorting.*;
import java.util.*;
/**
 *
 * @author tellier212
 */
public class Experimentation {
    
    private static boolean isSorted(int[] tab){
        for(int i = 0; i < tab.length - 1; i++){
            if(tab[i] > tab[i + 1]){
                return false;
            } 
        }
        return true;
    }

    /**
     * Outil de benchmarking hors interface graphique.
     *
     * Lance chaque algorithme sur plusieurs tailles de tableau et affiche
     * les métriques (comparaisons, accès, échanges, temps) sur la sortie standard
     */
    public static void main(String[] args){
    //Arguments : Taille Desordre typeDesordre
    
        if(args.length > 3){
            System.out.println("Il y a trop d'arguments");
            
        }
        if(args.length < 3){
            System.out.println("Il manque des arguements");
        }

        int taille = Integer.parseInt(args[0]);
        int pourcentage = Integer.parseInt(args[1]);
        int typeDesordre = Integer.parseInt(args[2]);

        for(int i=0;i<100;i++){
            
            //Nouvelle graine aléatoire
            int[] tab = Generator.generateurTab(taille,pourcentage,typeDesordre);
        
            // Copie des tableaux
            int[] tabCopie1 = Arrays.copyOf(tab, tab.length);
            int[] tabCopie2 = Arrays.copyOf(tab, tab.length);
            int[] tabCopie3 = Arrays.copyOf(tab, tab.length);
            int[] tabCopie4 = Arrays.copyOf(tab, tab.length);
            int[] tabCopie5 = Arrays.copyOf(tab, tab.length);

            Sort s  = new CountingSort(null);
            Sort q  = new QuickSort(null);
            Sort in = new InsertionSort(null);
            Sort b  = new BubbleSort(null);
            Sort m  = new MergeSort(null);

            // Création des threads
            Thread t1 = new Thread(() -> s.sort(tabCopie1));
            Thread t2 = new Thread(() -> q.sort(tabCopie2));
            Thread t3 = new Thread(() -> in.sort(tabCopie3));
            Thread t4 = new Thread(() -> b.sort(tabCopie4));
            Thread t5 = new Thread(() -> m.sort(tabCopie5));

            // Démarrer les threads
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();

            // Attendre que tous les threads soient terminés
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                t5.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrompu : " + e.getMessage());
            }

            // Affichage une fois les tris finis
            if(isSorted(tabCopie1))
                System.out.println("[csv] "+s.getName()+";"+s.getNbrAccesses()+";"+s.getNbrComparisons()+";"+s.getNbrSwaps()+";"+s.getTimeNano());
            if(isSorted(tabCopie2))
                System.out.println("[csv] "+q.getName()+";"+q.getNbrAccesses()+";"+q.getNbrComparisons()+";"+q.getNbrSwaps()+";"+q.getTimeNano());
            if(isSorted(tabCopie3))
                System.out.println("[csv] "+in.getName()+";"+in.getNbrAccesses()+";"+in.getNbrComparisons()+";"+in.getNbrSwaps()+";"+in.getTimeNano());
            if(isSorted(tabCopie4))
                System.out.println("[csv] "+b.getName()+";"+b.getNbrAccesses()+";"+b.getNbrComparisons()+";"+b.getNbrSwaps()+";"+b.getTimeNano());
            if(isSorted(tabCopie5))
                System.out.println("[csv] "+m.getName()+";"+m.getNbrAccesses()+";"+m.getNbrComparisons()+";"+m.getNbrSwaps()+";"+m.getTimeNano());
        }
        
    }
}
