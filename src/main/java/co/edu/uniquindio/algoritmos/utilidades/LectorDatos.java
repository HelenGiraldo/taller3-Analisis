package co.edu.uniquindio.algoritmos.utilidades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LectorDatos {

    public static int[] leerArchivo(String ruta) throws IOException {

        List<String> lineas = Files.readAllLines(Path.of(ruta));

        int[] numeros = new int[lineas.size()];

        for (int i = 0; i < lineas.size(); i++) {
            numeros[i] = Integer.parseInt(lineas.get(i).trim());
        }

        return numeros;
    }
}
