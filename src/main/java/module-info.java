module org.example.kolikter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    opens org.example.kolikter to javafx.fxml;
    opens org.example.kolikter.model to javafx.base; // ← МІНЕ ОСЫ ҚАЖЕТ
    exports org.example.kolikter;
    exports org.example.kolikter.model;
}
