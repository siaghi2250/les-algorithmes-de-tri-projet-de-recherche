package modele.event;

/** Écouteur notifié à chaque changement d'état du modèle. */
public interface EcouteurModele {
    /**
     * Appelée lorsque le modèle est mis à jour.
     *
     * @param source objet à l'origine de la notification (non utilisé)
     */
    void modeleMiseAJour(Object source);
}