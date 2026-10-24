package co.edu.uniquindio.algoritmos.pruebas;

import co.edu.uniquindio.algoritmos.RadixSort;

import java.util.Arrays;
import java.util.Random;

public class PruebaRadixSort {

    public static void main(String[] args) {

        probar(new int[]{});
        probar(new int[]{7});
        probar(new int[]{9, 3});
        probar(new int[]{5, 5, 5, 5, 5});
        probar(new int[]{3, 1, 3, 2, 1, 3});
        probar(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        probar(new int[]{8, 7, 6, 5, 4, 3, 2, 1});
        probar(new int[]{329, 457, 657, 839, 436, 720, 355});
        probar(new int[]{45, 12, 78, 3, 99, 23, 56, 1, 67, 34});

        // Prueba con números de 8 dígitos, como los del taller
        Random aleatorio = new Random(42);
        int[] datos = new int[1000];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = 10_000_000 + aleatorio.nextInt(90_000_000);
        }
        probar(datos);
    }

    private static void probar(int[] arreglo) {

        boolean pequeno = arreglo.length <= 10;

        if (pequeno) {
            System.out.println("Antes:   " + Arrays.toString(arreglo));
        }

        int[] esperado = arreglo.clone();
        Arrays.sort(esperado);

        RadixSort.ordenar(arreglo);

        if (pequeno) {
            System.out.println("Después: " + Arrays.toString(arreglo));
        } else {
            System.out.println("Arreglo de " + arreglo.length + " elementos");
        }

        if (estaOrdenado(arreglo) && Arrays.equals(arreglo, esperado)) {
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