
def buscar(arreglo, clave):
    primero = 0
    ultimo = len(arreglo) - 1

    while primero <= ultimo:
        medio = (primero + ultimo) // 2

        if arreglo[medio] == clave:
            return medio
        elif arreglo[medio] < clave:
            primero = medio + 1
        else:
            ultimo = medio - 1

    return -1
