import math


def buscar(arreglo, clave):
    """Búsqueda por Saltos (Jump Search) sobre un arreglo ordenado de forma ascendente.

    Se avanza en bloques de tamaño raíz(n) hasta encontrar el bloque que podría
    contener la clave, y dentro de ese bloque se busca de forma lineal.
    Devuelve la posición de la clave, o -1 si no está.
    Fuente: GeeksforGeeks - "Jump Search".
    """
    n = len(arreglo)

    if n == 0:
        return -1

    salto = int(math.sqrt(n))      # tamaño del bloque
    anterior = 0                   # inicio del bloque actual
    limite = min(salto, n)         # fin (exclusivo) del bloque actual

    # 1. Saltar de bloque en bloque mientras el último elemento del bloque sea menor que la clave
    while arreglo[limite - 1] < clave:
        anterior = limite
        limite = min(limite + salto, n)

        if anterior >= n:
            return -1              # se salió del arreglo: la clave es mayor que todos

    # 2. Búsqueda lineal dentro del bloque encontrado
    while anterior < limite and arreglo[anterior] < clave:
        anterior += 1

    # 3. Verificar si se encontró la clave
    if anterior < limite and arreglo[anterior] == clave:
        return anterior

    return -1