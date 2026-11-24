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
import labpresta.datos.UsuarioCSV; 
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class UsuarioFormularioController {
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtTelefono;
    @FXML private Label lblMensaje;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colCedula;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colTipo;
    @FXML private TableColumn<Usuario, String> colTelefono;


    @FXML private TextField txtBuscar;

   
    private List<Usuario> usuarios = new ArrayList<>();

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        refrescarLista();
    }

    @FXML
    private void initialize() {
        cmbTipo.getItems().addAll(Usuario.TIPO_ESTUDIANTE, Usuario.TIPO_DOCENTE);

        // HU-C2 · Cada vez que se escribe en el buscador, se filtra la lista
        txtBuscar.textProperty().addListener((obs, anterior, nuevo) -> refrescarLista());
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
                     if (!new UsuarioCSV().guardar(usuarios)) {
            lblMensaje.setText("No se pudo guardar el archivo usuarios.csv");
            return;
        }
        refrescarLista();
        lblMensaje.setText("Usuario guardado correctamente");
        limpiar();
    }


    private void refrescarLista() {
        String texto = txtBuscar.getText() == null ? "" : txtBuscar.getText().trim().toLowerCase();
        List<Usuario> resultado = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (coincide(usuario, texto)) {
                resultado.add(usuario);
            }
        }
                // HU-C2 · Columnas de la tabla de usuarios
        colCedula.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCedula()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo()));
        colTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));

        tablaUsuarios.setItems(FXCollections.observableArrayList(resultado));
        tablaUsuarios.setPlaceholder(new Label(texto.isEmpty()
                ? "No hay usuarios registrados."
                : "No se encontraron usuarios con \"" + txtBuscar.getText().trim() + "\"."));
    }


    private boolean coincide(Usuario usuario, String texto) {
        if (texto.isEmpty()) {
            return true;
        }
        return usuario.getCedula().contains(texto)
                || usuario.getNombre().toLowerCase().contains(texto);
    }

    private void limpiar() {
        txtCedula.clear();
        txtNombre.clear();
        txtTelefono.clear();
        cmbTipo.setValue(null);
    }
}