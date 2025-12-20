package com.example.gestionetudiants.DAO;

import com.example.gestionetudiants.model.Personnel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO {
    private Connection c;

    public PersonnelDAO() {
        this.c = DatabaseConnection.getConnection();
    }

    public boolean insert(Personnel personnel) {
        String sql = "INSERT INTO personnel(nom) VALUES(?)";
        try (PreparedStatement statement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, personnel.getNom());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    personnel.setId(generatedId);
                }
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM personnel WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Personnel personnel) {
        String sql = "UPDATE personnel SET nom = ? WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, personnel.getNom());
            statement.setInt(2, personnel.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Personnel> selectAll() {
        List<Personnel> personnelList = new ArrayList<>();
        String sql = "SELECT * FROM personnel";

        try (Statement statement = c.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Personnel personnel = new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
                personnelList.add(personnel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnelList;
    }

    public Personnel selectById(int id) {
        String sql = "SELECT * FROM personnel WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}