module com.example.tap2025 {
    requires javafx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.fxml;
    requires java.scripting;

    opens com.example.tap2025 to javafx.fxml;
    exports com.example.tap2025;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    opens com.example.tap2025.modelos;
}