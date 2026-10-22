
package co.edu.uniquindio.algoritmos.pruebas;

import java.io.IOException;

import co.edu.uniquindio.algoritmos.HeapSort;
import co.edu.uniquindio.algoritmos.utilidades.LectorDatos;

public class PruebaHeapSort {

    public static void main(String[] args) {

        try {
            int[] arreglo = LectorDatos.leerArchivo(
                    "datos/ordenamiento/aleatorios_1000.txt"
            );

            HeapSort.ordenar(arreglo);

            boolean ordenado = true;

            for (int i = 1; i < arreglo.length; i++) {
                if (arreglo[i] < arreglo[i - 1]) {
                    ordenado = false;
                    break;
                }
            }

            if (ordenado) {
                System.out.println(
                        "Heap Sort ordenó correctamente los 1000 números."
                );
            } else {
                System.out.println(
                        "Error: el arreglo no quedó ordenado."
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "No se pudieron leer los datos: " + e.getMessage()
            );
        }
    }
}
