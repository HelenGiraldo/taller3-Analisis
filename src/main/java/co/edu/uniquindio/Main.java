package co.edu.uniquindio;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {

        int[] numeros = {1, 5, 3, 2};

        boolean ordenado = true;

        for (int i = 0; i < numeros.length - 1; i++) {

            if (numeros[i] > numeros[i + 1]) {
                ordenado = false;
                break;
            }
        }

        if (ordenado) {
            System.out.println("El arreglo está ordenado.");
        } else {
            System.out.println("El arreglo NO está ordenado.");
        }
    }
}
