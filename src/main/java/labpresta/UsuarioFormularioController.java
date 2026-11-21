
package labpresta;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import labpresta.modelo.Usuario;
import labpresta.modelo.ValidadorUsuario;

public class UsuarioFormularioController {
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtTelefono;
    @FXML private Label lblMensaje;
    @FXML private ListView<Usuario> lstUsuarios;

    // Lista de usuarios registrados. PrimaryController la entrega al abrir la pantalla.
    private List<Usuario> usuarios = new ArrayList<>();

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        refrescarLista();
    }

    @FXML
    private void initialize() {
        cmbTipo.getItems().addAll(Usuario.TIPO_ESTUDIANTE, Usuario.TIPO_DOCENTE);
    }

    @FXML
    private void guardar() {
        String error = ValidadorUsuario.validarNuevoUsuario(
                txtCedula.getText(), txtNombre.getText(), cmbTipo.getValue(),
                txtTelefono.getText(), usuarios);

        if (error != null) {
            lblMensaje.setText(error);
            return;
        }

        usuarios.add(new Usuario(txtCedula.getText(), txtNombre.getText(),
                cmbTipo.getValue(), txtTelefono.getText()));
        // Pendiente (tarea de guardado): escribir la lista en usuarios.csv.

        refrescarLista();
        lblMensaje.setText("Usuario guardado correctamente");
        limpiar();
    }

    private void refrescarLista() {
        lstUsuarios.setItems(FXCollections.observableArrayList(usuarios));
    }

    private void limpiar() {
        txtCedula.clear();
        txtNombre.clear();
        txtTelefono.clear();
        cmbTipo.setValue(null);
    }
}
