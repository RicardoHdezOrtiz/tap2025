module com.example.tap2025 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.scripting;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;

    opens com.example.tap2025 to javafx.fxml;
    opens com.example.tap2025.modelos;

    exports com.example.tap2025;
    exports com.example.tap2025.views;
    exports com.example.tap2025.modelos;
}