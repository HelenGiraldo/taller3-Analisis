package co.edu.uniquindio.algoritmos_busqueda.pruebas;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaExponencial;

import java.util.Arrays;
import java.util.Random;

public class PruebaBusquedaExponencial {

    public static void main(String[] args) {

        int[] ejemplo = {3, 8, 15, 21, 30, 42, 55, 61, 70, 84, 91, 99, 105, 118, 130, 142};

        // Ejemplo a mano: para 55 el índice se duplica 1, 2, 4, 8 (arreglo[8] = 70 >= 55),
        // y la búsqueda binaria se hace en el rango [4, 8], donde encuentra 55 en la posición 6.
        System.out.println("Arreglo: " + Arrays.toString(ejemplo));
        probar(ejemplo, 55, 6);      // presente
        probar(ejemplo, 3, 0);       // primer elemento
        probar(ejemplo, 142, 15);    // último elemento (n es potencia de 2)
        probar(ejemplo, 60, -1);     // ausente, entre dos valores
        probar(ejemplo, 1, -1);      // menor que todos
        probar(ejemplo, 500, -1);    // mayor que todos

        // Casos límite
        probar(new int[]{}, 5, -1);
        probar(new int[]{7}, 7, 0);
        probar(new int[]{7}, 8, -1);
        probar(new int[]{2, 4}, 4, 1);
        probar(new int[]{2, 4}, 3, -1);
        probar(new int[]{5, 5, 5, 5, 5}, 5, 0);
        probar(new int[]{5, 5, 5, 5, 5}, 6, -1);

        // Todos los tamaños de 0 a 300 (potencias de 2 y no potencias), todas las posiciones y claves ausentes
        boolean tamanosBien = true;
        for (int n = 0; n <= 300; n++) {
            int[] arreglo = new int[n];
            for (int i = 0; i < n; i++) {
                arreglo[i] = 10 * i + 5;
            }
            for (int i = 0; i < n; i++) {
                if (BusquedaExponencial.buscar(arreglo, arreglo[i]) != i) {
                    tamanosBien = false;
                }
            }
            for (int clave = 0; clave <= 10 * n + 10; clave++) {
                if (clave % 10 != 5 && BusquedaExponencial.buscar(arreglo, clave) != -1) {
                    tamanosBien = false;
                }
            }
        }
        System.out.println("Tamaños de 0 a 300, todas las claves: " + (tamanosBien ? "OK" : "FALLA"));

        // Prueba grande contra Arrays.binarySearch (claves presentes y ausentes), datos como los del taller
        Random aleatorio = new Random(42);
        int[] datos = new int[10_000];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = 10_000_000 + aleatorio.nextInt(90_000_000);
        }
        Arrays.sort(datos);

        boolean todoBien = true;
        for (int i = 0; i < 20_000; i++) {
            int clave = (i % 2 == 0) ? datos[aleatorio.nextInt(datos.length)]
                    : 10_000_000 + aleatorio.nextInt(90_000_000);
            int obtenido = BusquedaExponencial.buscar(datos, clave);
            boolean existe = Arrays.binarySearch(datos, clave) >= 0;
            boolean correcto = existe ? (obtenido >= 0 && datos[obtenido] == clave) : obtenido == -1;
            if (!correcto) {
                todoBien = false;
            }
        }
        System.out.println("20.000 búsquedas aleatorias contra Arrays.binarySearch: "
                + (todoBien ? "OK" : "FALLA"));
    }

    private static void probar(int[] arreglo, int clave, int esperado) {

        int obtenido = BusquedaExponencial.buscar(arreglo, clave);

        System.out.println("Buscar " + clave + " -> posición " + obtenido
                + " (esperado " + esperado + "): "
                + (obtenido == esperado ? "OK" : "FALLA"));
    }
}