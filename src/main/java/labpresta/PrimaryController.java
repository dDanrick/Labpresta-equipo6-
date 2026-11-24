package labpresta;

import labpresta.modelo.ArchivoEquipos;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import labpresta.modelo.Equipo;
import labpresta.modelo.Usuario;
import labpresta.datos.UsuarioCSV;

public class PrimaryController {

    @FXML private BorderPane raiz;
    @FXML private VBox panelInicio;
    @FXML private Label lblEstado;

    @FXML
    private void mostrarInicio() {
        raiz.setCenter(panelInicio);
        lblEstado.setText("Listo");
    }

    @FXML
    private void abrirEquipos() {
        try {
            FXMLLoader loader = new FXMLLoader(PrimaryController.class.getResource("EquipoFormulario.fxml"));
            Parent formulario = loader.load();
            EquipoFormularioController controlador = loader.getController();
            controlador.setEquipos(equipos);
            raiz.setCenter(formulario);
            lblEstado.setText("Equipos");
        } catch (Exception e) {
            e.printStackTrace();
            lblEstado.setText("Error: " + e.getMessage());
        }
    }
    

    @FXML
    private void abrirUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(PrimaryController.class.getResource("UsuarioFormulario.fxml"));
            Parent formulario = loader.load();
            UsuarioFormularioController controlador = loader.getController();
            controlador.setUsuarios(usuarios);
            raiz.setCenter(formulario);
            lblEstado.setText("Usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            lblEstado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void abrirNuevoPrestamo() {
        try {
            FXMLLoader loader = new FXMLLoader(PrimaryController.class.getResource("PrestamoFormulario.fxml"));
            Parent formulario = loader.load();
            PrestamoFormularioController controlador = loader.getController();
            controlador.setDatos(usuarios, equipos);
            raiz.setCenter(formulario);
            lblEstado.setText("Nuevo préstamo");
        } catch (Exception e) {
            e.printStackTrace();
            lblEstado.setText("Error: " + e.getMessage());
            }
    }

        @FXML
    private void abrirDevolucion() {
        try {
            FXMLLoader loader = new FXMLLoader(PrimaryController.class.getResource("DevolucionFormulario.fxml"));
            Parent formulario = loader.load();
            DevolucionController controlador = loader.getController();
            controlador.setDatos(equipos, usuarios);
            raiz.setCenter(formulario);
            lblEstado.setText("Devolución");
        } catch (Exception e) {
            e.printStackTrace();
            lblEstado.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void acercaDe() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de");
        alerta.setHeaderText("LabPresta v1.0");
        alerta.setContentText("Sprint 1 - Escenario C");
        alerta.showAndWait();
    }

    @FXML
    private void salir() {
        Platform.exit();
    }
    
    private final List<Equipo> equipos = ArchivoEquipos.leer();
    private final List<Usuario> usuarios = new ArrayList<>(new UsuarioCSV().cargar());



    /** Reemplaza el panel central por un marcador de la pantalla que irá ahí. */
    private void mostrarMarcador(String titulo, String historia) {
        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Arial", 18));
        Label lblDetalle = new Label("Aquí irá la pantalla de " + historia);
        Button btnVolver = new Button("Volver al inicio");
        btnVolver.setOnAction(e -> mostrarInicio());

        VBox panel = new VBox(15, lblTitulo, lblDetalle, btnVolver);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-padding: 30 0 0 0;");

        raiz.setCenter(panel);
        lblEstado.setText(titulo);
    }
}
