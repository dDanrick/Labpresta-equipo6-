package labpresta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import labpresta.modelo.Equipo;
import labpresta.modelo.Usuario;
import java.io.IOException;
import labpresta.datos.PrestamoCSV;
import labpresta.modelo.ArchivoEquipos;
import labpresta.modelo.Prestamo;
import labpresta.modelo.ValidadorPrestamo;

public class PrestamoFormularioController {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private ComboBox<Usuario> cmbUsuario;
    @FXML private ComboBox<Equipo> cmbEquipo;
    @FXML private DatePicker dpFecha;
    @FXML private Label lblMensaje;

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();

    public void setDatos(List<Usuario> usuarios, List<Equipo> equipos) {
        this.usuarios = usuarios;
        this.equipos = equipos;
        refrescarListas();
    }

    @FXML
    private void initialize() {
        
        dpFecha.setValue(LocalDate.now());
        dpFecha.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate fecha) {
                return fecha == null ? "" : FORMATO.format(fecha);
            }

            @Override
            public LocalDate fromString(String texto) {
                try {
                    return (texto == null || texto.isBlank()) ? null : LocalDate.parse(texto, FORMATO);
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }

    private void refrescarListas() {
        cmbUsuario.getItems().setAll(usuarios);

        List<Equipo> disponibles = new ArrayList<>();
        for (Equipo equipo : equipos) {
            if (equipo.estaDisponible()) {
                disponibles.add(equipo);
            }
        }
        cmbEquipo.getItems().setAll(disponibles);
    }

     @FXML
    private void guardar() {
        Usuario usuario = cmbUsuario.getValue();
        Equipo equipo = cmbEquipo.getValue();
        LocalDate fecha = dpFecha.getValue();

        if (usuario == null) {
            lblMensaje.setText("Seleccione el usuario");
            return;
        }
        String error = ValidadorPrestamo.validarEquipoDisponible(equipo);
        if (error != null) {
            lblMensaje.setText(error);
            return;
        }
        if (fecha == null) {
            lblMensaje.setText("Ingrese una fecha válida (dd/MM/yyyy)");
            return;
        }

        // Fecha límite: 1 día para estudiantes, 3 días para docentes
        LocalDate limite = usuario.esDocente() ? fecha.plusDays(3) : fecha.plusDays(1);

        PrestamoCSV archivo = new PrestamoCSV();
        List<Prestamo> prestamos = archivo.cargar();
        prestamos.add(new Prestamo(equipo.getCodigo(), usuario.getCedula(), fecha, limite));

        if (!archivo.guardar(prestamos)) {
            lblMensaje.setText("No se pudo guardar prestamos.csv");
            return;
        }

        equipo.marcarPrestado();
        try {
            ArchivoEquipos.guardar(equipos);
        } catch (IOException e) {
            lblMensaje.setText("Préstamo guardado, pero no se pudo actualizar equipos.csv");
            return;
        }

        refrescarListas();
        cmbUsuario.setValue(null);
        lblMensaje.setText("Préstamo registrado. Devolver hasta el " + FORMATO.format(limite));
    }
}