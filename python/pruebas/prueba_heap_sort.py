
from pathlib import Path
import sys

# Ruta a la raíz del proyecto
BASE = Path(__file__).resolve().parents[2]

# Permite importar HeapSort desde la carpeta algoritmo
sys.path.insert(0, str(BASE / "python"))

from algoritmo.Heap_sort import ordenar

# Ruta al archivo de prueba
ruta = BASE / "datos" / "ordenamiento" / "aleatorios_1000.txt"

try:
    with open(ruta, "r", encoding="utf-8") as archivo:
        arreglo = [
            int(linea.strip())
            for linea in archivo
            if linea.strip()
        ]

    ordenar(arreglo)

    ordenado = True

    for i in range(1, len(arreglo)):
        if arreglo[i] < arreglo[i - 1]:
            ordenado = False
            break

    if ordenado:
        print(
            "Heap Sort ordenó correctamente "
            f"los {len(arreglo)} números."
        )
    else:
        print("Error: el arreglo no quedó ordenado.")

except OSError as error:
    print(f"No se pudieron leer los datos: {error}")
