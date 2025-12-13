package com.example.gestionetudiants.DAO;
import com.example.gestionetudiants.model.Personnel;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class personnelDAO {
    private Connection c;

    public void PersonnelDAO(){
        this.c = DatabaseConnection.getConnection();
    }
    public boolean insert (Personnel personnel)
    {
        String sql = "INSERT INTO personnel(nom) VALUES(?,?)";
                try (PreparedStatement statement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                    statement.setString(1, personnel.getNom());
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            int generatedId = rs.getInt(1);
                            personnel.setId(generatedId); // pour pouvoir mettre à jour l'objet student
                        }
                    }

                    return rowsInserted > 0;

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
    }

}
    }

}
