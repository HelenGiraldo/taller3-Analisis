package co.edu.uniquindio.algoritmos_busqueda.medidor;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaLineal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MedidorBusquedaLineal {

    private static final int[] TAMANOS = {
            1000, 10000, 100000, 1000000
    };

    private static final String[] VARIANTES = {
            "aleatorios", "ascendente", "descendente"
    };

    private static final int REPETICIONES = 5;
    private static final int CALENTAMIENTOS = 1;

    private static final Path CARPETA_DATOS =
            Path.of("datos", "ordenamiento");

    public static void main(String[] args) throws Exception {

        for (int n : TAMANOS) {
            for (String variante : VARIANTES) {

                Path ruta = CARPETA_DATOS.resolve(
                        variante + "_" + n + ".txt"
                );

                System.out.println("\nVariante: " + variante);
                System.out.println("Tamaño: " + n);

                if (!Files.exists(ruta)) {
                    System.out.println(
                            "Archivo no encontrado: " + ruta.toAbsolutePath()
                    );
                    continue;
                }

                int[] arreglo = leerDatos(ruta);

                if (arreglo.length != n) {
                    System.out.println(
                            "Error: se esperaban " + n
                                    + " elementos, pero se encontraron "
                                    + arreglo.length + "."
                    );
                    continue;
                }

                medir(arreglo);
            }
        }
    }

    private static int[] leerDatos(Path ruta) throws Exception {

        List<String> lineas = Files.readAllLines(ruta);

        return lineas.stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static void medir(int[] arreglo) {

        double[] tiempos = new double[REPETICIONES];

        // Buscar un valor que no esté en los datos.
        // El rango del taller va de 10.000.000 a 99.999.999.
        int objetivo = -1;

        // Calentamiento: no se incluye en las mediciones.
        for (int i = 0; i < CALENTAMIENTOS; i++) {
            int resultado = BusquedaLineal.buscar(arreglo, objetivo);

            if (resultado != -1) {
                throw new IllegalStateException(
                        "El elemento debía ser inexistente."
                );
            }
        }

        // Cinco mediciones independientes.
        for (int repeticion = 0;
             repeticion < REPETICIONES;
             repeticion++) {

            long inicio = System.nanoTime();

            int resultado = BusquedaLineal.buscar(arreglo, objetivo);

            long fin = System.nanoTime();

            double tiempoMs = (fin - inicio) / 1_000_000.0;
            tiempos[repeticion] = tiempoMs;

            // Verificar después de detener el cronómetro.
            if (resultado != -1) {
                throw new IllegalStateException(
                        "La búsqueda debía devolver -1."
                );
            }

            System.out.printf(
                    "Repetición %d: %.4f ms%n",
                    repeticion + 1, tiempoMs
            );
        }

        double promedio = calcularPromedio(tiempos);
        double desviacion = calcularDesviacion(tiempos, promedio);

        System.out.printf("Promedio: %.4f ms%n", promedio);
        System.out.printf(
                "Desviación estándar: %.4f ms%n", desviacion
        );
        System.out.println("Verificación: OK");
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