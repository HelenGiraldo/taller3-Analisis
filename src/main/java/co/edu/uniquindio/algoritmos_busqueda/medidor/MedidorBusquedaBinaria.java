
package co.edu.uniquindio.algoritmos_busqueda.medidor;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaBinaria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class MedidorBusquedaBinaria {

    private static final Path CARPETA_DATOS =
            Path.of("datos", "ordenamiento");

    private static final int[] TAMANOS = {
            1000, 10000, 100000, 1000000
    };

    private static final String[] VARIANTES = {
            "aleatorios", "ascendente", "descendente"
    };

    private static final int CLAVE = -1;
    private static final int REPETICIONES = 5;
    private static final int CALENTAMIENTO = 20000;
    private static final int BUSQUEDAS_POR_MEDICION = 100000;

    // Permite conservar los resultados de las búsquedas.
    private static volatile long resultadoGlobal = 0;

    public static void main(String[] args) throws IOException {

        for (int n : TAMANOS) {
            System.out.println("\nTamaño: " + n);

            for (String variante : VARIANTES) {

                Path archivo = CARPETA_DATOS.resolve(
                        variante + "_" + n + ".txt"
                );

                List<String> lineas = Files.readAllLines(archivo);
                int[] arreglo = new int[lineas.size()];

                for (int i = 0; i < lineas.size(); i++) {
                    arreglo[i] = Integer.parseInt(lineas.get(i).trim());
                }

                // Se ordena antes de medir.
                Arrays.sort(arreglo);

                // Calentamiento de la JVM.
                long resultadosCalentamiento = 0;

                for (int i = 0; i < CALENTAMIENTO; i++) {
                    resultadosCalentamiento +=
                            BusquedaBinaria.buscar(arreglo, CLAVE);
                }

                resultadoGlobal = resultadosCalentamiento;

                double[] tiempos = new double[REPETICIONES];

                // Mediciones.
                for (int r = 0; r < REPETICIONES; r++) {

                    long resultados = 0;
                    long inicio = System.nanoTime();

                    for (int i = 0; i < BUSQUEDAS_POR_MEDICION; i++) {
                        resultados +=
                                BusquedaBinaria.buscar(arreglo, CLAVE);
                    }

                    long fin = System.nanoTime();

                    // Se conserva el resultado fuera del ciclo medido.
                    resultadoGlobal = resultados;

                    tiempos[r] =
                            (fin - inicio) / 1_000_000.0
                                    / BUSQUEDAS_POR_MEDICION;

                    System.out.printf(
                            "%s - Repetición %d: %.6f ms%n",
                            variante, r + 1, tiempos[r]
                    );
                }

                double suma = 0;

                for (double tiempo : tiempos) {
                    suma += tiempo;
                }

                double promedio = suma / REPETICIONES;
                double sumaCuadrados = 0;

                for (double tiempo : tiempos) {
                    sumaCuadrados +=
                            Math.pow(tiempo - promedio, 2);
                }

                double desviacion = Math.sqrt(
                        sumaCuadrados / (REPETICIONES - 1)
                );

                System.out.printf(
                        "Promedio: %.6f ms%n", promedio
                );

                System.out.printf(
                        "Desviación estándar: %.6f ms%n", desviacion
                );
            }
        }
    }
}
