package com.example.gestionetudiants.controller;

import com.example.gestionetudiants.DAO.ClassroomDAO;
import com.example.gestionetudiants.model.Classroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Optional;

public class ClassroomController {
    @FXML private TableView<Classroom> classroomTable;
    @FXML private TableColumn<Classroom, Integer> colId;
    @FXML private TableColumn<Classroom, String> colNom;
    @FXML private TableColumn<Classroom, String> colNiveau;

    @FXML private TextField txtNom;
    @FXML private TextField txtNiveau;

    private ClassroomDAO classroomDAO;
    private ObservableList<Classroom> classroomList;

    @FXML
    public void initialize() {
        classroomDAO = new ClassroomDAO();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));

        loadClassrooms();

        // Ecouteur de sélection pour remplir les champs quand on clique sur une ligne
        classroomTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNom.setText(newSelection.getNom());
                        txtNiveau.setText(newSelection.getNiveau());
                    }
                }
        );
    }

    // CETTE MÉTHODE corrige l'erreur de l'image 2
    @FXML
    private void handleAdd() {
        if (txtNom.getText().isEmpty() || txtNiveau.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        Classroom classroom = new Classroom(0, txtNom.getText(), txtNiveau.getText());
        if (classroomDAO.insert(classroom)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Classe ajoutée !");
            clearFields();
            loadClassrooms();
        }
    }

    @FXML
    private void handleUpdate() {
        Classroom selected = classroomTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setNiveau(txtNiveau.getText());
            if (classroomDAO.update(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Mise à jour réussie !");
                loadClassrooms();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Classroom selected = classroomTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (classroomDAO.delete(selected.getId())) {
                loadClassrooms();
                clearFields();
            }
        }
    }

    private void loadClassrooms() {
        classroomList = FXCollections.observableArrayList(classroomDAO.selectAll());
        classroomTable.setItems(classroomList);
    }

    private void clearFields() {
        txtNom.clear();
        txtNiveau.clear();
        classroomTable.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}