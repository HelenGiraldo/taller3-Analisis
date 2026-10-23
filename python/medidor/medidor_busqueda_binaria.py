
from pathlib import Path
from time import perf_counter
from statistics import mean, stdev

import sys

CARPETA_PROYECTO = Path(__file__).resolve().parents[2]
CARPETA_DATOS = CARPETA_PROYECTO / "datos" / "ordenamiento"
CARPETA_ALGORITMOS = Path(__file__).resolve().parents[1] / "algoritmo"

sys.path.insert(0, str(CARPETA_ALGORITMOS))

from algoritmo_busqueda.busqueda_binaria import buscar


TAMANOS = [1000, 10000, 100000, 1000000]
VARIANTES = ["aleatorios", "ascendente", "descendente"]

CLAVE = -1
REPETICIONES = 5
CALENTAMIENTO = 20000
BUSQUEDAS_POR_MEDICION = 100000


for n in TAMANOS:
    print(f"\nTamaño: {n}")

    for variante in VARIANTES:
        archivo = CARPETA_DATOS / f"{variante}_{n}.txt"

        with open(archivo, "r", encoding="utf-8") as datos:
            arreglo = [int(linea.strip()) for linea in datos if linea.strip()]

        # La búsqueda binaria necesita datos ordenados.
        arreglo.sort()

        # Calentamiento previo a las mediciones.
        for _ in range(CALENTAMIENTO):
            buscar(arreglo, CLAVE)

        tiempos = []

        for repeticion in range(REPETICIONES):
            inicio = perf_counter()

            for _ in range(BUSQUEDAS_POR_MEDICION):
                buscar(arreglo, CLAVE)

            fin = perf_counter()

            tiempo_ms = (
                    (fin - inicio) * 1000 / BUSQUEDAS_POR_MEDICION
            )

            tiempos.append(tiempo_ms)

            print(
                f"{variante} - Repetición {repeticion + 1}: "
                f"{tiempo_ms:.6f} ms"
            )

        promedio = mean(tiempos)
        desviacion = stdev(tiempos)

        print(f"Promedio: {promedio:.6f} ms")
        print(f"Desviación estándar: {desviacion:.6f} ms")
