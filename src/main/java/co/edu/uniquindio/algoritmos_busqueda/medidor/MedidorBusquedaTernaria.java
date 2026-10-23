package co.edu.uniquindio.algoritmos_busqueda.medidor;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedidorBusquedaTernaria {

    private static final int[] TAMANNOS = {
            1000, 10000, 100000, 1000000
    };

    private static final int REPETICIONES = 5;
    private static final int CLAVE = -1;

    public static int busquedaTernaria(int[] arreglo, int clave) {
        int izquierda = 0;
        int derecha = arreglo.length - 1;

        while (izquierda <= derecha) {
            int tercio = (derecha - izquierda) / 3;
            int medio1 = izquierda + tercio;
            int medio2 = derecha - tercio;

            if (arreglo[medio1] == clave) {
                return medio1;
            }

            if (arreglo[medio2] == clave) {
                return medio2;
            }

            if (clave < arreglo[medio1]) {
                derecha = medio1 - 1;
            } else if (clave > arreglo[medio2]) {
                izquierda = medio2 + 1;
            } else {
                izquierda = medio1 + 1;
                derecha = medio2 - 1;
            }
        }

        return -1;
    }

    private static int[] cargarArreglo(
            int tamano, String variante) throws Exception {

        Path ruta = Path.of(
                "datos", variante + "_" + tamano + ".txt"
        );

        List<String> lineas = Files.readAllLines(ruta);
        List<Integer> valores = new ArrayList<>();

        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                valores.add(Integer.parseInt(linea.trim()));
            }
        }

        Collections.sort(valores);

        int[] arreglo = new int[valores.size()];

        for (int i = 0; i < valores.size(); i++) {
            arreglo[i] = valores.get(i);
        }

        return arreglo;
    }

    private static double medirBusqueda(
            int[] arreglo, int clave) {

        long inicio = System.nanoTime();
        int resultado = busquedaTernaria(arreglo, clave);
        long fin = System.nanoTime();

        if (resultado != -1) {
            throw new IllegalStateException(
                    "La clave -1 no debería estar presente."
            );
        }

        return (fin - inicio) / 1_000_000.0;
    }

    public static void main(String[] args) throws Exception {
        for (int tamano : TAMANNOS) {
            for (String variante : new String[]{
                    "aleatorios", "ascendente", "descendente"}) {

                int[] arreglo = cargarArreglo(tamano, variante);

                // Calentamiento previo
                for (int i = 0; i < 1000; i++) {
                    busquedaTernaria(arreglo, CLAVE);
                }

                double[] tiempos = new double[REPETICIONES];
                double suma = 0;

                for (int i = 0; i < REPETICIONES; i++) {
                    tiempos[i] = medirBusqueda(arreglo, CLAVE);
                    suma += tiempos[i];
                }

                double promedio = suma / REPETICIONES;
                double sumaCuadrados = 0;

                for (double tiempo : tiempos) {
                    sumaCuadrados +=
                            Math.pow(tiempo - promedio, 2);
                }

                double desviacion = Math.sqrt(
                        sumaCuadrados / REPETICIONES
                );

                System.out.println("\nVariante: " + variante);
                System.out.println("Tamaño: " + tamano);

                for (int i = 0; i < REPETICIONES; i++) {
                    System.out.printf(
                            "Repetición %d: %.6f ms%n",
                            i + 1, tiempos[i]
                    );
                }

                System.out.printf(
                        "Promedio: %.6f ms%n", promedio
                );
                System.out.printf(
                        "Desviación estándar: %.6f ms%n",
                        desviacion
                );
                System.out.println(
                        "Resultado de búsqueda: -1"
                );
            }
        }
    }
}
