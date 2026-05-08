#!/bin/bash
#Lancer l'expérimentation
# bash Lanceur.sh <dossier> expe

# Lancer l'agrégation
# bash Lanceur.sh <dossier> aggr

# Lancer les deux
#  bash Lanceur.sh <dossier> tout
TAILLES=(100 500 1000 5000 10000 25000 50000 75000 100000)
dossier=$1
mode=$2

if [ -z "$dossier" ] || [ -z "$mode" ]; then
    echo "Usage : bash Lanceur.sh <dossier> <expe|aggr|tout>"
    exit 1
fi

if [ "$mode" = "expe" ] || [ "$mode" = "tout" ]; then
    cd ..
    ant compile
    cd experimentation/

    mkdir -p "$dossier"

    echo "Lancement des experimentations"
    for taille in "${TAILLES[@]}"; do
        bash experimentation.sh $taille $dossier || exit 1
    done
    echo "Toutes les expérimentations sont terminées"
fi

if [ "$mode" = "aggr" ] || [ "$mode" = "tout" ]; then
    echo "- - Debut de l'aggregation par algorithme - - -"
    bash Aggregation.sh "$dossier" "$dossier/algos"
    echo "- - Fin de l'aggregation par algorithme - - -"
fi
