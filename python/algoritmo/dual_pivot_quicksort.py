def intercambiar(arreglo, a, b):
    temporal = arreglo[a]
    arreglo[a] = arreglo[b]
    arreglo[b] = temporal


def ordenar(arreglo):
    _ordenar(arreglo, 0, len(arreglo) - 1)


def _ordenar(arreglo, bajo, alto):
    if bajo >= alto:
        return

    # El pivote izquierdo (primer elemento) debe ser el menor de los dos
    if arreglo[bajo] > arreglo[alto]:
        intercambiar(arreglo, bajo, alto)

    pivote_izquierdo = arreglo[bajo]   # primer elemento
    pivote_derecho = arreglo[alto]     # último elemento

    menores = bajo + 1    # donde va el próximo elemento menor al pivote izquierdo
    mayores = alto - 1    # donde va el próximo elemento mayor al pivote derecho
    i = bajo + 1          # elemento que se está revisando

    while i <= mayores:
        if arreglo[i] < pivote_izquierdo:
            intercambiar(arreglo, i, menores)
            menores += 1
            i += 1
        elif arreglo[i] > pivote_derecho:
            intercambiar(arreglo, i, mayores)
            mayores -= 1
        else:
            i += 1

    # Los pivotes se colocan en su posición final
    menores -= 1
    mayores += 1
    intercambiar(arreglo, bajo, menores)
    intercambiar(arreglo, alto, mayores)

    # Se ordenan las tres zonas
    _ordenar(arreglo, bajo, menores - 1)
    _ordenar(arreglo, menores + 1, mayores - 1)
    _ordenar(arreglo, mayores + 1, alto)