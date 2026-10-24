import sys
import os
import time
import math
from pathlib import Path

RAIZ_PROYECTO = Path(__file__).resolve().parent.parent.parent
CARPETA_PYTHON = RAIZ_PROYECTO / "python"

sys.path.insert(0, str(CARPETA_PYTHON))

from algoritmo.dual_pivot_quicksort import ordenar

ALGORITMO = "Dual-Pivot QuickSort"
LENGUAJE = "Python"
REPETICIONES = 5
TAMANOS = [1_000, 10_000, 100_000, 1_000_000]
VARIANTES = ["aleatorios", "ascendente", "descendente"]

CARPETA_DATOS = RAIZ_PROYECTO / "datos" / "ordenamiento"
ARCHIVO_CSV = RAIZ_PROYECTO / "resultados" / "ordenamiento_python.csv"

ENCABEZADO = (
    "algoritmo,lenguaje,variante,n,rep1_ms,rep2_ms,rep3_ms,"
    "rep4_ms,rep5_ms,promedio_ms,desviacion_ms,estado,verificacion"
)


def leer_archivo(ruta):
    with open(ruta, "r", encoding="utf-8") as archivo:
        return [int(linea.strip()) for linea in archivo if linea.strip()]


def esta_ordenado(arreglo):
    for i in range(len(arreglo) - 1):
        if arreglo[i] > arreglo[i + 1]:
            return False
    return True


def calcular_promedio(valores):
    return sum(valores) / len(valores)


def calcular_desviacion(valores):
    promedio = calcular_promedio(valores)
    suma = 0.0

    for valor in valores:
        suma += (valor - promedio) ** 2

    return math.sqrt(suma / len(valores))


def redondear(valor):
    return round(valor, 4)


def escribir_linea(linea):
    ARCHIVO_CSV.parent.mkdir(parents=True, exist_ok=True)
    es_nuevo = not ARCHIVO_CSV.exists()

    with open(ARCHIVO_CSV, "a", encoding="utf-8", newline="") as archivo:
        if es_nuevo:
            archivo.write(ENCABEZADO + "\n")

        archivo.write(linea + "\n")


def guardar_resultado(variante, n, tiempos, promedio, desviacion, verificacion):
    reps = ",".join(str(t) for t in tiempos)

    escribir_linea(
        f"{ALGORITMO},{LENGUAJE},{variante},{n},{reps},"
        f"{promedio},{desviacion},medido,{verificacion}"
    )


def guardar_error(variante, n):
    escribir_linea(
        f"{ALGORITMO},{LENGUAJE},{variante},{n},"
        f",,,,,,,ERROR_RECURSION,"
    )


def medir(variante, n):
    ruta_datos = CARPETA_DATOS / f"{variante}_{n}.txt"

    if not ruta_datos.exists():
        print(f"\nERROR: no se encontró el archivo de datos:")
        print(ruta_datos)
        return

    original = leer_archivo(ruta_datos)

    tiempos = []
    ordenado_correcto = True

    print(f"\nVariante: {variante}")
    print(f"Tamaño: {n}")

    try:
        for r in range(REPETICIONES):
            copia = original[:]

            inicio = time.perf_counter_ns()
            ordenar(copia)
            fin = time.perf_counter_ns()

            tiempo = redondear((fin - inicio) / 1_000_000)
            tiempos.append(tiempo)

            if not esta_ordenado(copia):
                ordenado_correcto = False

            print(f"Repetición {r + 1}: {tiempo} ms")

        promedio = redondear(calcular_promedio(tiempos))
        desviacion = redondear(calcular_desviacion(tiempos))
        verificacion = "OK" if ordenado_correcto else "FALLA"

        print(f"Promedio: {promedio} ms")
        print(f"Desviación estándar: {desviacion} ms")
        print(f"Verificación: {verificacion}")

        guardar_resultado(
            variante, n, tiempos, promedio, desviacion, verificacion
        )

    except RecursionError:
        print("ERROR: se superó el límite de recursión de Python (RecursionError)")
        guardar_error(variante, n)


def main():
    ARCHIVO_CSV.parent.mkdir(parents=True, exist_ok=True)

    print(f"Carpeta de datos: {CARPETA_DATOS}")
    print(f"Archivo de resultados: {ARCHIVO_CSV}")

    for n in TAMANOS:
        for variante in VARIANTES:
            medir(variante, n)

    print("\nMediciones terminadas.")


if __name__ == "__main__":
    main()