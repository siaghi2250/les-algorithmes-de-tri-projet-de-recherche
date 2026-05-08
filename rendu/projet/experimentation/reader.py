import csv
import numpy as np
import matplotlib.pyplot as plt
from collections import defaultdict
import os

# CONFIGURATION

algorithms = [
    "QuickSort",
    "MergeSort",
    "InsertionSort",
    "BubbleSort",
    "CountingSort"
]

algorithms_filtered = [
    "QuickSort",
    "MergeSort",
    "CountingSort"
]

sizes_to_plot = [10000, 50000, 100000]

base_path = "resultats/Experimentation_2/algos"
output_dir = "resultats/Experimentation_2/analyse_desordre_zoom"

os.makedirs(output_dir, exist_ok=True)

# LECTURE DES DONNÉES

data = defaultdict(lambda: {
    "access": [],
    "compare": [],
    "swap": [],
    "time": []
})

for algo in algorithms:

    filename = f"{base_path}/{algo}.csv"

    with open(filename) as f:
        reader = csv.reader(f, delimiter=';')
        next(reader)

        for row in reader:

            size = int(row[0])
            disorder = int(row[1])
            dtype = row[2]
            algo_name = row[3]

            access = int(row[4])
            compare = int(row[5])
            swap = int(row[6])
            time = int(row[7])

            key = (algo_name, size, disorder, dtype)

            data[key]["access"].append(access)
            data[key]["compare"].append(compare)
            data[key]["swap"].append(swap)
            data[key]["time"].append(time)

# MOYENNES

mean_data = {}

for key, metrics in data.items():

    mean_data[key] = {
        "access": np.mean(metrics["access"]),
        "compare": np.mean(metrics["compare"]),
        "swap": np.mean(metrics["swap"]),
        "time": np.mean(metrics["time"])
    }

disorders = sorted(set(k[2] for k in mean_data.keys()))
dtypes = sorted(set(k[3] for k in mean_data.keys()))

metrics_list = ["access", "compare", "swap", "time"]

# COMPARAISON ALGOS vs DÉSORDRE

for size in sizes_to_plot:
    for dtype in dtypes:
        for metric in metrics_list:

            plt.figure(figsize=(10,6))

            for algo in algorithms:

                x = []
                y = []

                for disorder in disorders:

                    key = (algo, size, disorder, dtype)

                    if key not in mean_data:
                        continue

                    x.append(disorder)
                    y.append(mean_data[key][metric])

                if x:
                    plt.plot(x, y, marker='o', label=algo)

            plt.xlabel("Désordre (%)")
            plt.ylabel(metric)
            plt.title(f"{metric} vs désordre — n={size} — type={dtype}")
            plt.legend()
            plt.grid(True)

            if metric == "time":
                plt.yscale("log")

            plt.tight_layout()

            filename = f"{output_dir}/algos_{metric}_size{size}_{dtype}.png"
            plt.savefig(filename, dpi=200)
            plt.close()

            print("Sauvegardé :", filename)

# INFLUENCE DU TYPE DE DÉSORDRE

for algo in algorithms:
    for size in sizes_to_plot:
        for metric in metrics_list:

            plt.figure(figsize=(10,6))

            for dtype in dtypes:

                x = []
                y = []

                for disorder in disorders:

                    key = (algo, size, disorder, dtype)

                    if key not in mean_data:
                        continue

                    x.append(disorder)
                    y.append(mean_data[key][metric])

                if x:
                    plt.plot(x, y, marker='o', label=dtype)

            plt.xlabel("Désordre (%)")
            plt.ylabel(metric)
            plt.title(f"{algo} — {metric} vs désordre — n={size}")
            plt.legend()
            plt.grid(True)

            if metric == "time":
                plt.yscale("log")

            plt.tight_layout()

            filename = f"{output_dir}/{algo}_{metric}_size{size}.png"
            plt.savefig(filename, dpi=200)
            plt.close()

            print("Sauvegardé :", filename)

# COURBES SANS BUBBLESORT ET INSERTIONSORT
"""
for size in sizes_to_plot:
    for dtype in dtypes:
        for metric in metrics_list:

            plt.figure(figsize=(10,6))

            for algo in algorithms_filtered:

                x = []
                y = []

                for disorder in disorders:

                    key = (algo, size, disorder, dtype)

                    if key not in mean_data:
                        continue

                    x.append(disorder)
                    y.append(mean_data[key][metric])

                if x:
                    plt.plot(x, y, marker='o', label=algo)

            plt.xlabel("Désordre (%)")
            plt.ylabel(metric)
            plt.title(f"{metric} vs désordre — n={size} — type={dtype}")
            plt.legend(loc="upper left")
            plt.grid(True)

            if metric == "time":
                plt.yscale("log")

            plt.tight_layout()

            filename = f"{output_dir}/algos_filtered_{metric}_size{size}_{dtype}.png"
            plt.savefig(filename, dpi=200)
            plt.close()

            print("Sauvegardé :", filename)
"""