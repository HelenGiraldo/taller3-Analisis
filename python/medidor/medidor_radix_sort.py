import math
import os
import sys
import time

# Permite importar radix_sort desde la carpeta del algoritmo.
sys.path.append(os.path.abspath(
    os.path.join(os.path.dirname(__file__), "..", "algoritmo")
))

from radix_sort import ordenar

ALGORITMO = "Radix Sort"
LENGUAJE = "Python"
REPETICIONES = 5
TAMANOS = [1_000, 10_000, 100_000, 1_000_000]
VARIANTES = ["aleatorios", "ascendente", "descendente"]


def encontrar_raiz():
    # Sube de carpeta hasta encontrar la que contiene "datos"
    carpeta = os.path.dirname(os.path.abspath(__file__))
    while not os.path.isdir(os.path.join(carpeta, "datos")):
        padre = os.path.dirname(carpeta)
        if padre == carpeta:
            raise FileNotFoundError("No se encontró la carpeta 'datos'")
        carpeta = padre
    return carpeta


RAIZ = encontrar_raiz()
CARPETA_DATOS = os.path.join(RAIZ, "datos", "ordenamiento")
ARCHIVO_CSV = os.path.join(RAIZ, "resultados", "ordenamiento_python.csv")

ENCABEZADO = ("algoritmo,lenguaje,variante,n,rep1_ms,rep2_ms,rep3_ms,"
              "rep4_ms,rep5_ms,promedio_ms,desviacion_ms,estado,verificacion")


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
    return math.sqrt(suma / len(valores))     # poblacional, igual que en Java


def redondear(valor):
    return round(valor, 4)


def escribir_linea(linea):
    es_nuevo = not os.path.exists(ARCHIVO_CSV)
    with open(ARCHIVO_CSV, "a", encoding="utf-8") as archivo:
        if es_nuevo:
            archivo.write(ENCABEZADO + "\n")
        archivo.write(linea + "\n")


def guardar_resultado(variante, n, tiempos, promedio, desviacion, verificacion):
    reps = ",".join(str(t) for t in tiempos)
    escribir_linea(f"{ALGORITMO},{LENGUAJE},{variante},{n},{reps},"
                   f"{promedio},{desviacion},medido,{verificacion}")


def medir(variante, n):
    # 1. Cargar los datos (no se mide)
    original = leer_archivo(os.path.join(CARPETA_DATOS, f"{variante}_{n}.txt"))

    tiempos = []
    ordenado_correcto = True

    print(f"\nVariante: {variante}")
    print(f"Tamaño: {n}")

    # 2. Repeticiones (Python no usa JIT, por eso no hay calentamiento)
    for r in range(REPETICIONES):
        copia = original[:]                    # copia nueva, no se mide

        inicio = time.perf_counter_ns()
        ordenar(copia)
        fin = time.perf_counter_ns()

        tiempo = redondear((fin - inicio) / 1_000_000)   # ms
        tiempos.append(tiempo)

        if not esta_ordenado(copia):           # verificación fuera del tiempo
            ordenado_correcto = False

        print(f"Repetición {r + 1}: {tiempo} ms")

    promedio = redondear(calcular_promedio(tiempos))
    desviacion = redondear(calcular_desviacion(tiempos))
    verificacion = "OK" if ordenado_correcto else "FALLA"

    print(f"Promedio: {promedio} ms")
    print(f"Desviación estándar: {desviacion} ms")
    print(f"Verificación: {verificacion}")

    guardar_resultado(variante, n, tiempos, promedio, desviacion, verificacion)


def main():
    print("Carpeta de datos:", CARPETA_DATOS)
    print("Archivo de resultados:", ARCHIVO_CSV)

    os.makedirs(os.path.dirname(ARCHIVO_CSV), exist_ok=True)

    for n in TAMANOS:
        for variante in VARIANTES:
            medir(variante, n)

    print("\nMediciones terminadas.")


if __name__ == "__main__":
    main()