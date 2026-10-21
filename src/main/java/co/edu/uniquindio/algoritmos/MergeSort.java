
package co.edu.uniquindio.algoritmos;

public class MergeSort {

    public static void ordenar(int[] arreglo) {
        if (arreglo.length < 2) {
            return;
        }

        int[] auxiliar = new int[arreglo.length];
        ordenar(arreglo, auxiliar, 0, arreglo.length - 1);
    }

    private static void ordenar(
            int[] arreglo,
            int[] auxiliar,
            int inicio,
            int fin) {

        if (inicio < fin) {
            int medio = inicio + (fin - inicio) / 2;

            ordenar(arreglo, auxiliar, inicio, medio);
            ordenar(arreglo, auxiliar, medio + 1, fin);

            mezclar(arreglo, auxiliar, inicio, medio, fin);
        }
    }

    private static void mezclar(
            int[] arreglo,
            int[] auxiliar,
            int inicio,
            int medio,
            int fin) {

        for (int i = inicio; i <= fin; i++) {
            auxiliar[i] = arreglo[i];
        }

        int izquierda = inicio;
        int derecha = medio + 1;

        for (int i = inicio; i <= fin; i++) {
            if (izquierda > medio) {
                arreglo[i] = auxiliar[derecha++];
            } else if (derecha > fin) {
                arreglo[i] = auxiliar[izquierda++];
            } else if (auxiliar[izquierda] <= auxiliar[derecha]) {
                arreglo[i] = auxiliar[izquierda++];
            } else {
                arreglo[i] = auxiliar[derecha++];
            }
        }
    }
}
