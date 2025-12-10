module com.example.gestionetudiants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.gestionetudiants to javafx.fxml;
    opens com.example.gestionetudiants.DAO to javafx.fxml;

    exports com.example.gestionetudiants;
    exports com.example.gestionetudiants.DAO;
}
