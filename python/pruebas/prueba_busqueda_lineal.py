from algoritmo_busqueda.busqueda_lineal import buscar


def probar_busqueda_lineal():
    arreglo = [10, 20, 30, 40, 50]

    # Buscar el primer elemento
    assert buscar(arreglo, 10) == 0

    # Buscar un elemento del medio
    assert buscar(arreglo, 30) == 2

    # Buscar el último elemento
    assert buscar(arreglo, 50) == 4

    # Buscar un elemento inexistente
    assert buscar(arreglo, 99) == -1

    # Buscar en un arreglo vacío
    assert buscar([], 10) == -1

    print("Todas las pruebas de Búsqueda Lineal pasaron correctamente.")


if __name__ == "__main__":
    probar_busqueda_lineal()