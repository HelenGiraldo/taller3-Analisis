package co.edu.uniquindio.algoritmos_busqueda.pruebas;

import co.edu.uniquindio.algoritmos_busqueda.BusquedaBinaria;

public class PruebaBusquedaBinaria {

    public static void main(String[] args) {
        int[] arreglo = {10, 20, 30, 40, 50, 60, 70};

        System.out.println("Prueba de búsqueda binaria");

        System.out.println("Buscar 10: " + BusquedaBinaria.buscar(arreglo, 10));
        System.out.println("Buscar 40: " + BusquedaBinaria.buscar(arreglo, 40));
        System.out.println("Buscar 70: " + BusquedaBinaria.buscar(arreglo, 70));
        System.out.println("Buscar 25: " + BusquedaBinaria.buscar(arreglo, 25));
    }
}
