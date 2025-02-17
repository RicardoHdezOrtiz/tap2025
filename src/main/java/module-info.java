module com.example.tap2025 {
    requires javafx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.fxml;
    requires java.scripting;

    opens com.example.tap2025 to javafx.fxml;
    exports com.example.tap2025;
}