
import time
import statistics
import sys
from pathlib import Path

BASE = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(BASE / "python"))

from algoritmo_busqueda.busqueda_binaria import buscar

CARPETA_DATOS = BASE / "datos" / "ordenamiento"

TAMANO = 1_000_000
REPETICIONES = 5
BUSQUEDAS = 1000


def leer_datos():
    archivo = CARPETA_DATOS / f"ascendente_{TAMANO}.txt"

    with open(archivo, "r", encoding="utf-8") as f:
        arreglo = [int(linea.strip()) for linea in f if linea.strip()]

    return arreglo


def medir_posicion(arreglo, clave):
    tiempos = []

    # Calentamiento
    for _ in range(100):
        buscar(arreglo, clave)

    for repeticion in range(REPETICIONES):
        inicio = time.perf_counter_ns()

        for _ in range(BUSQUEDAS):
            buscar(arreglo, clave)

        fin = time.perf_counter_ns()

        tiempo_ms = (fin - inicio) / BUSQUEDAS / 1_000_000
        tiempos.append(tiempo_ms)

        print(
            f"  Repetición {repeticion + 1}: "
            f"{tiempo_ms:.8f} ms por búsqueda"
        )

    promedio = statistics.mean(tiempos)
    desviacion = statistics.pstdev(tiempos)

    print(f"  Promedio: {promedio:.8f} ms")
    print(f"  Desviación estándar: {desviacion:.8f} ms")

    return promedio, desviacion


def main():
    arreglo = leer_datos()

    if len(arreglo) != TAMANO:
        print(f"Error: se esperaban {TAMANO} elementos.")
        return

    posiciones = {
        "Inicio": arreglo[0],
        "Centro": arreglo[len(arreglo) // 2],
        "Final": arreglo[-1]
    }

    print("BÚSQUEDA BINARIA - PRUEBA DE POSICIONES")
    print(f"Tamaño del arreglo: {len(arreglo)}")
    print()

    for nombre, clave in posiciones.items():
        print(f"Posición: {nombre} | Clave: {clave}")
        medir_posicion(arreglo, clave)
        print()


if __name__ == "__main__":
    main()
