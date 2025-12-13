package com.example.gestionetudiants.DAO;

import com.example.gestionetudiants.model.Student;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO
{
        /* creer la connection avec la base de donnees
        ensuite  le constructeur
        la methode insert
        la mathode update
        delete
        select all pour lister
        select by id
         */

    private Connection c;
    public  void StudentDAO()
    {
        this.c = DatabaseConnection.getConnection();
    }
    public boolean insert(Student student)
    {
        String sql = "INSERT INTO student(nom,prenom, date_naissance,email,sexe) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, student.getNom());
            statement.setString(2, student.getPrenom());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getSexe());

            java.sql.Date sqlDate = new java.sql.Date(student.getDate_naissance().getTime());
            statement.setDate(3, sqlDate); // parce qu'il faut convertir la date Java normale en date SQL
            // sinon il y aura conflit

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    String generatedId = rs.getString(1);
                    student.setId(generatedId); // pour pouvoir mettre à jour l'objet student
                }
            }

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id){
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
        try (PreparedStatement statement = c.prepareStatement(sql))
        {
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



}

