
package labpresta;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import labpresta.modelo.Equipo;
import labpresta.modelo.ValidadorEquipo;



public class EquipoFormularioController {
    @FXML private TextField txtCodigo;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtDescripcion;
    @FXML private Label lblMensaje;

    // Lista de equipos registrados. PrimaryController la entrega al abrir la pantalla.
    private List<Equipo> equipos = new ArrayList<>();

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @FXML
    private void initialize() {
        cmbTipo.getItems().addAll(ValidadorEquipo.TIPOS_VALIDOS);
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
        // Pendiente (tarea de guardado): escribir la lista en equipos.csv.

        mostrarMensaje("Equipo " + equipo.getCodigo() + " registrado como " + equipo.getEstado() + ".");
        limpiar();
    }

    private void limpiar() {
        txtCodigo.clear();
        txtDescripcion.clear();
        cmbTipo.setValue(null);
    }

    private void mostrarMensaje(String texto) {
        lblMensaje.setText(texto);
    }
}
