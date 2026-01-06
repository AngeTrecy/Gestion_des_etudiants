package com.example.gestionetudiants.controller;

import com.example.gestionetudiants.DAO.PersonnelDAO;
import com.example.gestionetudiants.model.Personnel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Optional;

public class PersonnelController {
    @FXML private TableView<Personnel> personnelTable;
    @FXML private TableColumn<Personnel, Integer> colId;
    @FXML private TableColumn<Personnel, String> colNom;

    @FXML private TextField txtNom;

    private PersonnelDAO personnelDAO;
    private ObservableList<Personnel> personnelList;

    @FXML
    public void initialize() {
        personnelDAO = new PersonnelDAO();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        loadPersonnel();

        personnelTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayPersonnelDetails(newSelection);
                    }
                }
        );
    }

    @FXML
    private void handleAdd() {
        if (validateInput()) {
            Personnel personnel = new Personnel(txtNom.getText());

            if (personnelDAO.insert(personnel)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Personnel ajouté avec succès!");
                clearFields();
                loadPersonnel();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du personnel.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Personnel selected = personnelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un personnel.");
            return;
        }

        if (validateInput()) {
            selected.setNom(txtNom.getText());

            if (personnelDAO.update(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Personnel mis à jour avec succès!");
                clearFields();
                loadPersonnel();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        Personnel selected = personnelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un personnel.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmer la suppression",
                "Voulez-vous vraiment supprimer ce personnel ?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (personnelDAO.delete(selected.getId())) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Personnel supprimé avec succès!");
                clearFields();
                loadPersonnel();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression.");
            }
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void loadPersonnel() {
        personnelList = FXCollections.observableArrayList(personnelDAO.selectAll());
        personnelTable.setItems(personnelList);
    }

    private void displayPersonnelDetails(Personnel personnel) {
        txtNom.setText(personnel.getNom());
    }

    private void clearFields() {
        txtNom.clear();
        personnelTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtNom.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir le champ nom.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}