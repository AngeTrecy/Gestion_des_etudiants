package com.example.gestionetudiants.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        loadStudentView();
    }

    @FXML
    private void handleStudents() {
        loadStudentView();
    }

    @FXML
    private void handleClassrooms() {
        loadClassroomView();
    }

    @FXML
    private void handleEnrollments() {
        loadEnrollmentView();
    }

    @FXML
    private void handlePersonnel() {
        loadPersonnelView();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Système de Gestion des Étudiants");
        alert.setContentText(
                "Version 1.0\n\n" +
                        "Application de gestion complète pour établissements scolaires.\n\n" +
                        "Fonctionnalités :\n" +
                        "• Gestion des étudiants\n" +
                        "• Gestion des classes\n" +
                        "• Gestion des inscriptions\n" +
                        "• Gestion du personnel\n\n" +
                        "Développé avec JavaFX et MySQL\n" +
                        "© 2024"
        );
        alert.showAndWait();
    }

    private void loadStudentView() {
        loadView("/com/example/gestionetudiants/view/StudentView.fxml");
    }

    private void loadClassroomView() {
        loadView("/com/example/gestionetudiants/view/ClassroomView.fxml");
    }

    private void loadEnrollmentView() {
        loadView("/com/example/gestionetudiants/view/EnrollmentView.fxml");
    }

    private void loadPersonnelView() {
        loadView("/com/example/gestionetudiants/view/PersonnelView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement",
                    "Impossible de charger la vue : " + fxmlPath + "\n" + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}