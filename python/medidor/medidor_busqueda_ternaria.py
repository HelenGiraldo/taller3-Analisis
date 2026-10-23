
import sys
import statistics
from pathlib import Path
from time import perf_counter_ns

CARPETA_PROYECTO = Path(__file__).resolve().parents[2]
CARPETA_DATOS = CARPETA_PROYECTO / "datos" / "ordenamiento"
sys.path.insert(0, str(CARPETA_PROYECTO / "python"))

from algoritmo_busqueda.busqueda_ternaria import busqueda_ternaria

TAMANOS = [1000, 10000, 100000, 1000000]
VARIANTES = ["aleatorios", "ascendente", "descendente"]
REPETICIONES = 5
CLAVE = -1


def cargar_arreglo(tamano, variante):
    ruta = CARPETA_DATOS / f"{variante}_{tamano}.txt"

    with open(ruta, "r", encoding="utf-8") as archivo:
        arreglo = [int(linea.strip()) for linea in archivo if linea.strip()]

    if len(arreglo) != tamano:
        raise ValueError(
            f"Se esperaban {tamano} elementos en {ruta}, "
            f"pero se encontraron {len(arreglo)}."
        )

    arreglo.sort()
    return arreglo


def medir(arreglo):
    inicio = perf_counter_ns()
    resultado = busqueda_ternaria(arreglo, CLAVE)
    fin = perf_counter_ns()

    if resultado != -1:
        raise ValueError("La búsqueda devolvió un resultado inesperado.")

    return (fin - inicio) / 1_000_000


def main():
    for tamano in TAMANOS:
        for variante in VARIANTES:
            ruta = CARPETA_DATOS / f"{variante}_{tamano}.txt"
            if not ruta.exists():
                print(f"\nArchivo no encontrado: {ruta}")
                continue

            arreglo = cargar_arreglo(tamano, variante)

            # Calentamiento
            for _ in range(100):
                busqueda_ternaria(arreglo, CLAVE)

            tiempos = [medir(arreglo) for _ in range(REPETICIONES)]

            print(f"\nVariante: {variante}")
            print(f"Tamaño: {tamano}")

            for i, tiempo in enumerate(tiempos, 1):
                print(f"Repetición {i}: {tiempo:.6f} ms")

            print(f"Promedio: {statistics.mean(tiempos):.6f} ms")
            print(
                "Desviación estándar: "
                f"{statistics.pstdev(tiempos):.6f} ms"
            )
            print("Resultado: -1 (correcto)")


if __name__ == "__main__":
    main()
