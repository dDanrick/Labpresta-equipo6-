module labpresta {
    requires javafx.controls;
    requires javafx.fxml;

    opens labpresta to javafx.fxml;
    exports labpresta;
}
