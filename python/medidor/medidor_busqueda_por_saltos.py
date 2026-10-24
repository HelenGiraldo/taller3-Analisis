import bisect
import math
import os
import sys
import time

# Permite importar busqueda_por_saltos desde la carpeta del algoritmo.
sys.path.append(os.path.abspath(
    os.path.join(os.path.dirname(__file__), "..", "algoritmo")
))

from busqueda_por_saltos import buscar

ALGORITMO = "Búsqueda por Saltos"
LENGUAJE = "Python"
REPETICIONES = 5
BUSQUEDAS = 1_000
TAMANOS = [1_000, 10_000, 100_000, 1_000_000]
ESCENARIOS = ["claves_mixtas", "inicio", "centro", "final"]


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
CARPETA_ORDENAMIENTO = os.path.join(RAIZ, "datos", "ordenamiento")
CARPETA_BUSQUEDA = os.path.join(RAIZ, "datos", "busqueda")
ARCHIVO_CSV = os.path.join(RAIZ, "resultados", "busqueda_python.csv")

ENCABEZADO = ("algoritmo,lenguaje,escenario,n,rep1_ns,rep2_ns,rep3_ns,"
              "rep4_ns,rep5_ns,promedio_ns,desviacion_ns,estado,verificacion")


def leer_archivo(ruta):
    with open(ruta, "r", encoding="utf-8") as archivo:
        return [int(linea.strip()) for linea in archivo if linea.strip()]


def armar_claves(escenario, arreglo, n):
    # claves_mixtas: 500 presentes + 500 ausentes (de los archivos).
    # Las demás: una clave repetida 1.000 veces.
    if escenario == "claves_mixtas":
        presentes = leer_archivo(os.path.join(CARPETA_BUSQUEDA, f"claves_presentes_{n}.txt"))
        ausentes = leer_archivo(os.path.join(CARPETA_BUSQUEDA, f"claves_ausentes_{n}.txt"))
        return presentes + ausentes

    if escenario == "inicio":
        posicion = 0
    elif escenario == "centro":
        posicion = n // 2
    else:
        posicion = n - 1

    return [arreglo[posicion]] * BUSQUEDAS


def buscar_todas(arreglo, claves):
    for clave in claves:
        buscar(arreglo, clave)


def verificar(arreglo, claves):
    # Cada resultado debe coincidir con bisect: presente -> posición con esa clave; ausente -> -1
    for clave in claves:
        obtenido = buscar(arreglo, clave)
        posicion = bisect.bisect_left(arreglo, clave)
        existe = posicion < len(arreglo) and arreglo[posicion] == clave

        if existe:
            if obtenido < 0 or arreglo[obtenido] != clave:
                return False
        elif obtenido != -1:
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
    return round(valor, 2)


def escribir_linea(linea):
    es_nuevo = not os.path.exists(ARCHIVO_CSV)
    with open(ARCHIVO_CSV, "a", encoding="utf-8") as archivo:
        if es_nuevo:
            archivo.write(ENCABEZADO + "\n")
        archivo.write(linea + "\n")


def medir(escenario, n):
    # 1. Cargar los datos y armar las 1.000 claves (no se mide)
    arreglo = leer_archivo(os.path.join(CARPETA_ORDENAMIENTO, f"ascendente_{n}.txt"))
    claves = armar_claves(escenario, arreglo, n)

    tiempos = []   # ns por búsqueda

    print(f"\nEscenario: {escenario}")
    print(f"Tamaño: {n}")

    # 2. Repeticiones (Python no usa JIT, por eso no hay calentamiento)
    for r in range(REPETICIONES):
        inicio = time.perf_counter_ns()
        buscar_todas(arreglo, claves)
        fin = time.perf_counter_ns()

        tiempo = redondear((fin - inicio) / BUSQUEDAS)
        tiempos.append(tiempo)

        print(f"Repetición {r + 1}: {tiempo} ns por búsqueda")

    # 3. Verificación fuera del tiempo medido
    verificacion = "OK" if verificar(arreglo, claves) else "FALLA"

    promedio = redondear(calcular_promedio(tiempos))
    desviacion = redondear(calcular_desviacion(tiempos))

    print(f"Promedio: {promedio} ns por búsqueda")
    print(f"Desviación estándar: {desviacion} ns")
    print(f"Verificación: {verificacion}")

    reps = ",".join(str(t) for t in tiempos)
    escribir_linea(f"{ALGORITMO},{LENGUAJE},{escenario},{n},{reps},"
                   f"{promedio},{desviacion},medido,{verificacion}")


def main():
    print("Carpeta de datos:", CARPETA_ORDENAMIENTO)
    print("Archivo de resultados:", ARCHIVO_CSV)

    os.makedirs(os.path.dirname(ARCHIVO_CSV), exist_ok=True)

    for n in TAMANOS:
        for escenario in ESCENARIOS:
            medir(escenario, n)

    print("\nMediciones terminadas.")


if __name__ == "__main__":
    main()