
import statistics
import sys
import time
from pathlib import Path

# Ruta a la raíz del proyecto
BASE = Path(__file__).resolve().parents[2]

# Permite importar los algoritmos desde la carpeta python
sys.path.insert(0, str(BASE / "python"))

from algoritmo.Heap_sort import ordenar

# Ruta a los archivos de datos generados con Java
CARPETA_DATOS = BASE / "datos" / "ordenamiento"

TAMANOS = [1000, 10000, 100000, 1000000]

VARIANTES = ["aleatorios", "ascendente", "descendente"]

REPETICIONES = 5
CALENTAMIENTOS = 1


def leer_datos(ruta):
    with open(ruta, "r", encoding="utf-8") as archivo:
        return [int(linea.strip()) for linea in archivo if linea.strip()]


def verificar_orden(arreglo):
    for i in range(1, len(arreglo)):
        if arreglo[i - 1] > arreglo[i]:
            return False
    return True


def medir(arreglo_original):
    tiempos = []

    # Calentamiento: no se incluye en las mediciones
    for _ in range(CALENTAMIENTOS):
        copia = arreglo_original.copy()
        ordenar(copia)

        if not verificar_orden(copia):
            raise ValueError("El arreglo no quedó ordenado.")

    # Cinco mediciones independientes
    for repeticion in range(REPETICIONES):
        # Copiar antes de iniciar el cronómetro
        copia = arreglo_original.copy()

        inicio = time.perf_counter_ns()
        ordenar(copia)
        fin = time.perf_counter_ns()

        tiempo_ms = (fin - inicio) / 1_000_000
        tiempos.append(tiempo_ms)

        # Verificar después de detener el cronómetro
        if not verificar_orden(copia):
            raise ValueError("El arreglo no quedó ordenado.")

        print(f"Repetición {repeticion + 1}: {tiempo_ms:.4f} ms")

    promedio = statistics.mean(tiempos)
    desviacion = statistics.pstdev(tiempos)

    print(f"Promedio: {promedio:.4f} ms")
    print(f"Desviación estándar: {desviacion:.4f} ms")
    print("Verificación: OK")

    return promedio, desviacion


def main():
    for n in TAMANOS:
        for variante in VARIANTES:
            ruta = CARPETA_DATOS / f"{variante}_{n}.txt"

            print(f"\nVariante: {variante}")
            print(f"Tamaño: {n}")

            if not ruta.exists():
                print(f"Archivo no encontrado: {ruta}")
                continue

            try:
                arreglo = leer_datos(ruta)

                if len(arreglo) != n:
                    print(
                        f"Error: se esperaban {n} elementos, "
                        f"pero se encontraron {len(arreglo)}."
                    )
                    continue

                medir(arreglo)

            except (OSError, ValueError) as error:
                print(f"Error: {error}")


if __name__ == "__main__":
    main()
