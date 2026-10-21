package co.edu.uniquindio.algoritmos;

public class DualPivotQuickSort {

    public static void ordenar(int[] arreglo) {
        ordenar(arreglo, 0, arreglo.length - 1);
    }

    private static void ordenar(int[] arreglo, int bajo, int alto) {

        if (bajo >= alto) {
            return;
        }

        // El pivote izquierdo (primer elemento) debe ser el menor de los dos
        if (arreglo[bajo] > arreglo[alto]) {
            intercambiar(arreglo, bajo, alto);
        }

        int pivoteIzquierdo = arreglo[bajo];   // primer elemento
        int pivoteDerecho = arreglo[alto];     // último elemento

        int menores = bajo + 1;   // donde va el próximo elemento menor al pivote izquierdo
        int mayores = alto - 1;   // donde va el próximo elemento mayor al pivote derecho
        int i = bajo + 1;         // elemento que se está revisando

        while (i <= mayores) {

            if (arreglo[i] < pivoteIzquierdo) {
                intercambiar(arreglo, i, menores);
                menores++;
                i++;
            } else if (arreglo[i] > pivoteDerecho) {
                intercambiar(arreglo, i, mayores);
                mayores--;
            } else {
                i++;
            }
        }

        // Los pivotes se colocan en su posición final
        menores--;
        mayores++;
        intercambiar(arreglo, bajo, menores);
        intercambiar(arreglo, alto, mayores);

        // Se ordenan las tres zonas
        ordenar(arreglo, bajo, menores - 1);              // menores que el pivote izquierdo
        ordenar(arreglo, menores + 1, mayores - 1);       // entre los dos pivotes
        ordenar(arreglo, mayores + 1, alto);              // mayores que el pivote derecho
    }

    private static void intercambiar(int[] arreglo, int a, int b) {
        int temporal = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = temporal;
    }
}