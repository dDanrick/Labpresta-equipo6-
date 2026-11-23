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
        if (equipo == null) {
            lblMensaje.setText("Seleccione un equipo");
            return;
        }
        if (fecha == null) {
            lblMensaje.setText("Ingrese una fecha válida (dd/MM/yyyy)");
            return;
        }

        // Aqui se aumentara lo que pertenece a las tareas de los demas de HU-C3:
      
        lblMensaje.setText("Pantalla lista. Falta conectar el registro del préstamo.");
    }
}