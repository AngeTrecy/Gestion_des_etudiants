module com.example.gestionetudiants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.gestionetudiants to javafx.fxml;
    opens com.example.gestionetudiants.controller to javafx.fxml;
    opens com.example.gestionetudiants.model to javafx.base;

    exports com.example.gestionetudiants;
    exports com.example.gestionetudiants.controller;
    exports com.example.gestionetudiants.model;
}