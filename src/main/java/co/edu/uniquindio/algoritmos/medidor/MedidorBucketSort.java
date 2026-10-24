package co.edu.uniquindio.algoritmos.medidor;

import co.edu.uniquindio.algoritmos.BucketSort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MedidorBucketSort {

    static final String ALGORITMO = "Bucket Sort";
    static final String LENGUAJE = "Java";
    static final int REPETICIONES = 5;
    static final int[] TAMANOS = {1_000, 10_000, 100_000, 1_000_000};
    static final String[] VARIANTES = {"aleatorios", "ascendente", "descendente"};

    static final String ENCABEZADO = "algoritmo,lenguaje,variante,n,rep1_ms,rep2_ms,rep3_ms,"
            + "rep4_ms,rep5_ms,promedio_ms,desviacion_ms,estado,verificacion";

    static Path raiz;
    static Path carpetaDatos;
    static Path archivoCsv;

    // Sube de carpeta hasta encontrar la que contiene "datos"
    static Path encontrarRaiz() {
        Path carpeta = Path.of("").toAbsolutePath();
        while (!Files.isDirectory(carpeta.resolve("datos"))) {
            carpeta = carpeta.getParent();
            if (carpeta == null) {
                throw new IllegalStateException("No se encontró la carpeta 'datos'");
            }
        }
        return carpeta;
    }

    static int[] leerArchivo(Path ruta) throws IOException {
        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        List<Integer> numeros = new ArrayList<>();
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                numeros.add(Integer.parseInt(linea.trim()));
            }
        }
        int[] datos = new int[numeros.size()];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = numeros.get(i);
        }
        return datos;
    }

    static boolean estaOrdenado(int[] arreglo) {
        for (int i = 0; i < arreglo.length - 1; i++) {
            if (arreglo[i] > arreglo[i + 1]) {
                return false;
            }
        }
        return true;
    }

    static double calcularPromedio(double[] valores) {
        double suma = 0;
        for (double v : valores) {
            suma += v;
        }
        return suma / valores.length;
    }

    static double calcularDesviacion(double[] valores) {
        double promedio = calcularPromedio(valores);
        double suma = 0;
        for (double v : valores) {
            suma += (v - promedio) * (v - promedio);
        }
        return Math.sqrt(suma / valores.length);   // poblacional
    }

    static double redondear(double valor) {
        return Math.round(valor * 10000.0) / 10000.0;
    }

    static void escribirLinea(String linea) throws IOException {
        boolean esNuevo = !Files.exists(archivoCsv);
        if (esNuevo) {
            Files.writeString(archivoCsv, ENCABEZADO + "\n", StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
        }
        Files.writeString(archivoCsv, linea + "\n", StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    static void medir(String variante, int n) throws IOException {
        // 1. Cargar los datos (no se mide)
        int[] original = leerArchivo(carpetaDatos.resolve(variante + "_" + n + ".txt"));

        // 2. Calentamiento para el JIT (no se incluye en el promedio)
        int[] calentamiento = original.clone();
        BucketSort.ordenar(calentamiento);

        double[] tiempos = new double[REPETICIONES];
        boolean ordenadoCorrecto = true;

        System.out.println("\nVariante: " + variante);
        System.out.println("Tamaño: " + n);

        // 3. Repeticiones
        for (int r = 0; r < REPETICIONES; r++) {
            int[] copia = original.clone();            // copia nueva, no se mide

            long inicio = System.nanoTime();
            BucketSort.ordenar(copia);
            long fin = System.nanoTime();

            double tiempo = redondear((fin - inicio) / 1_000_000.0);   // ms
            tiempos[r] = tiempo;

            if (!estaOrdenado(copia)) {                // verificación fuera del tiempo
                ordenadoCorrecto = false;
            }

            System.out.println("Repetición " + (r + 1) + ": " + tiempo + " ms");
        }

        double promedio = redondear(calcularPromedio(tiempos));
        double desviacion = redondear(calcularDesviacion(tiempos));
        String verificacion = ordenadoCorrecto ? "OK" : "FALLA";

        System.out.println("Promedio: " + promedio + " ms");
        System.out.println("Desviación estándar: " + desviacion + " ms");
        System.out.println("Verificación: " + verificacion);

        StringBuilder reps = new StringBuilder();
        for (int r = 0; r < REPETICIONES; r++) {
            if (r > 0) {
                reps.append(",");
            }
            reps.append(tiempos[r]);
        }
        escribirLinea(String.format(Locale.US, "%s,%s,%s,%d,%s,%s,%s,medido,%s",
                ALGORITMO, LENGUAJE, variante, n, reps, promedio, desviacion, verificacion));
    }

    public static void main(String[] args) throws IOException {
        raiz = encontrarRaiz();
        carpetaDatos = raiz.resolve("datos").resolve("ordenamiento");
        archivoCsv = raiz.resolve("resultados").resolve("ordenamiento_java.csv");

        System.out.println("Carpeta de datos: " + carpetaDatos);
        System.out.println("Archivo de resultados: " + archivoCsv);

        Files.createDirectories(archivoCsv.getParent());

        for (int n : TAMANOS) {
            for (String variante : VARIANTES) {
                medir(variante, n);
            }
        }

        System.out.println("\nMediciones terminadas.");
    }
}