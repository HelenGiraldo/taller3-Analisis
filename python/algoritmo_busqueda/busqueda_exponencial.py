
def buscar(arreglo, clave):
    n = len(arreglo)

    if n == 0:
        return -1

    if arreglo[0] == clave:
        return 0

    limite = 1

    while limite < n and arreglo[limite] < clave:
        limite *= 2

    izquierda = limite // 2
    derecha = min(limite, n - 1)

    while izquierda <= derecha:
        medio = (izquierda + derecha) // 2

        if arreglo[medio] == clave:
            return medio
        elif arreglo[medio] < clave:
            izquierda = medio + 1
        else:
            derecha = medio - 1

    return -1
