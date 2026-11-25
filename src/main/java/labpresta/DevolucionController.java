package labpresta;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import labpresta.datos.PrestamoCSV;
import labpresta.datos.UsuarioCSV;
import labpresta.modelo.ArchivoEquipos;
import labpresta.modelo.Equipo;
import labpresta.modelo.Prestamo;
import labpresta.modelo.Usuario;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class DevolucionController {
    
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, String> colEquipo;
    @FXML private TableColumn<Prestamo, String> colCedula;
    @FXML private TableColumn<Prestamo, String> colNombre;
    @FXML private TableColumn<Prestamo, String> colFechaPrestamo;
    @FXML private TableColumn<Prestamo, String> colFechaLimite;
    @FXML private DatePicker dpFecha;
    @FXML private Label lblMensaje;

    private final PrestamoCSV archivoPrestamos = new PrestamoCSV();
    private List<Prestamo> prestamos = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    /** Recibe las listas que ya tiene cargadas la pantalla principal. */
    public void setDatos(List<Equipo> equipos, List<Usuario> usuarios) {
        this.equipos = equipos;
        this.usuarios = usuarios;
        this.prestamos = archivoPrestamos.cargar();
        actualizarTabla();
    }

    @FXML
    private void initialize() {
        colEquipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigoEquipo()));
        colCedula.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCedulaUsuario()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(nombreDe(d.getValue().getCedulaUsuario())));
        colFechaPrestamo.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFechaPrestamo())));
        colFechaLimite.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFechaLimite())));
        tablaPrestamos.setPlaceholder(new Label("No hay préstamos activos."));
        
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

    @FXML
    private void registrarDevolucion() {
        Prestamo seleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un préstamo de la tabla.");
            return;
        }

        LocalDate fecha = dpFecha.getValue();
        if (fecha == null) {
            lblMensaje.setText("Ingrese una fecha válida (dd/MM/yyyy).");
            return;
        }
        if (fecha.isBefore(seleccionado.getFechaPrestamo())) {
            lblMensaje.setText("La fecha de devolución no puede ser anterior a la del préstamo.");
            return;
        }

        seleccionado.setFechaDevolucion(fecha);

        Equipo equipo = buscarEquipo(seleccionado.getCodigoEquipo());
        if (equipo != null) {
            equipo.marcarDisponible();
        }

        if (!archivoPrestamos.guardar(prestamos)) {
            seleccionado.setFechaDevolucion(null);
            lblMensaje.setText("No se pudo guardar prestamos.csv");
            return;
        }
        try {
            ArchivoEquipos.guardar(equipos);
        } catch (IOException e) {
            lblMensaje.setText("Devolución guardada, pero no se pudo actualizar equipos.csv");
            actualizarTabla();
            return;
        }

        actualizarTabla();
        lblMensaje.setText("Devolución registrada");
    }

    /** Muestra solo los préstamos que no se han devuelto. */
    private void actualizarTabla() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.estaActivo()) {
                activos.add(p);
            }
        }
        tablaPrestamos.setItems(FXCollections.observableArrayList(activos));
    }

    private Equipo buscarEquipo(String codigo) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    private String nombreDe(String cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u.getNombre();
            }
        }
        return "(no registrado)";
    }
}

