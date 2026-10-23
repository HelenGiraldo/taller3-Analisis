package co.edu.uniquindio.algoritmos_busqueda;

public class BusquedaLineal {

    public static int buscar(int[] arreglo, int objetivo) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == objetivo) {
                return i;
            }
        }

        return -1;
    }
}