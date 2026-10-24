import bisect
import os
import random
import sys

# Permite importar busqueda_por_saltos desde la carpeta del algoritmo.
sys.path.append(os.path.abspath(
    os.path.join(os.path.dirname(__file__), "..", "algoritmo")
))

from busqueda_por_saltos import buscar


def probar(arreglo, clave, esperado):
    obtenido = buscar(arreglo, clave)
    estado = "OK" if obtenido == esperado else "FALLA"
    print(f"Buscar {clave} -> posición {obtenido} (esperado {esperado}): {estado}")


def main():
    ejemplo = [3, 8, 15, 21, 30, 42, 55, 61, 70, 84, 91, 99, 105, 118, 130, 142]

    # Ejemplo a mano: n = 16, salto = 4
    print("Arreglo:", ejemplo)
    probar(ejemplo, 55, 6)       # presente, en el medio
    probar(ejemplo, 3, 0)        # primer elemento
    probar(ejemplo, 142, 15)     # último elemento
    probar(ejemplo, 60, -1)      # ausente, entre dos valores
    probar(ejemplo, 1, -1)       # menor que todos
    probar(ejemplo, 500, -1)     # mayor que todos

    # Casos límite
    probar([], 5, -1)
    probar([7], 7, 0)
    probar([7], 8, -1)
    probar([2, 4], 4, 1)
    probar([2, 4], 3, -1)
    probar([5, 5, 5, 5, 5], 6, -1)

    # Prueba grande contra bisect (todas las claves, presentes y ausentes)
    aleatorio = random.Random(42)
    datos = sorted(aleatorio.randint(10_000_000, 99_999_999) for _ in range(10_000))

    todo_bien = True
    for i in range(20_000):
        if i % 2 == 0:
            clave = datos[aleatorio.randrange(len(datos))]
        else:
            clave = aleatorio.randint(10_000_000, 99_999_999)
        obtenido = buscar(datos, clave)
        posicion = bisect.bisect_left(datos, clave)
        existe = posicion < len(datos) and datos[posicion] == clave
        correcto = (obtenido >= 0 and datos[obtenido] == clave) if existe else obtenido == -1
        if not correcto:
            todo_bien = False

    print("20.000 búsquedas aleatorias contra bisect:", "OK" if todo_bien else "FALLA")


if __name__ == "__main__":
    main()