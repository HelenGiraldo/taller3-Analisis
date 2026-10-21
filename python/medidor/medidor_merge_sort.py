
import math
import os
import statistics
import sys
import time

from algoritmo.MergeSort import ordenar


REPETICIONES = 5
CALENTAMIENTOS = 3
N = 1000000  #esto lo cambiamos dependiendo la cantidad de elementos a probar


def leer_datos(ruta):
    with open(ruta, "r", encoding="utf-8") as archivo:
        return [int(linea.strip()) for linea in archivo if linea.strip()]


def esta_ordenado(arreglo):
    for i in range(1, len(arreglo)):
        if arreglo[i] < arreglo[i - 1]:
            return False
    return True


def medir(variante, n):
    ruta = os.path.abspath(
        os.path.join(
            os.path.dirname(__file__),
            "..", "..",
            "datos", "ordenamiento",
            f"{variante}_{n}.txt"
        )
    )

    datos_originales = leer_datos(ruta)

    print(f"\nVariante: {variante}")
    print(f"Tamaño: {n}")

    # Calentamiento con una muestra pequeña.
    muestra = datos_originales[:min(1000, len(datos_originales))]

    for _ in range(CALENTAMIENTOS):
        calentamiento = muestra.copy()
        ordenar(calentamiento)

    tiempos = []

    for repeticion in range(REPETICIONES):
        copia = datos_originales.copy()

        inicio = time.perf_counter()
        ordenar(copia)
        fin = time.perf_counter()

        tiempo_ms = (fin - inicio) * 1000
        tiempos.append(tiempo_ms)

        if not esta_ordenado(copia):
            print("ERROR: el arreglo no quedó ordenado.")
            return

        print(f"Repetición {repeticion + 1}: {tiempo_ms:.4f} ms")

    promedio = statistics.mean(tiempos)
    desviacion = statistics.pstdev(tiempos)

    print(f"Promedio: {promedio:.4f} ms")
    print(f"Desviación estándar: {desviacion:.4f} ms")
    print("Verificación: OK")


if __name__ == "__main__":
    for variante in ("aleatorios", "ascendente", "descendente"):
        medir(variante, N)
