
package labpresta.modelo;


public class ValidadorPrestamo {
    /**
     * Comprueba que el equipo pueda prestarse.
     * Solo se prestan equipos en estado Disponible; si está Prestado o
     * En mantenimiento, el préstamo no debe registrarse.
     * @return null si el equipo está disponible, o el mensaje de error para mostrar en pantalla
     */
    public static String validarEquipoDisponible(Equipo equipo) {
        if (equipo == null) {
            return "Seleccione un equipo.";
        }
        if (Equipo.ESTADO_PRESTADO.equalsIgnoreCase(equipo.getEstado())) {
            return "El equipo " + equipo.getCodigo() + " no está disponible: está Prestado";
        }
        if (Equipo.ESTADO_MANTENIMIENTO.equalsIgnoreCase(equipo.getEstado())) {
            return "El equipo " + equipo.getCodigo() + " no está disponible: está En mantenimiento";
        }
        if (!equipo.estaDisponible()) {
            return "El equipo " + equipo.getCodigo() + " no está disponible";
        }
        return null;
    }
}
