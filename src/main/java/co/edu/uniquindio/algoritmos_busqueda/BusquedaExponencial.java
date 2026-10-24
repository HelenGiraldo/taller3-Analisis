package co.edu.uniquindio.algoritmos_busqueda;

/**
 * Búsqueda Exponencial sobre un arreglo ordenado de forma ascendente.
 * Primero encuentra un rango donde puede estar la clave duplicando un índice (1, 2, 4, 8, ...)
 * mientras el valor en ese índice sea menor que la clave; luego aplica búsqueda binaria
 * solo dentro de ese rango [i/2, min(i, n-1)].
 * Devuelve la posición de la clave, o -1 si no está.
 * Fuente: GeeksforGeeks - "Exponential Search".
 */
public class BusquedaExponencial {

    public static int buscar(int[] arreglo, int clave) {

        int n = arreglo.length;

        if (n == 0) {
            return -1;
        }

        // 1. Si la clave está en la primera posición, termina de inmediato
        if (arreglo[0] == clave) {
            return 0;
        }

        // 2. Encontrar el rango: duplicar el índice mientras el valor sea menor que la clave
        long indice = 1;

        while (indice < n && arreglo[(int) indice] < clave) {
            indice = indice * 2;
        }

        // 3. Búsqueda binaria en [indice / 2, min(indice, n - 1)]
        int inicio = (int) (indice / 2);
        int fin = (int) Math.min(indice, n - 1);

        return busquedaBinaria(arreglo, inicio, fin, clave);
    }

    private static int busquedaBinaria(int[] arreglo, int inicio, int fin, int clave) {

        while (inicio <= fin) {

            int medio = inicio + (fin - inicio) / 2;

            if (arreglo[medio] == clave) {
                return medio;
            }

            if (arreglo[medio] < clave) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }
}