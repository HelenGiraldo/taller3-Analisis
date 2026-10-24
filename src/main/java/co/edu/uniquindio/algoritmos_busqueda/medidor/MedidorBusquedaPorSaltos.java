package co.edu.uniquindio.algoritmos_busqueda.medidor;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaPorSaltos;
import co.edu.uniquindio.algoritmos.utilidades.LectorDatos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MedidorBusquedaPorSaltos {

    private static final int[] TAMANOS = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] ESCENARIOS = {"claves_mixtas", "inicio", "centro", "final"};
    private static final int BUSQUEDAS = 1_000;
    private static final int REPETICIONES = 5;
    private static final int PASADAS_CALENTAMIENTO = 50;
    private static final String ARCHIVO_CSV = "resultados/busqueda_java.csv";

    // Se acumula el resultado de las búsquedas para que la JVM no elimine el código medido
    private static long sumidero = 0;

    public static void main(String[] args) throws IOException {

        for (int n : TAMANOS) {
            for (String escenario : ESCENARIOS) {
                medir(escenario, n);
            }
        }

        System.out.println("\nMediciones terminadas. (control: " + sumidero + ")");
    }

    private static void medir(String escenario, int n) throws IOException {

        // 1. Cargar los datos y armar las 1.000 claves (no se mide)
        int[] arreglo = LectorDatos.leerArchivo("datos/ordenamiento/ascendente_" + n + ".txt");
        int[] claves = armarClaves(escenario, arreglo, n);

        double[] tiempos = new double[REPETICIONES];   // ns por búsqueda

        System.out.println("\nEscenario: " + escenario);
        System.out.println("Tamaño: " + n);

        // 2. Calentamiento: no se incluye en el promedio
        for (int p = 0; p < PASADAS_CALENTAMIENTO; p++) {
            sumidero += buscarTodas(arreglo, claves);
        }

        // 3. Repeticiones: se mide el tiempo total de las 1.000 búsquedas
        for (int r = 0; r < REPETICIONES; r++) {

            long inicio = System.nanoTime();
            sumidero += buscarTodas(arreglo, claves);
            long fin = System.nanoTime();

            tiempos[r] = redondear((fin - inicio) / (double) BUSQUEDAS);

            System.out.println("Repetición " + (r + 1) + ": " + tiempos[r] + " ns por búsqueda");
        }

        // 4. Verificación fuera del tiempo medido
        String verificacion = verificar(arreglo, claves) ? "OK" : "FALLA";

        double promedio = redondear(calcularPromedio(tiempos));
        double desviacion = redondear(calcularDesviacion(tiempos));

        System.out.println("Promedio: " + promedio + " ns por búsqueda");
        System.out.println("Desviación estándar: " + desviacion + " ns");
        System.out.println("Verificación: " + verificacion);

        guardarResultado(escenario, n, tiempos, promedio, desviacion, verificacion);
    }

    // claves_mixtas: 500 presentes + 500 ausentes (de los archivos). Las demás: una clave repetida 1.000 veces
    private static int[] armarClaves(String escenario, int[] arreglo, int n) throws IOException {

        if (escenario.equals("claves_mixtas")) {

            int[] presentes = LectorDatos.leerArchivo("datos/busqueda/claves_presentes_" + n + ".txt");
            int[] ausentes = LectorDatos.leerArchivo("datos/busqueda/claves_ausentes_" + n + ".txt");

            int[] claves = new int[presentes.length + ausentes.length];
            System.arraycopy(presentes, 0, claves, 0, presentes.length);
            System.arraycopy(ausentes, 0, claves, presentes.length, ausentes.length);
            return claves;
        }

        int posicion;

        if (escenario.equals("inicio")) {
            posicion = 0;
        } else if (escenario.equals("centro")) {
            posicion = n / 2;
        } else {
            posicion = n - 1;
        }

        int[] claves = new int[BUSQUEDAS];
        Arrays.fill(claves, arreglo[posicion]);
        return claves;
    }

    private static long buscarTodas(int[] arreglo, int[] claves) {

        long suma = 0;

        for (int clave : claves) {
            suma += BusquedaPorSaltos.buscar(arreglo, clave);
        }

        return suma;
    }

    // Cada resultado debe coincidir con Arrays.binarySearch: presente -> posición con esa clave; ausente -> -1
    private static boolean verificar(int[] arreglo, int[] claves) {

        for (int clave : claves) {

            int obtenido = BusquedaPorSaltos.buscar(arreglo, clave);
            boolean existe = Arrays.binarySearch(arreglo, clave) >= 0;

            if (existe) {
                if (obtenido < 0 || arreglo[obtenido] != clave) {
                    return false;
                }
            } else if (obtenido != -1) {
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
        return Math.round(valor * 100.0) / 100.0;
    }

    private static void guardarResultado(String escenario, int n, double[] tiempos,
                                         double promedio, double desviacion,
                                         String verificacion) throws IOException {

        String linea = "Búsqueda por Saltos,Java," + escenario + "," + n + ","
                + tiempos[0] + "," + tiempos[1] + "," + tiempos[2] + ","
                + tiempos[3] + "," + tiempos[4] + ","
                + promedio + "," + desviacion + ",medido," + verificacion;

        File archivo = new File(ARCHIVO_CSV);
        boolean esNuevo = !archivo.exists();

        try (FileWriter escritor = new FileWriter(archivo, true)) {

            if (esNuevo) {
                escritor.write("algoritmo,lenguaje,escenario,n,rep1_ns,rep2_ns,rep3_ns,"
                        + "rep4_ns,rep5_ns,promedio_ns,desviacion_ns,estado,verificacion\n");
            }

            escritor.write(linea + "\n");
        }
    }
}