def buscar(arreglo, clave):
    """Búsqueda Exponencial sobre un arreglo ordenado de forma ascendente.

    Primero encuentra un rango donde puede estar la clave duplicando un índice (1, 2, 4, 8, ...)
    mientras el valor en ese índice sea menor que la clave; luego aplica búsqueda binaria
    solo dentro de ese rango [i/2, min(i, n-1)].
    Devuelve la posición de la clave, o -1 si no está.
    Fuente: GeeksforGeeks - "Exponential Search".
    """
    n = len(arreglo)

    if n == 0:
        return -1

    # 1. Si la clave está en la primera posición, termina de inmediato
    if arreglo[0] == clave:
        return 0

    # 2. Encontrar el rango: duplicar el índice mientras el valor sea menor que la clave
    indice = 1

    while indice < n and arreglo[indice] < clave:
        indice = indice * 2

    # 3. Búsqueda binaria en [indice // 2, min(indice, n - 1)]
    return _busqueda_binaria(arreglo, indice // 2, min(indice, n - 1), clave)


def _busqueda_binaria(arreglo, inicio, fin, clave):
    while inicio <= fin:
        medio = inicio + (fin - inicio) // 2

        if arreglo[medio] == clave:
            return medio

        if arreglo[medio] < clave:
            inicio = medio + 1
        else:
            fin = medio - 1

    return -1