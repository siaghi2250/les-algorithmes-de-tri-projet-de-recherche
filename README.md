Analyse des algorithmes de tri
Conception Logicielle 3 — Université de Caen Normandie

Thomas Matthieu · Marie Vianney · Siaghi Massinissa · Tellier Basile


Présentation
Ce projet répond à une question scientifique : comment les paramètres d'un
tableau (taille, degré et zone de désordre) influencent-ils les performances
des algorithmes de tri ?
Il est structuré en quatre étapes : modélisation, génération, expérimentation
et analyse. Cinq algorithmes sont étudiés : Bubble Sort, Insertion Sort,
Quick Sort, Merge Sort et Counting Sort. Chaque algorithme est instrumenté
pour mesurer comparaisons, accès mémoire, échanges et temps d'exécution.

Structure
projet/
├── build.xml
├── src/                  — code source Java
├── experimentation/
│   ├── experimentation.sh
│   └── resultats/        — fichiers CSV par taille
└── dist/                 — généré par ant (Javadoc, JAR)

Prérequis

Java 17+
Apache Ant
Python3
## Interface graphique

L'application affiche le tableau sous forme de barres verticales mises à jour
en temps réel pendant le tri. Chaque couleur correspond à une opération :
orange (comparaison), vert (échange), bleu (lecture), cyan (écriture).
Les métriques (comparaisons, accès, échanges, temps) sont affichées en bas
et se mettent à jour à chaque opération.

Des contrôles permettent de démarrer, mettre en pause, réinitialiser le tri,
générer un nouveau tableau et régler la vitesse de visualisation.


Pour lancer l'interface graphique de la visualisation : 

tout d'abord il fait se placer dans le répertoire projet$ et faire :

ant run       # compile + javadoc + lance l'application
ant clean     # supprime build/ et dist/
ant compile   # compile uniquement
ant javadoc   # génère la doc dans dist/docs/api/

Expérimentation
Génération automatique de données de performance pour analyse comparative.
Lancement (se placer dans experimentation/) :

	Expérimentation complète
bash Lanceur.sh resultats/Mon_Experience expe

	Agrégation des résultats
bash Lanceur.sh resultats/Mon_Experience aggr

	Les deux à la suite
bash Lanceur.sh resultats/Mon_Experience tout

Les résultats sont exportés en CSV dans resultats/ pour analyse ultérieure.

