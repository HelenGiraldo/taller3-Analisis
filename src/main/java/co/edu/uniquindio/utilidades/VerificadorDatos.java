package co.edu.uniquindio.utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VerificadorDatos {

    public static void main(String[] args) {

        int[] tamanos = {
                1_000, 10_000, 100_000, 1_000_000
        };

        try {
            for (int n : tamanos) {

                System.out.println("\nVerificando n = " + n);

                verificarArchivo(
                        Paths.get("datos", "ordenamiento",
                                "aleatorios_" + n + ".txt"),
                        n,
                        "Aleatorios",
                        0

                );



                verificarArchivo(
                        Paths.get("datos", "ordenamiento",
                                "ascendente_" + n + ".txt"),
                        n,
                        "Ascendente",
                        1
                );

                verificarArchivo(
                        Paths.get("datos", "ordenamiento",
                                "descendente_" + n + ".txt"),
                        n,
                        "Descendente",
                        2
                );

                verificarArchivo(
                        Paths.get("datos", "busqueda",
                                "claves_presentes_" + n + ".txt"),
                        500,
                        "Claves presentes",
                        0
                );

                verificarArchivo(
                        Paths.get("datos", "busqueda",
                                "claves_ausentes_" + n + ".txt"),
                        500,
                        "Claves ausentes",
                        0
                );
                verificarClaves(n);
            }

            System.out.println(
                    "\nVerificación de archivos terminada."
            );

        } catch (IOException e) {
            System.out.println(
                    "Error al leer los archivos: " + e.getMessage()
            );
        }
    }

    private static void verificarArchivo(
            Path ruta,
            int cantidadEsperada,
            String nombre,
            int tipoOrden
    ) throws IOException {

        if (!Files.exists(ruta)) {
            System.out.println(
                    nombre + ": ERROR, el archivo no existe."
            );
            return;
        }

        List<String> lineas = Files.readAllLines(ruta);

        if (lineas.size() != cantidadEsperada) {
            System.out.println(
                    nombre + ": ERROR, se esperaban "
                            + cantidadEsperada + " números, pero hay "
                            + lineas.size() + "."
            );
            return;
        }

        int anterior = 0;
        boolean primero = true;

        for (String linea : lineas) {

            int numero;

            try {
                numero = Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println(
                        nombre + ": ERROR, hay un dato inválido."
                );
                return;
            }

            if (numero < 10_000_000 || numero > 99_999_999) {
                System.out.println(
                        nombre + ": ERROR, hay un número fuera del rango."
                );
                return;
            }

            if (!primero && tipoOrden == 1
                    && numero < anterior) {
                System.out.println(
                        nombre + ": ERROR, no está ordenado ascendentemente."
                );
                return;
            }

            if (!primero && tipoOrden == 2
                    && numero > anterior) {
                System.out.println(
                        nombre + ": ERROR, no está ordenado descendentemente."
                );
                return;
            }

            anterior = numero;
            primero = false;
        }

        System.out.println(
                nombre + ": OK (" + lineas.size() + " números)."
        );
    }


    private static void verificarClaves(int n) throws IOException {

        Path rutaDatos = Paths.get(
                "datos", "ordenamiento", "ascendente_" + n + ".txt"
        );

        Path rutaPresentes = Paths.get(
                "datos", "busqueda", "claves_presentes_" + n + ".txt"
        );

        Path rutaAusentes = Paths.get(
                "datos", "busqueda", "claves_ausentes_" + n + ".txt"
        );

        List<String> lineasDatos = Files.readAllLines(rutaDatos);
        List<String> lineasPresentes = Files.readAllLines(rutaPresentes);
        List<String> lineasAusentes = Files.readAllLines(rutaAusentes);

        java.util.Set<Integer> datos = new java.util.HashSet<>();

        for (String linea : lineasDatos) {
            datos.add(Integer.parseInt(linea.trim()));
        }

        for (String linea : lineasPresentes) {
            int clave = Integer.parseInt(linea.trim());

            if (!datos.contains(clave)) {
                System.out.println(
                        "ERROR: una clave presente no está en los datos."
                );
                return;
            }
        }

        for (String linea : lineasAusentes) {
            int clave = Integer.parseInt(linea.trim());

            if (datos.contains(clave)) {
                System.out.println(
                        "ERROR: una clave ausente sí está en los datos."
                );
                return;
            }
        }

        System.out.println(
                "Claves de búsqueda: OK (500 presentes y 500 ausentes)."
        );
    }

}
