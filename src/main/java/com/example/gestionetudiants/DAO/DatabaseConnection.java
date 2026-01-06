package com.example.gestionetudiants.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static final String URL = "jdbc:mysql://localhost:3306/gestion_etudiant";
    static final String USER = "root";
    static final String PASS = "";

    public static Connection getConnection() {
        Connection c = null;

        try {
            // pour Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL chargé.");

            // verifiacation de la connexion
            c = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("📊Connexion réussie...");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("📉Échec de la connexion : " + e.getMessage());
        }

        return c;
    }
}
