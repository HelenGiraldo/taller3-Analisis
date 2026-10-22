
def ordenar(arreglo):
    n = len(arreglo)

    # Construir el Max Heap
    for i in range(n // 2 - 1, -1, -1):
        heapify(arreglo, n, i)

    # Extraer los elementos del heap
    for i in range(n - 1, 0, -1):

        # Intercambiar la raiz con el ultimo elemento
        arreglo[0], arreglo[i] = arreglo[i], arreglo[0]

        # Restaurar el Max Heap
        heapify(arreglo, i, 0)


def heapify(arreglo, n, i):
    mayor = i
    izquierdo = 2 * i + 1
    derecho = 2 * i + 2

    # Comprobar si el hijo izquierdo es mayor
    if izquierdo < n and arreglo[izquierdo] > arreglo[mayor]:
        mayor = izquierdo

    # Comprobar si el hijo derecho es mayor
    if derecho < n and arreglo[derecho] > arreglo[mayor]:
        mayor = derecho

    # Si un hijo es mayor, intercambiar
    if mayor != i:
        arreglo[i], arreglo[mayor] = arreglo[mayor], arreglo[i]

        # Continuar reorganizando el subarbol
        heapify(arreglo, n, mayor)
