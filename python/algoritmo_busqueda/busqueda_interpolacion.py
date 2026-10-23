
def buscar(arreglo, clave):
    izquierda = 0
    derecha = len(arreglo) - 1

    while (
            izquierda <= derecha
            and arreglo[izquierda] <= clave <= arreglo[derecha]
    ):
        if arreglo[izquierda] == arreglo[derecha]:
            if arreglo[izquierda] == clave:
                return izquierda
            return -1

        posicion = izquierda + (
                (clave - arreglo[izquierda])
                * (derecha - izquierda)
                // (arreglo[derecha] - arreglo[izquierda])
        )

        if arreglo[posicion] == clave:
            return posicion
        elif arreglo[posicion] < clave:
            izquierda = posicion + 1
        else:
            derecha = posicion - 1

    return -1
