package labpresta;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import labpresta.modelo.ArchivoEquipos;
import labpresta.modelo.Equipo;
import labpresta.modelo.ValidadorEquipo;

public class EquipoFormularioController {
    @FXML private TextField txtCodigo;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtDescripcion;
    @FXML private Label lblMensaje;

 
    @FXML private TableView<Equipo> tablaEquipos;
    @FXML private TableColumn<Equipo, String> colCodigo;
    @FXML private TableColumn<Equipo, String> colTipo;
    @FXML private TableColumn<Equipo, String> colDescripcion;
    @FXML private TableColumn<Equipo, String> colEstado;

    private List<Equipo> equipos = new ArrayList<>();

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
        actualizarTabla();
    }

    @FXML
    private void initialize() {
        cmbTipo.getItems().addAll(ValidadorEquipo.TIPOS_VALIDOS);

        colCodigo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCodigo()));
        colTipo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getTipo()));
        colDescripcion.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getDescripcion()));
        colEstado.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getEstado()));
        tablaEquipos.setPlaceholder(new Label("No hay equipos registrados."));
    }

    @FXML
    private void guardar() {
        String error = ValidadorEquipo.validarNuevoEquipo(
                txtCodigo.getText(), cmbTipo.getValue(), txtDescripcion.getText(), equipos);

        if (error != null) {
            mostrarMensaje(error);
            return;
        }

        Equipo equipo = new Equipo(txtCodigo.getText(), cmbTipo.getValue(), txtDescripcion.getText());
        equipos.add(equipo);
        guardarEnArchivo();

        actualizarTabla();
        mostrarMensaje("Equipo registrado correctamente.");
        limpiar();
    }


    public void actualizarTabla() {
        tablaEquipos.getItems().setAll(equipos);
        tablaEquipos.refresh();
    }

    private void limpiar() {
        txtCodigo.clear();
        txtDescripcion.clear();
        cmbTipo.setValue(null);
    }

    private void mostrarMensaje(String texto) {
        lblMensaje.setText(texto);
    }

    // HU-C1 · Marcar en mantenimiento o devolver a disponible
    @FXML
    private void marcarMantenimiento() {
        Equipo e = tablaEquipos.getSelectionModel().getSelectedItem();
        if (e == null) { aviso("Selecciona un equipo de la tabla."); return; }
        if (Equipo.ESTADO_PRESTADO.equals(e.getEstado())) { aviso("No se puede: el equipo está prestado."); return; }
        if (Equipo.ESTADO_MANTENIMIENTO.equals(e.getEstado())) { aviso("El equipo ya está en mantenimiento."); return; }
        e.marcarEnMantenimiento();
        guardarEnArchivo();
        actualizarTabla();
        mostrarMensaje("Equipo " + e.getCodigo() + " marcado en mantenimiento.");
    }

    @FXML
    private void devolverDisponible() {
        Equipo e = tablaEquipos.getSelectionModel().getSelectedItem();
        if (e == null) { aviso("Selecciona un equipo de la tabla."); return; }
        if (!Equipo.ESTADO_MANTENIMIENTO.equals(e.getEstado())) { aviso("Solo se devuelven a disponible los equipos en mantenimiento."); return; }
        e.marcarDisponible();
        guardarEnArchivo();
        actualizarTabla();
        mostrarMensaje("Equipo " + e.getCodigo() + " disponible otra vez.");
    }

    private void aviso(String texto) {
        new Alert(Alert.AlertType.WARNING, texto).showAndWait();
    }

    
    private void guardarEnArchivo() {
        try {
            ArchivoEquipos.guardar(equipos);
        } catch (java.io.IOException ex) {
            aviso("No se pudo guardar en equipos.csv: " + ex.getMessage());
        }
    }
}