package modele.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de base du pattern Observer pour les modèles.
 * Gère la liste des écouteurs et l'envoi des notifications.
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

    private final List<EcouteurModele> ecouteurs = new ArrayList<>();

    @Override
    public void ajouterEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    @Override
    public void retirerEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    /** Notifie tous les écouteurs enregistrés d'un changement d'état. */
    protected void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.modeleMiseAJour(this);
        }
    }
}