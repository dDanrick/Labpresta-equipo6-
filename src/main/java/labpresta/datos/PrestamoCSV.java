package labpresta.datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import labpresta.modelo.Prestamo;

public class PrestamoCSV {

    private static final String ENCABEZADO = "codigoEquipo;cedulaUsuario;fechaPrestamo;fechaLimite;fechaDevolucion";
    private final Path archivo;

    public PrestamoCSV() {
        // Carpeta data junto al .jar (ver RutaDatos)
        this(RutaDatos.archivo("prestamos.csv"));
    }

    public PrestamoCSV(Path archivo) {
        this.archivo = archivo;
    }


    public List<Prestamo> cargar() {
        List<Prestamo> prestamos = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return prestamos;
        }
        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea = lector.readLine(); 
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    prestamos.add(Prestamo.fromCsv(linea));
                } catch (RuntimeException e) {
                    System.err.println("Se ignoró una línea dañada en prestamos.csv: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer prestamos.csv: " + e.getMessage());
        }
        return prestamos;
    }

    
    public boolean guardar(List<Prestamo> prestamos) {
        try {
            if (archivo.getParent() != null) {
                Files.createDirectories(archivo.getParent());
            }
            try (BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                escritor.write(ENCABEZADO);
                escritor.newLine();
                for (Prestamo prestamo : prestamos) {
                    escritor.write(prestamo.toCsv());
                    escritor.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("No se pudo guardar prestamos.csv: " + e.getMessage());
            return false;
        }
    }
}