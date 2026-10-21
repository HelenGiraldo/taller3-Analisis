package co.edu.uniquindio.algoritmos.pruebas;

import co.edu.uniquindio.algoritmos.DualPivotQuickSort;

import java.util.Arrays;

public class PruebaDualPivotQuickSort {

    public static void main(String[] args) {

        probar(new int[]{});
        probar(new int[]{7});
        probar(new int[]{9, 3});
        probar(new int[]{5, 5, 5, 5, 5});
        probar(new int[]{3, 1, 3, 2, 1, 3});
        probar(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        probar(new int[]{8, 7, 6, 5, 4, 3, 2, 1});
        probar(new int[]{45, 12, 78, 3, 99, 23, 56, 1, 67, 34});
    }

    private static void probar(int[] arreglo) {

        System.out.println("Antes:   " + Arrays.toString(arreglo));

        DualPivotQuickSort.ordenar(arreglo);

        System.out.println("Después: " + Arrays.toString(arreglo));

        if (estaOrdenado(arreglo)) {
            System.out.println("Verificación: OK");
        } else {
            System.out.println("Verificación: FALLA");
        }

        System.out.println();
    }

    private static boolean estaOrdenado(int[] arreglo) {

        for (int i = 0; i < arreglo.length - 1; i++) {
            if (arreglo[i] > arreglo[i + 1]) {
                return false;
            }
        }

        return true;
    }
}