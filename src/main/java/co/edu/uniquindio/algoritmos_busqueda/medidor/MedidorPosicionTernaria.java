package co.edu.uniquindio.algoritmos_busqueda.medidor;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaTernaria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MedidorPosicionTernaria {

    private static final int TAMANO = 1_000_000;
    private static final int REPETICIONES = 5;
    private static final int BUSQUEDAS = 1000;

    private static int[] cargarArreglo() throws IOException {
        Path ruta = Path.of(
                "datos", "ordenamiento", "ascendente_" + TAMANO + ".txt"
        );

        List<String> lineas = Files.readAllLines(ruta);

        int[] arreglo = lineas.stream()
                .filter(linea -> !linea.trim().isEmpty())
                .mapToInt(linea -> Integer.parseInt(linea.trim()))
                .toArray();

        if (arreglo.length != TAMANO) {
            throw new IllegalStateException(
                    "Se esperaban " + TAMANO + " elementos en "
                            + ruta.toAbsolutePath() + ", pero se encontraron "
                            + arreglo.length + "."
            );
        }

        return arreglo;
    }

    private static double medir(int[] arreglo, int clave) {
        long inicio = System.nanoTime();
        int resultado = -1;

        for (int i = 0; i < BUSQUEDAS; i++) {
            resultado = BusquedaTernaria.buscar(arreglo, clave);
        }

        long fin = System.nanoTime();

        if (resultado == -1) {
            throw new IllegalStateException("No se encontró la clave.");
        }

        return (fin - inicio) / (double) BUSQUEDAS / 1_000_000.0;
    }

    public static void main(String[] args) throws IOException {
        int[] arreglo = cargarArreglo();

        int[] claves = {
                arreglo[0],
                arreglo[arreglo.length / 2],
                arreglo[arreglo.length - 1]
        };

        String[] posiciones = {"Inicio", "Centro", "Final"};

        for (int p = 0; p < posiciones.length; p++) {
            double[] tiempos = new double[REPETICIONES];
            double suma = 0;

            // Calentamiento antes de medir esta posición
            for (int i = 0; i < 10000; i++) {
                BusquedaTernaria.buscar(arreglo, claves[p]);
            }

            for (int i = 0; i < REPETICIONES; i++) {
                tiempos[i] = medir(arreglo, claves[p]);
                suma += tiempos[i];
            }

            double promedio = suma / REPETICIONES;
            double sumaCuadrados = 0;

            for (double tiempo : tiempos) {
                sumaCuadrados += Math.pow(tiempo - promedio, 2);
            }

            double desviacion = Math.sqrt(
                    sumaCuadrados / REPETICIONES
            );

            System.out.println("\nPosición: " + posiciones[p]);
            System.out.println("Clave: " + claves[p]);

            for (int i = 0; i < REPETICIONES; i++) {
                System.out.printf(
                        "Repetición %d: %.8f ms%n",
                        i + 1, tiempos[i]
                );
            }

            System.out.printf("Promedio: %.8f ms%n", promedio);
            System.out.printf(
                    "Desviación estándar: %.8f ms%n", desviacion
            );
        }
    }
}
