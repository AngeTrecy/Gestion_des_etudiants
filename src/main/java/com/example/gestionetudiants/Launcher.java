package com.example.gestionetudiants;
import com.example.gestionetudiants.DAO.DatabaseConnection;
import javafx.application.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) {
        DatabaseConnection.getConnection();
    }
}
