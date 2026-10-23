package co.edu.uniquindio.algoritmos_busqueda.pruebas;


import co.edu.uniquindio.algoritmos_busqueda.BusquedaTernaria;

public class PruebaBusquedaTernaria {

    private static void probar(
            int[] arreglo, int clave,
            int esperado, String descripcion) {

        int resultado = BusquedaTernaria.buscar(arreglo, clave);
        boolean correcto = resultado == esperado;

        System.out.println(
                descripcion + ": " + (correcto ? "CORRECTO" : "ERROR")
        );

        if (!correcto) {
            System.out.println(
                    "Esperado: " + esperado + ", obtenido: " + resultado
            );
        }
    }

    public static void main(String[] args) {
        int[] arreglo = {10, 20, 30, 40, 50, 60, 70};

        probar(arreglo, 10, 0, "Buscar primer elemento");
        probar(arreglo, 40, 3, "Buscar elemento central");
        probar(arreglo, 70, 6, "Buscar último elemento");
        probar(arreglo, 30, 2, "Buscar elemento intermedio");
        probar(arreglo, 25, -1, "Buscar elemento inexistente");

        probar(new int[]{}, 10, -1, "Arreglo vacío");
        probar(new int[]{15}, 15, 0, "Un solo elemento presente");
        probar(new int[]{15}, 20, -1, "Un solo elemento ausente");
    }
}
