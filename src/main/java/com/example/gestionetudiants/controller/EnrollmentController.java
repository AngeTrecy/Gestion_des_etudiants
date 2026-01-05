package com.example.gestionetudiants.controller;

import com.example.gestionetudiants.DAO.EnrollmentDAO;
import com.example.gestionetudiants.DAO.StudentDAO;
import com.example.gestionetudiants.DAO.ClassroomDAO;
import com.example.gestionetudiants.model.Enrollment;
import com.example.gestionetudiants.model.Student;
import com.example.gestionetudiants.model.Classroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.Optional;

public class EnrollmentController {
    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private TableColumn<Enrollment, Integer> colId;
    @FXML private TableColumn<Enrollment, String> colStudent;
    @FXML private TableColumn<Enrollment, String> colClassroom;
    @FXML private TableColumn<Enrollment, Integer> colYear;
    @FXML private TableColumn<Enrollment, LocalDate> colDateInscription;

    @FXML private ComboBox<Student> cbStudent;
    @FXML private ComboBox<Classroom> cbClassroom;
    @FXML private TextField txtYear;
    @FXML private DatePicker dateInscription;
    @FXML private TextField txtSearch;

    private EnrollmentDAO enrollmentDAO;
    private StudentDAO studentDAO;
    private ClassroomDAO classroomDAO;
    private ObservableList<Enrollment> enrollmentList;

    @FXML
    public void initialize() {
        enrollmentDAO = new EnrollmentDAO();
        studentDAO = new StudentDAO();
        classroomDAO = new ClassroomDAO();

        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudent.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getStudent().getNom() + " " +
                                cellData.getValue().getStudent().getPrenom()
                )
        );
        colClassroom.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getClassroom().getNom()
                )
        );
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colDateInscription.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        // Charger les données dans les ComboBox
        loadComboBoxes();
        loadEnrollments();

        // Listener pour remplir les champs lors de la sélection
        enrollmentTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayEnrollmentDetails(newSelection);
                    }
                }
        );
    }

    @FXML
    private void handleAdd() {
        if (validateInput()) {
            Enrollment enrollment = new Enrollment(
                    0,
                    cbStudent.getValue(),
                    cbClassroom.getValue(),
                    Integer.parseInt(txtYear.getText()),
                    dateInscription.getValue()
            );

            if (enrollmentDAO.insert(enrollment)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription ajoutée avec succès!");
                clearFields();
                loadEnrollments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout de l'inscription.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une inscription.");
            return;
        }

        if (validateInput()) {
            selected.setStudent(cbStudent.getValue());
            selected.setClassroom(cbClassroom.getValue());
            selected.setYear(Integer.parseInt(txtYear.getText()));
            selected.setDateInscription(dateInscription.getValue());

            if (enrollmentDAO.update(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription mise à jour avec succès!");
                clearFields();
                loadEnrollments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        Enrollment selected = enrollmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une inscription.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Confirmer la suppression",
                "Voulez-vous vraiment supprimer cette inscription ?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (enrollmentDAO.delete(selected.getId())) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription supprimée avec succès!");
                clearFields();
                loadEnrollments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = txtSearch.getText();
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            loadEnrollments();
        } else {
            enrollmentList = FXCollections.observableArrayList(
                    enrollmentDAO.searchByStudentName(searchTerm)
            );
            enrollmentTable.setItems(enrollmentList);
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void loadEnrollments() {
        enrollmentList = FXCollections.observableArrayList(enrollmentDAO.selectAll());
        enrollmentTable.setItems(enrollmentList);
    }

    private void loadComboBoxes() {
        // Charger les étudiants
        ObservableList<Student> students = FXCollections.observableArrayList(studentDAO.selectAll());
        cbStudent.setItems(students);

        // Charger les classes
        ObservableList<Classroom> classrooms = FXCollections.observableArrayList(classroomDAO.selectAll());
        cbClassroom.setItems(classrooms);
    }

    private void displayEnrollmentDetails(Enrollment enrollment) {
        cbStudent.setValue(enrollment.getStudent());
        cbClassroom.setValue(enrollment.getClassroom());
        txtYear.setText(String.valueOf(enrollment.getYear()));
        dateInscription.setValue(enrollment.getDateInscription());
    }

    private void clearFields() {
        cbStudent.setValue(null);
        cbClassroom.setValue(null);
        txtYear.clear();
        dateInscription.setValue(null);
        txtSearch.clear();
        enrollmentTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        if (cbStudent.getValue() == null || cbClassroom.getValue() == null ||
                txtYear.getText().isEmpty() || dateInscription.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir tous les champs.");
            return false;
        }

        try {
            Integer.parseInt(txtYear.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Attention", "L'année doit être un nombre valide.");
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