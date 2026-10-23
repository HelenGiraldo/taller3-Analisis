
from BusquedaTernaria import busqueda_ternaria


def probar(clave, esperado, descripcion):
    arreglo = [10, 20, 30, 40, 50, 60, 70]
    resultado = busqueda_ternaria(arreglo, clave)

    correcto = resultado == esperado

    print(
        f"{descripcion}: "
        f"{'CORRECTO' if correcto else 'ERROR'}"
    )

    if not correcto:
        print(f"Esperado: {esperado}, obtenido: {resultado}")


def main():
    probar(10, 0, "Buscar primer elemento")
    probar(40, 3, "Buscar elemento central")
    probar(70, 6, "Buscar último elemento")
    probar(30, 2, "Buscar elemento intermedio")
    probar(25, -1, "Buscar elemento inexistente")

    print(
        "Arreglo vacío:",
        busqueda_ternaria([], 10)
    )

    print(
        "Un solo elemento:",
        busqueda_ternaria([15], 15)
    )

    print(
        "Un solo elemento ausente:",
        busqueda_ternaria([15], 20)
    )


if __name__ == "__main__":
    main()
