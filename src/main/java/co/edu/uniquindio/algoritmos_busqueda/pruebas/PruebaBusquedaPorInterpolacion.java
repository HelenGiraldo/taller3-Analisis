package co.edu.uniquindio.algoritmos_busqueda.pruebas;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaPorInterpolacion;

import java.util.Arrays;
import java.util.Random;

public class PruebaBusquedaPorInterpolacion {

    public static void main(String[] args) {

        int[] ejemplo = {3, 8, 15, 21, 30, 42, 55, 61, 70, 84, 91, 99, 105, 118, 130, 142};

        // Ejemplo a mano: para 55 la posición estimada es 0 + (55-3)*15/(142-3) = 5 (vale 42);
        // como 42 < 55 se sigue con el rango [6, 15], donde la estimación cae en 6.
        System.out.println("Arreglo: " + Arrays.toString(ejemplo));
        probar(ejemplo, 55, 6);      // presente
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
        probar(new int[]{5, 5, 5, 5, 5}, 5, 0);   // todos iguales: no debe dividir entre cero
        probar(new int[]{5, 5, 5, 5, 5}, 6, -1);

        // Prueba grande contra Arrays.binarySearch (claves presentes y ausentes), datos como los del taller
        Random aleatorio = new Random(42);
        int[] datos = new int[10_000];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = 10_000_000 + aleatorio.nextInt(90_000_000);
        }
        Arrays.sort(datos);
        System.out.println("Datos uniformes (como los del taller): "
                + (comparar(datos, aleatorio) ? "OK" : "FALLA"));

        // Datos NO uniformes (crecimiento exponencial): sigue siendo correcto, aunque más lento
        int[] sesgados = new int[30];
        long valor = 1;
        for (int i = 0; i < sesgados.length; i++) {
            sesgados[i] = (int) valor;
            valor = Math.min(valor * 2 + 1, Integer.MAX_VALUE);
        }
        boolean sesgadosBien = true;
        for (int i = 0; i < sesgados.length; i++) {
            if (BusquedaPorInterpolacion.buscar(sesgados, sesgados[i]) != i) {
                sesgadosBien = false;
            }
        }
        System.out.println("Datos no uniformes (potencias de 2): " + (sesgadosBien ? "OK" : "FALLA"));

        // Valores extremos, para comprobar que no hay desbordamiento
        int[] extremos = {Integer.MIN_VALUE, -5, 0, 7, Integer.MAX_VALUE};
        boolean extremosBien = true;
        for (int i = 0; i < extremos.length; i++) {
            if (BusquedaPorInterpolacion.buscar(extremos, extremos[i]) != i) {
                extremosBien = false;
            }
        }
        System.out.println("Valores extremos de int: " + (extremosBien ? "OK" : "FALLA"));
    }

    private static boolean comparar(int[] datos, Random aleatorio) {

        for (int i = 0; i < 20_000; i++) {
            int clave = (i % 2 == 0) ? datos[aleatorio.nextInt(datos.length)]
                    : 10_000_000 + aleatorio.nextInt(90_000_000);
            int obtenido = BusquedaPorInterpolacion.buscar(datos, clave);
            boolean existe = Arrays.binarySearch(datos, clave) >= 0;
            boolean correcto = existe ? (obtenido >= 0 && datos[obtenido] == clave) : obtenido == -1;
            if (!correcto) {
                return false;
            }
        }

        return true;
    }

    private static void probar(int[] arreglo, int clave, int esperado) {

        int obtenido = BusquedaPorInterpolacion.buscar(arreglo, clave);

        System.out.println("Buscar " + clave + " -> posición " + obtenido
                + " (esperado " + esperado + "): "
                + (obtenido == esperado ? "OK" : "FALLA"));
    }
}