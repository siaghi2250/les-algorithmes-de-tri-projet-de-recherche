#!/bin/bash
# bash Aggregation.sh source destination
# Ex:     bash Aggregation.sh resultats/Test2 resultats/Test2/par_algo

ALGOS=("CountingSort" "QuickSort" "InsertionSort" "BubbleSort" "MergeSort")
SOURCE=$1
DEST=$2

if [ -z "$SOURCE" ] || [ -z "$DEST" ]; then
    echo "Usage : bash Aggregation.sh <dossier_source> <dossier_destination>"
    exit 1
fi

echo "Recherche des fichiers dans : $SOURCE"
ls "$SOURCE"/size_*.csv 2>/dev/null || { echo "Aucun fichier size_*.csv trouvé dans $SOURCE"; exit 1; }

mkdir -p "$DEST"

HEADER="taille;desordre;typeDesordre;algo;acces;comparaisons;echanges;temps"

for algo in "${ALGOS[@]}"; do
    fichier_algo="$DEST/${algo}.csv"

    echo "$HEADER" > "$fichier_algo"

    for size_file in "$SOURCE"/size_*.csv; do
        awk -F';' -v algo="$algo" 'NR>1 && $4==algo {print}' "$size_file" >> "$fichier_algo"
    done

    echo "Agrégation terminée : $fichier_algo ($(( $(wc -l < "$fichier_algo") - 1 )) lignes de données)"
done
