package co.edu.uniquindio.algoritmos_busqueda.pruebas;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaPorSaltos;

import java.util.Arrays;
import java.util.Random;

public class PruebaBusquedaPorSaltos {

    public static void main(String[] args) {

        int[] ejemplo = {3, 8, 15, 21, 30, 42, 55, 61, 70, 84, 91, 99, 105, 118, 130, 142};

        // Ejemplo a mano: n = 16, salto = 4
        System.out.println("Arreglo: " + Arrays.toString(ejemplo));
        probar(ejemplo, 55, 6);      // presente, en el medio
        probar(ejemplo, 3, 0);       // primer elemento
        probar(ejemplo, 142, 15);    // último elemento
        probar(ejemplo, 60, -1);     // ausente, entre dos valores
        probar(ejemplo, 1, -1);      // menor que todos
        probar(ejemplo, 500, -1);    // mayor que todos

        // Casos límite
        probar(new int[]{}, 5, -1);
        probar(new int[]{7}, 7, 0);
        probar(new int[]{7}, 8, -1);
        probar(new int[]{2, 4}, 4, 1);
        probar(new int[]{2, 4}, 3, -1);
        probar(new int[]{5, 5, 5, 5, 5}, 6, -1);

        // Prueba grande contra Arrays.binarySearch (todas las claves, presentes y ausentes)
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
            int obtenido = BusquedaPorSaltos.buscar(datos, clave);
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

        int obtenido = BusquedaPorSaltos.buscar(arreglo, clave);

        System.out.println("Buscar " + clave + " -> posición " + obtenido
                + " (esperado " + esperado + "): "
                + (obtenido == esperado ? "OK" : "FALLA"));
    }
}