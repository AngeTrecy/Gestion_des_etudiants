package com.example.gestionetudiants.model;

public class Personnel {
    int id;
    String nom;

    public Personnel(){

    }
    public Personnel(int id, String nom){
        this.id = id;
        this.nom = nom;
    }
    public  Personnel(String nom){
        this.nom = nom;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
