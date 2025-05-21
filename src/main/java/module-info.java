module org.example.kolikter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens org.example.kolikter to javafx.fxml;
    opens org.example.kolikter.model to javafx.base; // ← МІНЕ ОСЫ ҚАЖЕТ
    exports org.example.kolikter;
    exports org.example.kolikter.model;
    exports org.example.kolikter.services;
    opens org.example.kolikter.services to javafx.fxml;
    exports org.example.kolikter.controller;
    opens org.example.kolikter.controller to javafx.fxml;
}
