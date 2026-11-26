package labpresta.modelo;

import java.util.List;

public class ValidadorEquipo {

    public static final List<String> TIPOS_VALIDOS = List.of(
            "Laptop",
            "Proyector",
            "Kit Arduino",
            "Otro"
    );

    public static String validarNuevoEquipo(String codigo, String tipo, String descripcion, List<Equipo> existentes) {
     
        if ((codigo == null || codigo.isBlank())
                && (tipo == null || tipo.isBlank())
                && (descripcion == null || descripcion.isBlank())) {
            return "Todos los campos son obligatorios.";
        }
        if (codigo == null || codigo.isBlank()) {
            return "El codigo del equipo es obligatorio.";
        }
        if (tipo == null || tipo.isBlank()) {
            return "Debe seleccionar el tipo de equipo.";
        }
        if (descripcion == null || descripcion.isBlank()) {
            return "La descripcion del equipo es obligatoria.";
        }

        String codigoNormalizado = codigo.trim();
        if (existeCodigo(codigoNormalizado, existentes)) {
            return "Código del equipo existente '" + codigoNormalizado.toUpperCase() + "'.";
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
