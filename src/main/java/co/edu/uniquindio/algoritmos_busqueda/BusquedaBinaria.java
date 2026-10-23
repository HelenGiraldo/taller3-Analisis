package co.edu.uniquindio.algoritmos_busqueda;


public class BusquedaBinaria {

    public static int buscar(int[] arreglo, int clave) {
        int primero = 0;
        int ultimo = arreglo.length - 1;

        while (primero <= ultimo) {
            int medio = (primero + ultimo) / 2;

            if (arreglo[medio] == clave) {
                return medio;
            } else if (arreglo[medio] < clave) {
                primero = medio + 1;
            } else {
                ultimo = medio - 1;
            }
        }

        return -1;
    }
}
