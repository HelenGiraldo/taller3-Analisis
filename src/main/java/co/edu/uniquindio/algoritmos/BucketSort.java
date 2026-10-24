package co.edu.uniquindio.algoritmos;

import java.util.ArrayList;
import java.util.List;

public class BucketSort {

    /** Ordena una lista pequeña (una cubeta) con Insertion Sort. */
    private static void insertionSort(List<Integer> lista) {
        for (int i = 1; i < lista.size(); i++) {
            int clave = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j) > clave) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, clave);
        }
    }

    /**
     * Bucket Sort con n cubetas.
     * Cubeta de un valor v = (v - minimo) * k / (maximo - minimo + 1), con k = n.
     */
    public static void ordenar(int[] arreglo) {
        int n = arreglo.length;
        if (n < 2) {
            return;
        }

        int minimo = arreglo[0];
        int maximo = arreglo[0];
        for (int valor : arreglo) {
            if (valor < minimo) minimo = valor;
            if (valor > maximo) maximo = valor;
        }
        int k = n;                                   // cantidad de cubetas
        long rango = (long) maximo - minimo + 1;

        // 1) Crear las cubetas vacías
        List<List<Integer>> cubetas = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            cubetas.add(new ArrayList<>());
        }

        // 2) Repartir cada valor en su cubeta
        for (int valor : arreglo) {
            int indice = (int) ((long) (valor - minimo) * k / rango);
            cubetas.get(indice).add(valor);
        }

        // 3) Ordenar cada cubeta y copiarla al arreglo en orden
        int posicion = 0;
        for (List<Integer> cubeta : cubetas) {
            insertionSort(cubeta);
            for (int valor : cubeta) {
                arreglo[posicion] = valor;
                posicion++;
            }
        }
    }
}