package co.edu.uniquindio.algoritmos.medidor;


import co.edu.uniquindio.algoritmos.HeapSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MedidorHeapSort {

    private static final int[] TAMANOS = {
            1_000, 10_000, 100_000, 1_000_000
    };

    private static final String[] VARIANTES = {
            "aleatorios", "ascendente", "descendente"
    };

    private static final int REPETICIONES = 5;
    private static final int CALENTAMIENTOS = 3;

    public static void main(String[] args) throws IOException {

        // Calentar la JVM antes de medir
        int[] datosCalentamiento =
                cargarDatos("aleatorios", 1_000);

        for (int i = 0; i < CALENTAMIENTOS; i++) {
            int[] copia = datosCalentamiento.clone();
            HeapSort.ordenar(copia);
        }

        // Medir todas las combinaciones
        for (int n : TAMANOS) {
            for (String variante : VARIANTES) {

                int[] datos = cargarDatos(variante, n);
                double[] tiempos = new double[REPETICIONES];

                System.out.println("\nVariante: " + variante);
                System.out.println("Tamaño: " + n);

                for (int r = 0; r < REPETICIONES; r++) {

                    // Copiar antes de iniciar el cronómetro
                    int[] copia = datos.clone();

                    long inicio = System.nanoTime();

                    HeapSort.ordenar(copia);

                    long fin = System.nanoTime();

                    // Convertir nanosegundos a milisegundos
                    tiempos[r] = (fin - inicio) / 1_000_000.0;

                    // Verificar que el arreglo quedó ordenado
                    if (!estaOrdenado(copia)) {
                        throw new IllegalStateException(
                                "El arreglo no quedó ordenado."
                        );
                    }

                    System.out.printf(
                            "Repetición %d: %.4f ms%n",
                            r + 1, tiempos[r]
                    );
                }

                double promedio = calcularPromedio(tiempos);
                double desviacion =
                        calcularDesviacion(tiempos, promedio);

                System.out.printf(
                        "Promedio: %.4f ms%n", promedio
                );

                System.out.printf(
                        "Desviación estándar: %.4f ms%n",
                        desviacion
                );

                System.out.println("Verificación: OK");
            }
        }
    }

    private static int[] cargarDatos(
            String variante, int n) throws IOException {

        Path ruta = Path.of(
                "datos", "ordenamiento",
                variante + "_" + n + ".txt"
        );

        List<Integer> numeros = new ArrayList<>(n);

        try (BufferedReader lector =
                     Files.newBufferedReader(ruta)) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();

                if (!linea.isEmpty()) {
                    numeros.add(Integer.parseInt(linea));
                }
            }
        }

        if (numeros.size() != n) {
            throw new IllegalStateException(
                    "Se esperaban " + n + " elementos, pero se leyeron "
                            + numeros.size() + " desde " + ruta
            );
        }

        int[] arreglo = new int[n];

        for (int i = 0; i < n; i++) {
            arreglo[i] = numeros.get(i);
        }

        return arreglo;
    }

    private static boolean estaOrdenado(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i - 1] > arreglo[i]) {
                return false;
            }
        }

        return true;
    }

    private static double calcularPromedio(double[] tiempos) {
        double suma = 0;

        for (double tiempo : tiempos) {
            suma += tiempo;
        }

        return suma / tiempos.length;
    }

    private static double calcularDesviacion(
            double[] tiempos, double promedio) {

        double suma = 0;

        for (double tiempo : tiempos) {
            suma += Math.pow(tiempo - promedio, 2);
        }

        return Math.sqrt(suma / tiempos.length);
    }
}
