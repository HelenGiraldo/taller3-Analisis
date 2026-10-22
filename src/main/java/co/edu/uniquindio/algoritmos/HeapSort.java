package co.edu.uniquindio.algoritmos;


public class HeapSort {

    public static void ordenar(int[] arreglo) {
        int n = arreglo.length;

        // Construir el Max Heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arreglo, n, i);
        }

        // Extraer los elementos del heap
        for (int i = n - 1; i > 0; i--) {

            // Mover la raíz (máximo) al final
            int temporal = arreglo[0];
            arreglo[0] = arreglo[i];
            arreglo[i] = temporal;

            // Restaurar el Max Heap
            heapify(arreglo, i, 0);
        }
    }

    private static void heapify(int[] arreglo, int n, int i) {
        int mayor = i;
        int izquierdo = 2 * i + 1;
        int derecho = 2 * i + 2;

        // Comprobar si el hijo izquierdo es mayor
        if (izquierdo < n && arreglo[izquierdo] > arreglo[mayor]) {
            mayor = izquierdo;
        }

        // Comprobar si el hijo derecho es mayor
        if (derecho < n && arreglo[derecho] > arreglo[mayor]) {
            mayor = derecho;
        }

        // Si un hijo es mayor, intercambiar y continuar
        if (mayor != i) {
            int temporal = arreglo[i];
            arreglo[i] = arreglo[mayor];
            arreglo[mayor] = temporal;

            heapify(arreglo, n, mayor);
        }
    }
}
