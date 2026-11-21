package labpresta.modelo;

import java.util.List;

public class ValidadorEquipo {

    public static final List<String> TIPOS_VALIDOS = List.of(
            "Laptop",
            "Proyector",
            "Kit Arduino",
            "Otro"
    );

    /**
     * Valida los datos de un nuevo equipo antes de registrarlo.
     *
     * @param codigo      Código del equipo (ej. LAP-01)
     * @param tipo        Tipo del equipo (Laptop, Proyector, Kit Arduino, Otro)
     * @param descripcion Descripción del equipo
     * @param existentes  Lista actual de equipos registrados (puede ser null o vacía)
     * @return null si todos los datos son válidos, o un mensaje claro de error para mostrar en pantalla
     */
    public static String validarNuevoEquipo(String codigo, String tipo, String descripcion, List<Equipo> existentes) {
        if (codigo == null || codigo.isBlank()) {
            return "El codigo del equipo es obligatorio.";
        }
        if (tipo == null || tipo.isBlank()) {
            return "Debe seleccionar o ingresar el tipo de equipo.";
        }
        if (descripcion == null || descripcion.isBlank()) {
            return "La descripcion del equipo es obligatoria.";
        }

        if (!esTipoValido(tipo)) {
            return "Tipo de equipo no valido. Los tipos permitidos son: Laptop, Proyector, Kit Arduino u Otro.";
        }

        String codigoNormalizado = codigo.trim();
        if (existeCodigo(codigoNormalizado, existentes)) {
            return "Ya existe un equipo registrado con el codigo '" + codigoNormalizado.toUpperCase() + "'.";
        }

        return null;
    }

    public static boolean esTipoValido(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return false;
        }
        String tipoLimpio = tipo.trim();
        for (String valido : TIPOS_VALIDOS) {
            if (valido.equalsIgnoreCase(tipoLimpio)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existeCodigo(String codigo, List<Equipo> existentes) {
        if (codigo == null || codigo.isBlank() || existentes == null || existentes.isEmpty()) {
            return false;
        }
        String codigoLimpio = codigo.trim();
        for (Equipo equipo : existentes) {
            if (equipo != null && equipo.getCodigo().equalsIgnoreCase(codigoLimpio)) {
                return true;
            }
        }
        return false;
    }
    
}