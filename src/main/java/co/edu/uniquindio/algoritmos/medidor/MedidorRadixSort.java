package co.edu.uniquindio.algoritmos.medidor;

import co.edu.uniquindio.algoritmos.RadixSort;
import co.edu.uniquindio.algoritmos.utilidades.LectorDatos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MedidorRadixSort {

    private static final int[] TAMANOS = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] VARIANTES = {"aleatorios", "ascendente", "descendente"};
    private static final int REPETICIONES = 5;
    private static final String ARCHIVO_CSV = "resultados/ordenamiento_java.csv";

    public static void main(String[] args) throws IOException {

        for (int n : TAMANOS) {
            for (String variante : VARIANTES) {
                medir(variante, n);
            }
        }

        System.out.println("\nMediciones terminadas.");
    }

    private static void medir(String variante, int n) throws IOException {

        // 1. Cargar los datos (no se mide)
        int[] original = LectorDatos.leerArchivo(
                "datos/ordenamiento/" + variante + "_" + n + ".txt"
        );

        double[] tiempos = new double[REPETICIONES];
        boolean ordenadoCorrecto = true;

        System.out.println("\nVariante: " + variante);
        System.out.println("Tamaño: " + n);

        // 2. Calentamiento: no se incluye en el promedio
        int[] calentamiento = original.clone();
        RadixSort.ordenar(calentamiento);

        // 3. Repeticiones
        for (int r = 0; r < REPETICIONES; r++) {

            int[] copia = original.clone();   // copia nueva, no se mide

            long inicio = System.nanoTime();
            RadixSort.ordenar(copia);
            long fin = System.nanoTime();

            tiempos[r] = redondear((fin - inicio) / 1_000_000.0);

            if (!estaOrdenado(copia)) {       // verificación fuera del tiempo
                ordenadoCorrecto = false;
            }

            System.out.println("Repetición " + (r + 1) + ": " + tiempos[r] + " ms");
        }

        double promedio = redondear(calcularPromedio(tiempos));
        double desviacion = redondear(calcularDesviacion(tiempos));
        String verificacion = ordenadoCorrecto ? "OK" : "FALLA";

        System.out.println("Promedio: " + promedio + " ms");
        System.out.println("Desviación estándar: " + desviacion + " ms");
        System.out.println("Verificación: " + verificacion);

        guardarResultado(variante, n, tiempos, promedio, desviacion, verificacion);
    }

    private static boolean estaOrdenado(int[] arreglo) {

        for (int i = 0; i < arreglo.length - 1; i++) {
            if (arreglo[i] > arreglo[i + 1]) {
                return false;
            }
        }

        return true;
    }

    private static double calcularPromedio(double[] valores) {

        double suma = 0;

        for (double valor : valores) {
            suma += valor;
        }

        return suma / valores.length;
    }

    private static double calcularDesviacion(double[] valores) {

        double promedio = calcularPromedio(valores);
        double suma = 0;

        for (double valor : valores) {
            suma += (valor - promedio) * (valor - promedio);
        }

        return Math.sqrt(suma / valores.length);
    }

    private static double redondear(double valor) {
        return Math.round(valor * 10000.0) / 10000.0;
    }

    private static void guardarResultado(String variante, int n, double[] tiempos,
                                         double promedio, double desviacion,
                                         String verificacion) throws IOException {

        String linea = "Radix Sort,Java," + variante + "," + n + ","
                + tiempos[0] + "," + tiempos[1] + "," + tiempos[2] + ","
                + tiempos[3] + "," + tiempos[4] + ","
                + promedio + "," + desviacion + ",medido," + verificacion;

        escribirLinea(linea);
    }

    private static void escribirLinea(String linea) throws IOException {

        File archivo = new File(ARCHIVO_CSV);
        boolean esNuevo = !archivo.exists();

        try (FileWriter escritor = new FileWriter(archivo, true)) {

            if (esNuevo) {
                escritor.write("algoritmo,lenguaje,variante,n,rep1_ms,rep2_ms,rep3_ms,"
                        + "rep4_ms,rep5_ms,promedio_ms,desviacion_ms,estado,verificacion\n");
            }

            escritor.write(linea + "\n");
        }
    }
}