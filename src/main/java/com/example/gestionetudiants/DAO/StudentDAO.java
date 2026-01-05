package com.example.gestionetudiants.DAO;

import com.example.gestionetudiants.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection c;

    public StudentDAO() {
        this.c = DatabaseConnection.getConnection();
    }

    public boolean insert(Student student) {
        String sql = "INSERT INTO student(id, nom, prenom, date_naissance, email, sexe) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            // Générer un ID si nécessaire
            if (student.getId() == null || student.getId().isEmpty()) {
                student.setId(generateUniqueId());
            }

            statement.setString(1, student.getId());
            statement.setString(2, student.getNom());
            statement.setString(3, student.getPrenom());
            java.sql.Date sqlDate = new java.sql.Date(student.getDate_naissance().getTime());
            statement.setDate(4, sqlDate);
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getSexe());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour générer un ID unique de 7 caractères (format: année + 3 chiffres)
    private String generateUniqueId() {
        int currentYear = java.time.Year.now().getValue();
        String yearPrefix = String.valueOf(currentYear);

        String sql = "SELECT MAX(CAST(SUBSTRING(id, 5) AS UNSIGNED)) FROM student WHERE id LIKE ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, yearPrefix + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int maxNum = rs.getInt(1);
                return String.format("%d%03d", currentYear, maxNum + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yearPrefix + "001";
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Student student) {
        String sql = "UPDATE student SET nom = ?, prenom = ?, date_naissance = ?, email = ?, sexe = ? WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, student.getNom());
            statement.setString(2, student.getPrenom());
            java.sql.Date sqlDate = new java.sql.Date(student.getDate_naissance().getTime());
            statement.setDate(3, sqlDate);
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getSexe());
            statement.setString(6, student.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> selectAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Statement statement = c.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        null,
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("date_naissance"),
                        rs.getString("sexe")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student selectById(String id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Student(
                        null,
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("date_naissance"),
                        rs.getString("sexe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> searchByName(String name) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE nom LIKE ? OR prenom LIKE ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            String searchPattern = "%" + name + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        null,
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("date_naissance"),
                        rs.getString("sexe")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}