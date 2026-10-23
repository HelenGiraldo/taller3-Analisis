
from pathlib import Path
import sys

# Ruta a la raíz del proyecto
BASE = Path(__file__).resolve().parents[2]

# Permite importar los algoritmos de búsqueda
sys.path.insert(0, str(BASE / "python"))

from algoritmo_busqueda.busqueda_lineal import buscar as lineal
from algoritmo_busqueda.busqueda_binaria import buscar as binaria
from algoritmo_busqueda.busqueda_ternaria import buscar as ternaria
from algoritmo_busqueda.busqueda_saltos import buscar as saltos
from algoritmo_busqueda.busqueda_interpolacion import buscar as interpolacion
from algoritmo_busqueda.busqueda_exponencial import buscar as exponencial

# Ruta al archivo de prueba
ruta = BASE / "datos" / "ordenamiento" / "ascendente_1000.txt"

# Algoritmos que se van a probar
algoritmos = {
    "Búsqueda Lineal": lineal,
    "Búsqueda Binaria": binaria,
    "Búsqueda Ternaria": ternaria,
    "Búsqueda por Saltos": saltos,
    "Búsqueda por Interpolación": interpolacion,
    "Búsqueda Exponencial": exponencial,
}

try:
    with open(ruta, "r", encoding="utf-8") as archivo:
        arreglo = [
            int(linea.strip())
            for linea in archivo
            if linea.strip()
        ]

    # Claves ubicadas al inicio, centro y final
    claves = [
        arreglo[0],
        arreglo[len(arreglo) // 2],
        arreglo[-1]
    ]

    for nombre, buscar in algoritmos.items():
        correcto = True

        for clave in claves:
            posicion = buscar(arreglo, clave)

            if posicion == -1 or arreglo[posicion] != clave:
                correcto = False
                break

        if correcto:
            print(
                f"{nombre} encontró correctamente "
                "las tres claves."
            )
        else:
            print(f"Error en {nombre}.")

except OSError as error:
    print(f"No se pudieron leer los datos: {error}")

except (IndexError, ZeroDivisionError) as error:
    print(f"Error al procesar los datos: {error}")
