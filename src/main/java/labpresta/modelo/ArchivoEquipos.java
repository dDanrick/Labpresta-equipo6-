package labpresta.modelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import labpresta.datos.RutaDatos;


public class ArchivoEquipos {

    private static final Path RUTA = RutaDatos.archivo("equipos.csv");
    private static final String ENCABEZADO = "codigo;tipo;descripcion;estado";

    
    public static List<Equipo> leer() {
        List<Equipo> equipos = new ArrayList<>();
        if (!Files.exists(RUTA)) {
            return equipos;
        }
        try {
            List<String> lineas = Files.readAllLines(RUTA, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                if (linea.isBlank() || linea.equals(ENCABEZADO)) {
                    continue;
                }
                try {
                    equipos.add(Equipo.fromCsv(linea));
                } catch (IllegalArgumentException e) {
                    System.err.println("Línea ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer equipos.csv: " + e.getMessage());
        }
        return equipos;
    }


    public static void guardar(List<Equipo> equipos) throws IOException {
        Files.createDirectories(RUTA.getParent());
        List<String> lineas = new ArrayList<>();
        lineas.add(ENCABEZADO);
        for (Equipo e : equipos) {
            lineas.add(e.toCsv());
        }
        Files.write(RUTA, lineas, StandardCharsets.UTF_8);
    }
}