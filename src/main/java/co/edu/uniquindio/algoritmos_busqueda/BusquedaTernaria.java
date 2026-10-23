package co.edu.uniquindio.algoritmos_busqueda;

public class BusquedaTernaria {

    public static int buscar(int[] arreglo, int clave) {
        int izquierda = 0;
        int derecha = arreglo.length - 1;

        while (izquierda <= derecha) {
            int tercio = (derecha - izquierda) / 3;
            int medio1 = izquierda + tercio;
            int medio2 = derecha - tercio;

            if (arreglo[medio1] == clave) {
                return medio1;
            }

            if (arreglo[medio2] == clave) {
                return medio2;
            }

            if (clave < arreglo[medio1]) {
                derecha = medio1 - 1;
            } else if (clave > arreglo[medio2]) {
                izquierda = medio2 + 1;
            } else {
                izquierda = medio1 + 1;
                derecha = medio2 - 1;
            }
        }

        return -1;
    }
}
