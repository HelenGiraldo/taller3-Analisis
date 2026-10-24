# Bucket Sort con n cubetas: reparte los valores en cubetas según su valor,
# ordena cada cubeta con Insertion Sort y copia las cubetas en orden.
# Fuente: GeeksforGeeks - "Bucket Sort".
# Cubeta de un valor v = (v - minimo) * k // (maximo - minimo + 1), con k = n.


def ordenar(arreglo):
    n = len(arreglo)

    if n <= 1:
        return

    minimo = obtener_minimo(arreglo)
    maximo = obtener_maximo(arreglo)
    k = n                                  # cantidad de cubetas
    rango = maximo - minimo + 1

    # 1. Crear las cubetas vacías
    cubetas = [[] for _ in range(k)]

    # 2. Repartir cada valor en su cubeta
    for i in range(n):
        indice = (arreglo[i] - minimo) * k // rango
        cubetas[indice].append(arreglo[i])

    # 3. Ordenar cada cubeta y copiarla al arreglo en orden
    posicion = 0
    for cubeta in cubetas:
        insertion_sort(cubeta)
        for valor in cubeta:
            arreglo[posicion] = valor
            posicion += 1


def obtener_minimo(arreglo):
    minimo = arreglo[0]

    for i in range(1, len(arreglo)):
        if arreglo[i] < minimo:
            minimo = arreglo[i]

    return minimo


def obtener_maximo(arreglo):
    maximo = arreglo[0]

    for i in range(1, len(arreglo)):
        if arreglo[i] > maximo:
            maximo = arreglo[i]

    return maximo


def insertion_sort(lista):
    # Ordena una cubeta (lista pequeña), estable porque solo mueve los mayores
    for i in range(1, len(lista)):
        clave = lista[i]
        j = i - 1
        while j >= 0 and lista[j] > clave:
            lista[j + 1] = lista[j]
            j -= 1
        lista[j + 1] = clave