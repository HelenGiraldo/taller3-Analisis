package co.edu.uniquindio.algoritmos_busqueda.pruebas;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaLineal;

public class PruebaBusquedaLineal {

    public static void main(String[] args) {

        int[] arreglo = {10, 20, 30, 40, 50};

        probar(arreglo, 10, 0, "Elemento al inicio");
        probar(arreglo, 30, 2, "Elemento en medio");
        probar(arreglo, 50, 4, "Elemento al final");
        probar(arreglo, 99, -1, "Elemento inexistente");
    }

    public static void probar(int[] arreglo, int objetivo,
                              int esperado, String caso) {

        int resultado = BusquedaLineal.buscar(arreglo, objetivo);

        if (resultado == esperado) {
            System.out.println(caso + ": CORRECTO");
        } else {
            System.out.println(caso + ": ERROR");
            System.out.println("Esperado: " + esperado);
            System.out.println("Obtenido: " + resultado);
        }
    }
}