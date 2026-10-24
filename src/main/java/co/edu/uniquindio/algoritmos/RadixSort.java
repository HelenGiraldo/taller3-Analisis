package co.edu.uniquindio.algoritmos;

/**
 * Radix Sort LSD en base 10: ordena dígito por dígito, del menos al más significativo,
 * usando Counting Sort (estable) en cada pasada.
 * Fuente: GeeksforGeeks - "Radix Sort".
 * Válido para enteros no negativos menores a 1.000.000.000 (el taller usa números de 8 dígitos).
 */
public class RadixSort {

    public static void ordenar(int[] arreglo) {

        int n = arreglo.length;

        if (n <= 1) {
            return;
        }

        int maximo = obtenerMaximo(arreglo);
        int[] salida = new int[n];   // arreglo auxiliar de tamaño n

        // Una pasada por cada dígito del número más grande
        for (int exp = 1; maximo / exp > 0; exp *= 10) {
            ordenarPorDigito(arreglo, salida, exp);
        }
    }

    private static int obtenerMaximo(int[] arreglo) {

        int maximo = arreglo[0];

        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] > maximo) {
                maximo = arreglo[i];
            }
        }

        return maximo;
    }

    // Counting Sort sobre el dígito que indica exp (1 = unidades, 10 = decenas, ...)
    private static void ordenarPorDigito(int[] arreglo, int[] salida, int exp) {

        int n = arreglo.length;
        int[] conteo = new int[10];   // un contador por cada dígito, de 0 a 9

        // 1. Contar cuántos números tienen cada dígito
        for (int i = 0; i < n; i++) {
            conteo[(arreglo[i] / exp) % 10]++;
        }

        // 2. Acumular: conteo[d] pasa a ser la posición final (exclusiva) del dígito d
        for (int d = 1; d < 10; d++) {
            conteo[d] += conteo[d - 1];
        }

        // 3. Recorrer de derecha a izquierda para conservar la estabilidad
        for (int i = n - 1; i >= 0; i--) {
            int digito = (arreglo[i] / exp) % 10;
            salida[conteo[digito] - 1] = arreglo[i];
            conteo[digito]--;
        }

        // 4. Copiar el resultado de la pasada al arreglo original
        for (int i = 0; i < n; i++) {
            arreglo[i] = salida[i];
        }
    }
}