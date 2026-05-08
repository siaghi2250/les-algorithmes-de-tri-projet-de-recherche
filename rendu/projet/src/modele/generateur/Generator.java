package modele.generateur;

import java.util.*;
import java.util.Collections;
import java.util.Random;

/**
 * Générateur de tableaux d'entiers partiellement désordonnés.
 *
 * Le tableau est initialement trié [0, 1, 2, ..., n-1], puis un pourcentage
 * de ses éléments est mélangé selon quatre modes de désordre :
 *   - Mode 1 : indices choisis aléatoirement dans tout le tableau
 *   - Mode 2 : désordre appliqué au début du tableau
 *   - Mode 3 : désordre appliqué au milieu du tableau
 *   - Mode 4 : désordre appliqué à la fin du tableau
 */
public class Generator {

    /** Taille du tableau à générer. */
    private int taille;

    /** Tableau d'entiers généré. */
    private int[] tab;

    /** Pourcentage d'éléments à mélanger (entre 0 et 100 inclus). */
    private int pourcentageAleatoire;

    /** Mode de désordre appliqué (1 = aléatoire, 2 = début, 3 = milieu, 4 = fin). */
    private int desordre;

    /**
     * Construit un générateur configuré avec une taille et un pourcentage de désordre.
     *
     * @param taille               nombre d'éléments du tableau
     * @param pourcentageAleatoire pourcentage d'éléments à mélanger (entre 0 et 100)
     */
    public Generator(int taille, int pourcentageAleatoire) {
        this.taille = taille;
        this.pourcentageAleatoire = pourcentageAleatoire;
    }

    /**
     * Calcule le nombre d'éléments à mélanger selon la taille et le pourcentage.
     *
     * Formule : résultat = taille * pourcentage / 100
     *
     * @param taille               taille totale du tableau
     * @param pourcentageAleatoire pourcentage souhaité (doit être compris entre 0 et 100)
     * @return nombre d'éléments à mélanger
     * @throws IllegalArgumentException si pourcentageAleatoire est hors de [0, 100]
     */
    public static int getNombreAleatoire(int taille, int pourcentageAleatoire) {
        if (pourcentageAleatoire > 100 || pourcentageAleatoire < 0) {
            throw new IllegalArgumentException("illegal pourcentage");
        }
        int res = taille * pourcentageAleatoire / 100;
        return res;
    }

    /**
     * Génère un tableau d'entiers [0, 1, ..., taille-1] partiellement mélangé.
     *
     * Le tableau est d'abord rempli de façon croissante, puis un sous-ensemble
     * d'éléments est mélangé selon le mode desordre choisi :
     *   - 1 : indices choisis aléatoirement dans tout le tableau
     *   - 2 : les nombreAleatoire premiers éléments sont mélangés
     *   - 3 : une zone centrale de nombreAleatoire éléments est mélangée
     *   - 4 : les nombreAleatoire derniers éléments sont mélangés
     *
     * @param taille               taille du tableau à générer
     * @param pourcentageAleatoire pourcentage d'éléments à mélanger (0-100)
     * @param desordre             mode de désordre (1, 2, 3 ou 4)
     * @return tableau d'entiers partiellement mélangé selon le mode demandé
     * @throws IllegalArgumentException si pourcentageAleatoire est hors de [0, 100]
     */
    public static int[] generateurTab(int taille, int pourcentageAleatoire, int desordre) {
        int nombreAleatoire = getNombreAleatoire(taille, pourcentageAleatoire);
        int[] tab = new int[taille];
        Random rand = new Random();

        // Initialisation : tableau trié [0, 1, 2, ..., taille-1]
        for (int i = 0; i < taille; i++) {
            tab[i] = i;
        }

        // Mode 1 : désordre à des positions aléatoires dans tout le tableau
        if (desordre == 1) {
            List<Integer> tabChangement = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();

            // Sélection de nombreAleatoire indices uniques au hasard
            for (int i = 0; i < nombreAleatoire; i++) {
                int aleatoire = rand.nextInt(taille);
                while (indices.contains(aleatoire)) {
                    aleatoire = rand.nextInt(taille);
                }
                indices.add(aleatoire);
                tabChangement.add(tab[aleatoire]);
            }

            // Mélange des valeurs collectées, puis réinsertion aux mêmes indices
            MelangeurShuffle(tabChangement);
            for (int i = 0; i < nombreAleatoire; i++) {
                tab[indices.get(i)] = tabChangement.get(i);
            }
        }

        // Mode 2 : désordre au début du tableau
        if (desordre == 2) {
            List<Integer> tabChangement = new ArrayList<>();

            // Collecte des nombreAleatoire premiers éléments
            for (int i = 0; i < nombreAleatoire; i++) {
                tabChangement.add(tab[i]);
            }

            // Mélange, puis réinsertion en début de tableau
            MelangeurShuffle(tabChangement);
            for (int i = 0; i < nombreAleatoire; i++) {
                tab[i] = tabChangement.get(i);
            }
        }

        // Mode 3 : désordre au milieu du tableau
        if (desordre == 3) {
            // Calcul de l'indice de début pour centrer la zone de mélange
            int debut = Math.max(0, (taille / 2) - (nombreAleatoire / 2));
            int j = 0;
            List<Integer> tabChangement = new ArrayList<>();

            // Collecte des éléments de la zone centrale
            for (int i = 0; i < nombreAleatoire; i++) {
                j = debut + i;
                tabChangement.add(tab[j]);
            }

            // Mélange, puis réinsertion dans la zone centrale
            MelangeurShuffle(tabChangement);
            for (int i = 0; i < nombreAleatoire; i++) {
                j = debut + i;
                tab[j] = tabChangement.get(i);
            }
        }

        // Mode 4 : désordre en fin de tableau
        if (desordre == 4) {
            int zonefin  = nombreAleatoire;
            int startfin = taille - zonefin; // indice de début de la zone de fin
            int j = 0;
            List<Integer> tabChangement = new ArrayList<>();

            // Collecte des nombreAleatoire derniers éléments
            for (int i = 0; i < nombreAleatoire; i++) {
                j = startfin + i;
                tabChangement.add(tab[j]);
            }

            // Mélange, puis réinsertion en fin de tableau
            MelangeurShuffle(tabChangement);
            for (int i = 0; i < nombreAleatoire; i++) {
                j = startfin + i;
                tab[j] = tabChangement.get(i);
            }
        }

        return tab;
    }

    /**
     * Mélange aléatoirement une liste d'entiers en place via Collections.shuffle.
     *
     * @param tab liste d'entiers à mélanger (modifiée directement)
     */
    public static void MelangeurShuffle(List tab) {
        Collections.shuffle(tab);
    }

    /**
     * Affiche les éléments d'un tableau d'entiers sur une seule ligne,
     * séparés par des espaces, suivis d'un saut de ligne.
     *
     * @param tab tableau d'entiers à afficher
     */
    private static void afficher(int[] tab) {
        for (int val : tab) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    /**
     * Point d'entrée principal — démontre les quatre modes de désordre
     * sur un tableau de taille 150 avec 50% d'éléments mélangés.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        System.out.println("désordre aleatoire (1) :");
        afficher(generateurTab(150, 50, 1));

        System.out.println("désordre début (2) :");
        afficher(generateurTab(150, 50, 2));

        System.out.println("désordre milieu (3) :");
        afficher(generateurTab(150, 50, 3));

        System.out.println("désordre fin (4) :");
        afficher(generateurTab(150, 50, 4));
    }
}