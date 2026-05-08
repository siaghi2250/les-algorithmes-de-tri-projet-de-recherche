#!/bin/bash

#Parametres d'experimentation
DESORDRES=(0 10 20 30 40 50 60 70 80 90 100) # + de desordres
TYPES=(1 2 3 4)

#Modifier les params avec generateur V2
size=$1
sortie=$(mktemp)
echo "- - Debut experimentation taille : $1 - - -"
#Bouclage imbriqué pour le lancement de l'experimentation
	#Creation du fichier csv
	fichier="$2/size_${size}.csv"
	
	#creation du header
	if [ ! -f "$fichier" ]; then
        echo "taille;desordre;typeDesordre;algo;accesses;comparisons;swaps;timeNano" > "$fichier"
    fi
	
    for disorder in "${DESORDRES[@]}"; do
        for type in "${TYPES[@]}"; do

            echo "[run] taille=$size pourcentage=$disorder desordre=$type"
            
			java -cp ../build modele.Experimentation "$size" "$disorder" "$type" > "$sortie" || exit 1
         
            # Recupération sortie std java
            grep '^\[csv\]' "$sortie" | while read -r line; do
                # Apres la balise [csv]
                data=$(echo "$line" | cut -d ' ' -f 2-)
                
                # Ajouter taille, pourcentage et typeDesordre au fichier
                echo "${size};${disorder};${type};${data}" >> "$fichier"
            done
        done
    done
echo "- - Fin experimentation taille : $1 - - -"


