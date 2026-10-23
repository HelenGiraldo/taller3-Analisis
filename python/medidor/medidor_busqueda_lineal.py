
import time
import statistics
import sys
from pathlib import Path

# Ruta a la raíz del proyecto
BASE = Path(__file__).resolve().parents[2]

# Permite importar los algoritmos desde la carpeta python/algoritmo
sys.path.insert(0, str(BASE / "python"))

from algoritmo_busqueda.busqueda_lineal import buscar

# Ruta a los archivos de datos generados con Java
CARPETA_DATOS = BASE / "datos" / "ordenamiento"

TAMANOS = [1000, 10000, 100000, 1000000]
VARIANTES = ["aleatorios", "ascendente", "descendente"]
REPETICIONES = 5
CALENTAMIENTOS = 1

# Valor inexistente en los datos del taller
CLAVE = -1


def leer_datos(ruta):
    with open(ruta, "r", encoding="utf-8") as archivo:
        return [int(linea.strip()) for linea in archivo]


def medir(arreglo):
    tiempos = []

    # Calentamiento: no se incluye en las mediciones
    for _ in range(CALENTAMIENTOS):
        resultado = buscar(arreglo, CLAVE)

        if resultado != -1:
            raise ValueError("La búsqueda debía devolver -1.")

    # Cinco mediciones independientes
    for repeticion in range(REPETICIONES):
        inicio = time.perf_counter_ns()
        resultado = buscar(arreglo, CLAVE)
        fin = time.perf_counter_ns()

        tiempo_ms = (fin - inicio) / 1_000_000
        tiempos.append(tiempo_ms)

        # Verificar después de detener el cronómetro
        if resultado != -1:
            raise ValueError("La búsqueda debía devolver -1.")

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

            arreglo = leer_datos(ruta)

            # Comprobar que el archivo contiene el tamaño esperado
            if len(arreglo) != n:
                print(
                    f"Error: se esperaban {n} elementos, "
                    f"pero se encontraron {len(arreglo)}."
                )
                continue

            medir(arreglo)


if __name__ == "__main__":
    main()
