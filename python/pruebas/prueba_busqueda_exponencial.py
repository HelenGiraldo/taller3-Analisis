import bisect
import os
import random
import sys

# Permite importar busqueda_exponencial desde la carpeta del algoritmo.
sys.path.append(os.path.abspath(
    os.path.join(os.path.dirname(__file__), "..", "algoritmo_busqueda")
))

from busqueda_exponencial import buscar


def probar(arreglo, clave, esperado):
    obtenido = buscar(arreglo, clave)
    estado = "OK" if obtenido == esperado else "FALLA"
    print(f"Buscar {clave} -> posición {obtenido} (esperado {esperado}): {estado}")


def main():
    ejemplo = [3, 8, 15, 21, 30, 42, 55, 61, 70, 84, 91, 99, 105, 118, 130, 142]

    # Ejemplo a mano: para 55 el índice se duplica 1, 2, 4, 8 (arreglo[8] = 70 >= 55),
    # y la búsqueda binaria se hace en el rango [4, 8], donde encuentra 55 en la posición 6.
    print("Arreglo:", ejemplo)
    probar(ejemplo, 55, 6)       # presente
    probar(ejemplo, 3, 0)        # primer elemento
    probar(ejemplo, 142, 15)     # último elemento (n es potencia de 2)
    probar(ejemplo, 60, -1)      # ausente, entre dos valores
    probar(ejemplo, 1, -1)       # menor que todos
    probar(ejemplo, 500, -1)     # mayor que todos

    # Casos límite
    probar([], 5, -1)
    probar([7], 7, 0)
    probar([7], 8, -1)
    probar([2, 4], 4, 1)
    probar([2, 4], 3, -1)
    probar([5, 5, 5, 5, 5], 5, 0)
    probar([5, 5, 5, 5, 5], 6, -1)

    # Todos los tamaños de 0 a 300 (potencias de 2 y no potencias), todas las posiciones y claves ausentes
    tamanos_bien = True
    for n in range(301):
        arreglo = [10 * i + 5 for i in range(n)]
        for i in range(n):
            if buscar(arreglo, arreglo[i]) != i:
                tamanos_bien = False
        for clave in range(10 * n + 11):
            if clave % 10 != 5 and buscar(arreglo, clave) != -1:
                tamanos_bien = False
    print("Tamaños de 0 a 300, todas las claves:", "OK" if tamanos_bien else "FALLA")

    # Prueba grande contra bisect (claves presentes y ausentes), datos como los del taller
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