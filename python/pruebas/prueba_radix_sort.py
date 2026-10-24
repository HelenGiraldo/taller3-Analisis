import os
import random
import sys

# Permite importar radix_sort desde la carpeta del algoritmo.
sys.path.append(os.path.abspath(
    os.path.join(os.path.dirname(__file__), "..", "algoritmo")
))

from radix_sort import ordenar


def probar(arreglo):
    pequeno = len(arreglo) <= 10
    esperado = sorted(arreglo)

    if pequeno:
        print("Antes:   ", arreglo)

    ordenar(arreglo)

    if pequeno:
        print("Después: ", arreglo)
    else:
        print("Arreglo de", len(arreglo), "elementos")

    ordenado = all(arreglo[i] <= arreglo[i + 1] for i in range(len(arreglo) - 1))
    print("Verificación:", "OK" if ordenado and arreglo == esperado else "FALLA")
    print()


if __name__ == "__main__":
    probar([])
    probar([7])
    probar([9, 3])
    probar([5, 5, 5, 5, 5])
    probar([3, 1, 3, 2, 1, 3])
    probar([1, 2, 3, 4, 5, 6, 7, 8])
    probar([8, 7, 6, 5, 4, 3, 2, 1])
    probar([329, 457, 657, 839, 436, 720, 355])
    probar([45, 12, 78, 3, 99, 23, 56, 1, 67, 34])

    # Prueba con números de 8 dígitos, como los del taller
    aleatorio = random.Random(42)
    probar([aleatorio.randint(10_000_000, 99_999_999) for _ in range(1000)])