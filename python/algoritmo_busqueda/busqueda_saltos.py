
from math import isqrt

def buscar(arreglo, clave):
    n = len(arreglo)

    if n == 0:
        return -1

    salto = isqrt(n)
    inicio = 0
    fin = salto

    while inicio < n and arreglo[min(fin, n) - 1] < clave:
        inicio = fin
        fin += salto

        if inicio >= n:
            return -1

    for i in range(inicio, min(fin, n)):
        if arreglo[i] == clave:
            return i
        if arreglo[i] > clave:
            return -1

    return -1
