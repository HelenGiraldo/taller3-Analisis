package co.edu.uniquindio.algoritmos.medidor;

import co.edu.uniquindio.algoritmos.InsertionSort;
import co.edu.uniquindio.algoritmos.utilidades.LectorDatos;

import java.io.IOException;

public class MedidorInsertionSort {

    private static final int REPETICIONES = 5;

    public static void main(String[] args) {

        int n = 1_000_000;

        String[] variantes = {
                "aleatorios",
                "ascendente",
                "descendente"
        };

        for (String variante : variantes) {

            String ruta = "datos/ordenamiento/"
                    + variante + "_" + n + ".txt";

            try {
                int[] datosOriginales =
                        LectorDatos.leerArchivo(ruta);

                System.out.println("\nVariante: " + variante);
                System.out.println("Tamaño: " + n);

                // Calentamientos de Java: no cuentan como mediciones
                for (int i = 0; i < 10; i++) {
                    int[] calentamiento = datosOriginales.clone();
                    InsertionSort.ordenar(calentamiento);
                }

                // Aquí guardamos los cinco tiempos
                double[] tiempos = new double[REPETICIONES];

                for (int r = 0; r < REPETICIONES; r++) {

                    // La copia se hace antes de iniciar el cronómetro
                    int[] copia = datosOriginales.clone();

                    long inicio = System.nanoTime();

                    InsertionSort.ordenar(copia);

                    long fin = System.nanoTime();

                    // Convertimos nanosegundos a milisegundos
                    tiempos[r] = (fin - inicio) / 1_000_000.0;

                    // Verificamos el resultado después de medir
                    if (!estaOrdenado(copia)) {
                        System.out.println(
                                "ERROR: el arreglo no quedó ordenado."
                        );
                        return;
                    }

                    System.out.printf(
                            "Repetición %d: %.4f ms%n",
                            r + 1,
                            tiempos[r]
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

            } catch (IOException e) {
                System.out.println(
                        "Error al leer " + ruta + ": "
                                + e.getMessage()
                );
                return;
            }
        }
    }

    private static boolean estaOrdenado(int[] arreglo) {

        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] < arreglo[i - 1]) {
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
            double[] tiempos,
            double promedio
    ) {

        double sumaCuadrados = 0;

        for (double tiempo : tiempos) {
            double diferencia = tiempo - promedio;
            sumaCuadrados += diferencia * diferencia;
        }

        return Math.sqrt(sumaCuadrados / tiempos.length);
    }
}
