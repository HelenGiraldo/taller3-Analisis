package co.edu.uniquindio.algoritmos_busqueda;

/**
 * Búsqueda por Saltos (Jump Search) sobre un arreglo ordenado de forma ascendente.
 * Se avanza en bloques de tamaño raíz(n) hasta encontrar el bloque que podría
 * contener la clave, y dentro de ese bloque se busca de forma lineal.
 * Devuelve la posición de la clave, o -1 si no está.
 * Fuente: GeeksforGeeks - "Jump Search".
 */
public class BusquedaPorSaltos {

    public static int buscar(int[] arreglo, int clave) {

        int n = arreglo.length;

        if (n == 0) {
            return -1;
        }

        int salto = (int) Math.sqrt(n);   // tamaño del bloque
        int anterior = 0;                 // inicio del bloque actual
        int limite = Math.min(salto, n);  // fin (exclusivo) del bloque actual

        // 1. Saltar de bloque en bloque mientras el último elemento del bloque sea menor que la clave
        while (arreglo[limite - 1] < clave) {

            anterior = limite;
            limite = Math.min(limite + salto, n);

            if (anterior >= n) {
                return -1;                // se salió del arreglo: la clave es mayor que todos
            }
        }

        // 2. Búsqueda lineal dentro del bloque encontrado
        while (anterior < limite && arreglo[anterior] < clave) {
            anterior++;
        }

        // 3. Verificar si se encontró la clave
        if (anterior < limite && arreglo[anterior] == clave) {
            return anterior;
        }

        return -1;
    }
}