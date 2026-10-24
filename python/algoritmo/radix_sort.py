# Radix Sort LSD en base 10: ordena dígito por dígito, del menos al más significativo,
# usando Counting Sort (estable) en cada pasada.
# Fuente: GeeksforGeeks - "Radix Sort".
# Válido para enteros no negativos (el taller usa números de 8 dígitos).


def ordenar(arreglo):
    n = len(arreglo)

    if n <= 1:
        return

    maximo = obtener_maximo(arreglo)
    salida = [0] * n          # arreglo auxiliar de tamaño n

    # Una pasada por cada dígito del número más grande
    exp = 1
    while maximo // exp > 0:
        ordenar_por_digito(arreglo, salida, exp)
        exp *= 10


def obtener_maximo(arreglo):
    maximo = arreglo[0]

    for i in range(1, len(arreglo)):
        if arreglo[i] > maximo:
            maximo = arreglo[i]

    return maximo


def ordenar_por_digito(arreglo, salida, exp):
    # Counting Sort sobre el dígito que indica exp (1 = unidades, 10 = decenas, ...)
    n = len(arreglo)
    conteo = [0] * 10         # un contador por cada dígito, de 0 a 9

    # 1. Contar cuántos números tienen cada dígito
    for i in range(n):
        conteo[(arreglo[i] // exp) % 10] += 1

    # 2. Acumular: conteo[d] pasa a ser la posición final (exclusiva) del dígito d
    for d in range(1, 10):
        conteo[d] += conteo[d - 1]

    # 3. Recorrer de derecha a izquierda para conservar la estabilidad
    for i in range(n - 1, -1, -1):
        digito = (arreglo[i] // exp) % 10
        salida[conteo[digito] - 1] = arreglo[i]
        conteo[digito] -= 1

    # 4. Copiar el resultado de la pasada al arreglo original
    for i in range(n):
        arreglo[i] = salida[i]