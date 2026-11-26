package labpresta.datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import labpresta.modelo.Usuario;


public class UsuarioCSV {

    private static final String ENCABEZADO = "cedula;nombre;tipo;telefono";
    private final Path archivo;

    public UsuarioCSV() {
        this(RutaDatos.archivo("usuarios.csv"));
    }

    public UsuarioCSV(Path archivo) {
        this.archivo = archivo;
    }


    public List<Usuario> cargar() {
        List<Usuario> usuarios = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return usuarios;
        }
        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea = lector.readLine(); // salta el encabezado
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    usuarios.add(Usuario.fromCsv(linea));
                } catch (IllegalArgumentException e) {
                    System.err.println("Se ignoró una línea dañada en usuarios.csv: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer usuarios.csv: " + e.getMessage());
        }
        return usuarios;
    }

 
    public boolean guardar(List<Usuario> usuarios) {
        try {
            if (archivo.getParent() != null) {
                Files.createDirectories(archivo.getParent());
            }
            try (BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                escritor.write(ENCABEZADO);
                escritor.newLine();
                for (Usuario usuario : usuarios) {
                    escritor.write(usuario.toCsv());
                    escritor.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("No se pudo guardar usuarios.csv: " + e.getMessage());
            return false;
        }
    }
}