
def ordenar(arreglo):
    if len(arreglo) < 2:
        return

    auxiliar = [0] * len(arreglo)
    ordenar_recursivo(arreglo, auxiliar, 0, len(arreglo) - 1)


def ordenar_recursivo(arreglo, auxiliar, inicio, fin):
    if inicio < fin:
        medio = inicio + (fin - inicio) // 2

        ordenar_recursivo(arreglo, auxiliar, inicio, medio)
        ordenar_recursivo(arreglo, auxiliar, medio + 1, fin)

        mezclar(arreglo, auxiliar, inicio, medio, fin)


def mezclar(arreglo, auxiliar, inicio, medio, fin):
    for i in range(inicio, fin + 1):
        auxiliar[i] = arreglo[i]

    izquierda = inicio
    derecha = medio + 1

    for i in range(inicio, fin + 1):
        if izquierda > medio:
            arreglo[i] = auxiliar[derecha]
            derecha += 1

        elif derecha > fin:
            arreglo[i] = auxiliar[izquierda]
            izquierda += 1

        elif auxiliar[izquierda] <= auxiliar[derecha]:
            arreglo[i] = auxiliar[izquierda]
            izquierda += 1

        else:
            arreglo[i] = auxiliar[derecha]
            derecha += 1
