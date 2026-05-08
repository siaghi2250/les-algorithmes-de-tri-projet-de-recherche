package modele.event;

/** Contrat d'un modèle capable de notifier des écouteurs. */
public interface ModeleEcoutable {
    /**
     * Ajoute un écouteur.
     * @param e écouteur à ajouter
     */
    void ajouterEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur.
     * @param e écouteur à retirer
     */
    void retirerEcouteur(EcouteurModele e);
}