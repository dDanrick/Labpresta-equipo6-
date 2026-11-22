
package labpresta.modelo;

public class Equipo {

    public static final String ESTADO_DISPONIBLE = "Disponible";
    public static final String ESTADO_PRESTADO = "Prestado";
    public static final String ESTADO_MANTENIMIENTO = "En mantenimiento";

    private final String codigo;
    private String tipo;
    private String descripcion;
    private String estado = "Disponible";

    public Equipo(String codigo, String tipo, String descripcion) {
        this.codigo = codigo != null ? codigo.trim().toUpperCase() : "";
        this.tipo = tipo != null ? tipo.trim() : "";
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.estado = ESTADO_DISPONIBLE;
    }

    public Equipo(String codigo, String tipo, String descripcion, String estado) {
        this.codigo = codigo != null ? codigo.trim().toUpperCase() : "";
        this.tipo = tipo != null ? tipo.trim() : "";
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.estado = (estado != null && !estado.isBlank()) ? estado.trim() : ESTADO_DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo != null ? tipo.trim() : "";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion.trim() : "";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return ESTADO_DISPONIBLE.equalsIgnoreCase(this.estado);
    }

    public void marcarPrestado() {
        this.estado = ESTADO_PRESTADO;
    }

    public void marcarEnMantenimiento() {
        this.estado = ESTADO_MANTENIMIENTO;
    }

    public void marcarDisponible() {
        this.estado = ESTADO_DISPONIBLE;
    }

    public String toCsv() {
        String descLimpia = descripcion.replace(";", ",");
        return codigo + ";" + tipo + ";" + descLimpia + ";" + estado;
    }

    public static Equipo fromCsv(String linea) {
        String[] partes = linea.split(";", -1);
        if (partes.length < 4) {
            throw new IllegalArgumentException("Línea CSV inválida para Equipo: " + linea);
        }
        return new Equipo(partes[0], partes[1], partes[2], partes[3]);
    }

    @Override
    public String toString() {
        return codigo + " - " + tipo + " (" + estado + ")";
    }
}