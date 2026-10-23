
import sys
import statistics
from pathlib import Path
from time import perf_counter_ns

CARPETA_PROYECTO = Path(__file__).resolve().parents[2]
CARPETA_DATOS = CARPETA_PROYECTO / "datos" / "ordenamiento"
sys.path.insert(0, str(CARPETA_PROYECTO / "python"))

from algoritmo_busqueda.busqueda_ternaria import busqueda_ternaria

TAMANO = 1_000_000
REPETICIONES = 5
BUSQUEDAS = 1000
CALENTAMIENTOS = 100


def cargar_arreglo():
    ruta = CARPETA_DATOS / f"ascendente_{TAMANO}.txt"

    with open(ruta, "r", encoding="utf-8") as archivo:
        arreglo = [int(linea.strip()) for linea in archivo if linea.strip()]

    if len(arreglo) != TAMANO:
        raise ValueError(
            f"Se esperaban {TAMANO} elementos en {ruta}, "
            f"pero se encontraron {len(arreglo)}."
        )

    return arreglo


def medir_posicion(arreglo, clave):
    inicio = perf_counter_ns()

    for _ in range(BUSQUEDAS):
        resultado = busqueda_ternaria(arreglo, clave)

    fin = perf_counter_ns()

    if resultado == -1:
        raise ValueError("No se encontró una clave que debería existir.")

    return (fin - inicio) / BUSQUEDAS / 1_000_000


def main():
    arreglo = cargar_arreglo()

    posiciones = {
        "Inicio": arreglo[0],
        "Centro": arreglo[len(arreglo) // 2],
        "Final": arreglo[-1]
    }

    for nombre, clave in posiciones.items():
        for _ in range(CALENTAMIENTOS):
            busqueda_ternaria(arreglo, clave)

        tiempos = [
            medir_posicion(arreglo, clave)
            for _ in range(REPETICIONES)
        ]

        print(f"\nPosición: {nombre}")
        print(f"Clave: {clave}")

        for i, tiempo in enumerate(tiempos, 1):
            print(f"Repetición {i}: {tiempo:.8f} ms")

        print(f"Promedio: {statistics.mean(tiempos):.8f} ms")
        print(
            "Desviación estándar: "
            f"{statistics.pstdev(tiempos):.8f} ms"
        )


if __name__ == "__main__":
    main()
