import os
import sys

from algoritmo.dual_pivot_quicksort import ordenar

def probar(arreglo):
    print("Antes:   ", arreglo)
    ordenar(arreglo)
    print("Después: ", arreglo)
    ordenado = all(arreglo[i] <= arreglo[i + 1] for i in range(len(arreglo) - 1))
    print("Verificación:", "OK" if ordenado else "FALLA")
    print()


if __name__ == "__main__":
    probar([])
    probar([7])
    probar([9, 3])
    probar([5, 5, 5, 5, 5])
    probar([3, 1, 3, 2, 1, 3])
    probar([1, 2, 3, 4, 5, 6, 7, 8])
    probar([8, 7, 6, 5, 4, 3, 2, 1])
    probar([45, 12, 78, 3, 99, 23, 56, 1, 67, 34])