
def busqueda_ternaria(arreglo, clave):
    izquierda = 0
    derecha = len(arreglo) - 1

    while izquierda <= derecha:
        tercio = (derecha - izquierda) // 3
        medio1 = izquierda + tercio
        medio2 = derecha - tercio

        if arreglo[medio1] == clave:
            return medio1

        if arreglo[medio2] == clave:
            return medio2

        if clave < arreglo[medio1]:
            derecha = medio1 - 1
        elif clave > arreglo[medio2]:
            izquierda = medio2 + 1
        else:
            izquierda = medio1 + 1
            derecha = medio2 - 1

    return -1
