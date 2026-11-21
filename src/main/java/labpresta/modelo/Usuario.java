
package labpresta.modelo;

public class Usuario {
    public static final String TIPO_ESTUDIANTE = "Estudiante";
    public static final String TIPO_DOCENTE = "Docente";

    private final String cedula;
    private String nombre;
    private String tipo;
    private String telefono;

    public Usuario(String cedula, String nombre, String tipo, String telefono) {
        this.cedula = limpiar(cedula);
        this.nombre = limpiar(nombre);
        this.tipo = limpiar(tipo);
        this.telefono = limpiar(telefono);
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = limpiar(nombre);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = limpiar(tipo);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = limpiar(telefono);
    }

    public boolean esEstudiante() {
        return TIPO_ESTUDIANTE.equalsIgnoreCase(tipo);
    }

    public boolean esDocente() {
        return TIPO_DOCENTE.equalsIgnoreCase(tipo);
    }

    public String toCsv() {
        String nombreLimpio = nombre.replace(";", ",");
        return cedula + ";" + nombreLimpio + ";" + tipo + ";" + telefono;
    }

    public static Usuario fromCsv(String linea) {
        String[] partes = linea.split(";", -1);
        if (partes.length < 4) {
            throw new IllegalArgumentException("Línea CSV inválida para Usuario: " + linea);
        }
        return new Usuario(partes[0], partes[1], partes[2], partes[3]);
    }

    private static String limpiar(String texto) {
        return texto != null ? texto.trim() : "";
    }

    @Override
    public String toString() {
        return cedula + " - " + nombre + " (" + tipo + ")";
    }
}
