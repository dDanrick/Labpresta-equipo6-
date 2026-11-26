package labpresta.modelo;

public class ValidadorPrestamo {

  
    public static String validarEquipoDisponible(Equipo equipo) {
        if (equipo == null) {
            return "Seleccione un equipo.";
        }
        if (Equipo.ESTADO_PRESTADO.equalsIgnoreCase(equipo.getEstado())) {
            return "El equipo " + equipo.getCodigo() + " está Prestado. Seleccione otro equipo.";
        }
        if (Equipo.ESTADO_MANTENIMIENTO.equalsIgnoreCase(equipo.getEstado())) {
            return "El equipo " + equipo.getCodigo() + " está En mantenimiento. Seleccione otro equipo.";
        }
        if (!equipo.estaDisponible()) {
            return "El equipo " + equipo.getCodigo() + " no está disponible.";
        }
        return null;
    }
}