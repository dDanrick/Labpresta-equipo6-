package labpresta.datos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class RutaDatos {

    private static final Path CARPETA = calcularCarpeta();

    private RutaDatos() {
    }

    public static Path archivo(String nombre) {
        return CARPETA.resolve(nombre);
    }

    private static Path calcularCarpeta() {
        try {
            Path ubicacion = Paths.get(RutaDatos.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            if (Files.isRegularFile(ubicacion) && ubicacion.toString().toLowerCase().endsWith(".jar")) {
                return ubicacion.getParent().resolve("data");
            }
        } catch (Exception e) {
            System.err.println("No se pudo ubicar el .jar; se usa la carpeta data actual: " + e.getMessage());
        }
        return Paths.get("data");
    }
}
