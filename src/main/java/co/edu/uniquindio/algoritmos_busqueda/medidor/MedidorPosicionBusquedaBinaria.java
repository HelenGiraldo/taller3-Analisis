package co.edu.uniquindio.algoritmos_busqueda.medidor;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MedidorPosicionBusquedaBinaria {

    private static final int TAMANO = 1_000_000;
    private static final int REPETICIONES = 5;
    private static final int BUSQUEDAS = 1000;

    public static void main(String[] args) throws IOException {

        Path archivo = Path.of(
                "datos", "ordenamiento",
                "ascendente_" + TAMANO + ".txt"
        );
        List<String> lineas = Files.readAllLines(archivo);
        int[] arreglo = new int[lineas.size()];

        for (int i = 0; i < lineas.size(); i++) {
            arreglo[i] = Integer.parseInt(lineas.get(i).trim());
        }

        if (arreglo.length != TAMANO) {
            System.out.println("Error: el arreglo no tiene un millón de elementos.");
            return;
        }

        int[] claves = {
                arreglo[0],
                arreglo[arreglo.length / 2],
                arreglo[arreglo.length - 1]
        };

        String[] posiciones = {"Inicio", "Centro", "Final"};

        System.out.println("BÚSQUEDA BINARIA - PRUEBA DE POSICIONES");
        System.out.println("Tamaño del arreglo: " + arreglo.length);
        System.out.println();

        for (int p = 0; p < posiciones.length; p++) {

            int clave = claves[p];
            double[] tiempos = new double[REPETICIONES];

            for (int i = 0; i < 100; i++) {
                buscar(arreglo, clave);
            }

            System.out.println(
                    "Posición: " + posiciones[p] + " | Clave: " + clave
            );

            for (int r = 0; r < REPETICIONES; r++) {

                long inicio = System.nanoTime();

                for (int i = 0; i < BUSQUEDAS; i++) {
                    buscar(arreglo, clave);
                }

                long fin = System.nanoTime();

                tiempos[r] =
                        (fin - inicio) / (double) BUSQUEDAS / 1_000_000.0;

                System.out.printf(
                        "  Repetición %d: %.8f ms por búsqueda%n",
                        r + 1, tiempos[r]
                );
            }

            double promedio = 0;

            for (double tiempo : tiempos) {
                promedio += tiempo;
            }

            promedio /= REPETICIONES;

            double sumaCuadrados = 0;

            for (double tiempo : tiempos) {
                sumaCuadrados += Math.pow(tiempo - promedio, 2);
            }

            double desviacion = Math.sqrt(
                    sumaCuadrados / REPETICIONES
            );

            System.out.printf("  Promedio: %.8f ms%n", promedio);
            System.out.printf(
                    "  Desviación estándar: %.8f ms%n%n",
                    desviacion
            );
        }
    }

    private static int buscar(int[] arreglo, int clave) {
        int inicio = 0;
        int fin = arreglo.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            if (arreglo[medio] == clave) {
                return medio;
            }

            if (arreglo[medio] < clave) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }
}
