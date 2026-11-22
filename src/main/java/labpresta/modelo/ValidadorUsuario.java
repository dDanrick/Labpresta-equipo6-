
package labpresta.modelo;

import java.util.List;

public class ValidadorUsuario {
    /**
     * Valida los datos de un nuevo usuario antes de registrarlo.
     *
     * @return null si todo es válido, o el mensaje de error para mostrar en pantalla
     * 
     */
    public static String validarNuevoUsuario(String cedula, String nombre, String tipo,
                                             String telefono, List<Usuario> existentes) {

        if (cedula == null || cedula.isBlank()
                || nombre == null || nombre.isBlank()
                || telefono == null || telefono.isBlank()) {
            return "Complete la cédula, el nombre y el teléfono.";
        }
        if (tipo == null || tipo.isBlank()) {
            return "Seleccione el tipo de usuario";
        }
        if (!tieneDiezDigitos(cedula)) {
            return "La cédula debe tener exactamente 10 dígitos numéricos.";
        }
        if (!tieneDiezDigitos(telefono)) {
            return "El teléfono debe tener exactamente 10 dígitos numéricos.";
        }
        if (existeCedula(cedula, existentes)) {
            return "Número de cédula existente";
        }
        return null;
    }

    public static boolean existeCedula(String cedula, List<Usuario> existentes) {
        if (cedula == null || existentes == null) {
            return false;
        }
        String cedulaLimpia = cedula.trim();
        for (Usuario usuario : existentes) {
            if (usuario != null && usuario.getCedula().equals(cedulaLimpia)) {
                return true;
            }
        }
        return false;
    }

    private static boolean tieneDiezDigitos(String texto) {
        return texto.trim().matches("\\d{10}");
    }
}
