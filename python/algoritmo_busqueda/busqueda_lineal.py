
def buscar(arreglo, clave):
    for i in range(len(arreglo)):
        if arreglo[i] == clave:
            return i

    return -1
