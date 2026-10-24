package co.edu.uniquindio.algoritmos_busqueda;

/**
 * Búsqueda por Interpolación sobre un arreglo ordenado de forma ascendente.
 * En lugar de mirar siempre el punto medio (como la búsqueda binaria), estima la posición
 * de la clave suponiendo que los valores están repartidos de forma uniforme:
 *
 *     pos = inicio + (clave - arreglo[inicio]) * (fin - inicio) / (arreglo[fin] - arreglo[inicio])
 *
 * Devuelve la posición de la clave, o -1 si no está.
 * Fuente: GeeksforGeeks - "Interpolation Search".
 */
public class BusquedaPorInterpolacion {

    public static int buscar(int[] arreglo, int clave) {

        int inicio = 0;
        int fin = arreglo.length - 1;

        // La clave solo puede estar mientras quede rango y esté entre el menor y el mayor valor del rango
        while (inicio <= fin && clave >= arreglo[inicio] && clave <= arreglo[fin]) {

            // Todos los valores del rango son iguales: evita dividir entre cero
            if (arreglo[inicio] == arreglo[fin]) {
                return (arreglo[inicio] == clave) ? inicio : -1;
            }

            // Posición estimada (long para que la multiplicación no desborde un int)
            long numerador = ((long) clave - arreglo[inicio]) * (fin - inicio);
            long denominador = (long) arreglo[fin] - arreglo[inicio];
            int posicion = inicio + (int) (numerador / denominador);

            if (arreglo[posicion] == clave) {
                return posicion;
            }

            if (arreglo[posicion] < clave) {
                inicio = posicion + 1;      // la clave está a la derecha de la posición estimada
            } else {
                fin = posicion - 1;         // la clave está a la izquierda
            }
        }

        return -1;
    }
}