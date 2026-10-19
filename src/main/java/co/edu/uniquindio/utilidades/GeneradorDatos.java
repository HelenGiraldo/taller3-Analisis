package co.edu.uniquindio.utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorDatos {

    // Tamaños exigidos para los experimentos
    private static final int[] TAMANOS = {
            1_000, 10_000, 100_000, 1_000_000
    };

    // Rango de los números: ambos extremos están incluidos
    private static final int MINIMO = 10_000_000;
    private static final int MAXIMO = 99_999_999;

    // Semilla fija para poder repetir la generación
    private static final long SEMILLA = 20261019L;

    // Cantidad de claves de búsqueda
    //para asi lograr las 1000 busquedas en total que se piden
    private static final int CANTIDAD_CLAVES = 500;

    public static void main(String[] args) {

        try {
            Path carpetaOrdenamiento =
                    Paths.get("datos", "ordenamiento");

            Path carpetaBusqueda =
                    Paths.get("datos", "busqueda");

            Files.createDirectories(carpetaOrdenamiento);
            Files.createDirectories(carpetaBusqueda);

            for (int n : TAMANOS) {

                System.out.println(
                        "Generando datos para n = " + n
                );

                Random random = new Random(SEMILLA + n);

                // 1. Generar los números aleatorios
                int[] aleatorios = new int[n];

                for (int i = 0; i < n; i++) {
                    aleatorios[i] =
                            random.nextInt(MAXIMO - MINIMO + 1)
                                    + MINIMO;
                }

                // 2. Guardar los números aleatorios
                escribirArchivo(
                        carpetaOrdenamiento.resolve(
                                "aleatorios_" + n + ".txt"
                        ),
                        aleatorios
                );

                // 3. Crear y guardar la versión ascendente
                int[] ascendentes = aleatorios.clone();
                Arrays.sort(ascendentes);

                escribirArchivo(
                        carpetaOrdenamiento.resolve(
                                "ascendente_" + n + ".txt"
                        ),
                        ascendentes
                );

                // 4. Crear y guardar la versión descendente
                int[] descendentes = ascendentes.clone();

                for (int izquierda = 0;
                     izquierda < descendentes.length / 2;
                     izquierda++) {

                    int derecha =
                            descendentes.length - 1 - izquierda;

                    int temporal = descendentes[izquierda];

                    descendentes[izquierda] =
                            descendentes[derecha];

                    descendentes[derecha] = temporal;
                }

                escribirArchivo(
                        carpetaOrdenamiento.resolve(
                                "descendente_" + n + ".txt"
                        ),
                        descendentes
                );

                // 5. Generar 500 claves presentes y 500 ausentes
                generarClaves(
                        ascendentes,
                        random,
                        carpetaBusqueda,
                        n
                );

                System.out.println(
                        "Datos y claves guardados para n = " + n
                );
            }

            System.out.println(
                    "\nGeneración terminada correctamente."
            );

        } catch (IOException e) {
            System.out.println(
                    "Error al crear los archivos: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }

    private static void escribirArchivo(
            Path ruta,
            int[] numeros
    ) throws IOException {

        try (BufferedWriter escritor =
                     Files.newBufferedWriter(
                             ruta,
                             StandardCharsets.UTF_8
                     )) {

            for (int numero : numeros) {
                escritor.write(Integer.toString(numero));
                escritor.newLine();
            }
        }
    }

    private static void generarClaves(
            int[] ascendentes,
            Random random,
            Path carpetaBusqueda,
            int n
    ) throws IOException {

        Set<Integer> clavesPresentes = new HashSet<>();
        Set<Integer> clavesAusentes = new HashSet<>();

        // Obtener 500 claves distintas que sí existan
        while (clavesPresentes.size() < CANTIDAD_CLAVES) {

            int posicion = random.nextInt(ascendentes.length);
            int clave = ascendentes[posicion];

            clavesPresentes.add(clave);
        }

        // Obtener 500 claves distintas que no existan
        while (clavesAusentes.size() < CANTIDAD_CLAVES) {

            int clave =
                    random.nextInt(MAXIMO - MINIMO + 1)
                            + MINIMO;

            boolean existe =
                    Arrays.binarySearch(ascendentes, clave) >= 0;

            if (!existe) {
                clavesAusentes.add(clave);
            }
        }

        escribirArchivo(
                carpetaBusqueda.resolve(
                        "claves_presentes_" + n + ".txt"
                ),
                convertirAArreglo(clavesPresentes)
        );

        escribirArchivo(
                carpetaBusqueda.resolve(
                        "claves_ausentes_" + n + ".txt"
                ),
                convertirAArreglo(clavesAusentes)
        );
    }

    private static int[] convertirAArreglo(Set<Integer> conjunto) {

        int[] resultado = new int[conjunto.size()];
        int i = 0;

        for (int numero : conjunto) {
            resultado[i] = numero;
            i++;
        }

        return resultado;
    }
}
