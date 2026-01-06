package com.example.gestionetudiants.controller;

import com.example.gestionetudiants.DAO.StudentDAO;
import com.example.gestionetudiants.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class StudentController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colId;
    @FXML private TableColumn<Student, String> colNom;
    @FXML private TableColumn<Student, String> colPrenom;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, Date> colDateNaissance;
    @FXML private TableColumn<Student, String> colSexe;

    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtEmail;
    @FXML private DatePicker dateNaissance;
    @FXML private ComboBox<String> cbSexe;
    @FXML private TextField txtSearch;

    private StudentDAO studentDAO;
    private ObservableList<Student> studentList;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));

        cbSexe.setItems(FXCollections.observableArrayList("M", "F"));

        loadStudents();

        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayStudentDetails(newSelection);
                    }
                }
        );
    }

    @FXML
    private void handleAdd() {
        if (validateInput()) {
            Student student = new Student(
                    txtNom.getText(),
                    txtPrenom.getText(),
                    txtEmail.getText(),
                    Date.from(dateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    cbSexe.getValue()
            );

            if (studentDAO.insert(student)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant ajouté avec succès!");
                clearFields();
                loadStudents();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout de l'étudiant.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un étudiant.");
            return;
        }

        if (validateInput()) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            selected.setDate_naissance(Date.from(dateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            selected.setSexe(cbSexe.getValue());

            if (studentDAO.update(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant mis à jour avec succès!");
                clearFields();
                loadStudents();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un étudiant.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmer la suppression",
                "Voulez-vous vraiment supprimer cet étudiant ?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (studentDAO.delete(selected.getId())) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant supprimé avec succès!");
                clearFields();
                loadStudents();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = txtSearch.getText();
        if (searchTerm.isEmpty()) {
            loadStudents();
        } else {
            studentList = FXCollections.observableArrayList(studentDAO.searchByName(searchTerm));
            studentTable.setItems(studentList);
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void loadStudents() {
        studentList = FXCollections.observableArrayList(studentDAO.selectAll());
        studentTable.setItems(studentList);
    }

    private void displayStudentDetails(Student student) {
        txtNom.setText(student.getNom());
        txtPrenom.setText(student.getPrenom());
        txtEmail.setText(student.getEmail());
        if (student.getDate_naissance() != null) {
            dateNaissance.setValue(student.getDate_naissance().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        }
        cbSexe.setValue(student.getSexe());
    }

    private void clearFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        dateNaissance.setValue(null);
        cbSexe.setValue(null);
        studentTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || dateNaissance.getValue() == null ||
                cbSexe.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir tous les champs.");
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