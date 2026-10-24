package co.edu.uniquindio.algoritmos.pruebas;

import co.edu.uniquindio.algoritmos.BucketSort;

import java.util.Arrays;
import java.util.Random;

public class PruebaBucketSort {

    static void probar(String nombre, int[] arreglo) {
        int[] esperado = arreglo.clone();
        Arrays.sort(esperado);
        BucketSort.ordenar(arreglo);
        if (Arrays.equals(arreglo, esperado)) {
            System.out.println("OK      " + nombre);
        } else {
            System.out.println("FALLÓ   " + nombre);
        }
    }

    public static void main(String[] args) {
        probar("ejemplo del informe", new int[]{34, 12, 49, 17, 41, 38, 15});
        probar("arreglo vacío", new int[]{});
        probar("un solo elemento", new int[]{55555555});
        probar("dos elementos", new int[]{90000000, 10000000});

        int[] iguales = new int[50];
        Arrays.fill(iguales, 20000000);
        probar("todos iguales", iguales);
        probar("con repetidos", new int[]{30000000, 20000000, 30000000, 20000000, 10000000});

        Random random = new Random();
        int[] aleatorio = new int[1000];
        for (int i = 0; i < aleatorio.length; i++) {
            aleatorio[i] = 10_000_000 + random.nextInt(90_000_000);
        }
        probar("aleatorio 1000", aleatorio.clone());

        int[] ascendente = aleatorio.clone();
        Arrays.sort(ascendente);
        probar("ascendente 1000", ascendente.clone());

        int[] descendente = new int[ascendente.length];
        for (int i = 0; i < ascendente.length; i++) {
            descendente[i] = ascendente[ascendente.length - 1 - i];
        }
        probar("descendente 1000", descendente);

        int[] juntos = new int[1000];
        for (int i = 0; i < juntos.length; i++) {
            juntos[i] = 10_000_000 + random.nextInt(51);
        }
        probar("valores muy juntos", juntos);
    }
}