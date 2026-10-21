
def ordenar(arreglo):
    for i in range(1, len(arreglo)):
        actual = arreglo[i]
        j = i - 1

        while j >= 0 and arreglo[j] > actual:
            arreglo[j + 1] = arreglo[j]
            j -= 1

        arreglo[j + 1] = actual
