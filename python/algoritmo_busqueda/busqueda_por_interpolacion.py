def buscar(arreglo, clave):
    """Búsqueda por Interpolación sobre un arreglo ordenado de forma ascendente.

    En lugar de mirar siempre el punto medio (como la búsqueda binaria), estima la posición
    de la clave suponiendo que los valores están repartidos de forma uniforme:

        pos = inicio + (clave - arreglo[inicio]) * (fin - inicio) / (arreglo[fin] - arreglo[inicio])

    Devuelve la posición de la clave, o -1 si no está.
    Fuente: GeeksforGeeks - "Interpolation Search".
    """
    inicio = 0
    fin = len(arreglo) - 1

    # La clave solo puede estar mientras quede rango y esté entre el menor y el mayor valor del rango
    while inicio <= fin and arreglo[inicio] <= clave <= arreglo[fin]:

        # Todos los valores del rango son iguales: evita dividir entre cero
        if arreglo[inicio] == arreglo[fin]:
            return inicio if arreglo[inicio] == clave else -1

        # Posición estimada (división entera, igual que en la versión de Java)
        numerador = (clave - arreglo[inicio]) * (fin - inicio)
        denominador = arreglo[fin] - arreglo[inicio]
        posicion = inicio + numerador // denominador

        if arreglo[posicion] == clave:
            return posicion

        if arreglo[posicion] < clave:
            inicio = posicion + 1       # la clave está a la derecha de la posición estimada
        else:
            fin = posicion - 1          # la clave está a la izquierda

    return -1