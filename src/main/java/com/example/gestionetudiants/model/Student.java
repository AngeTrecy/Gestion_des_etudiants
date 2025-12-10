package com.example.gestionetudiants.model;
import java.util.Date;

public class Student {
    private String id;
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String email;

    public Student() {
    }

    public Student(String nom, String prenom, String email, Date date_naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.email = email;
    }
    public Student(String id, String nom, String prenom, String email, Date date_naissance){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.email = email;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public void setNom(String nom){
        this.nom=nom;
    }
    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Date getDate_naissance(){
        return date_naissance;
    }
    public void setDate_naissance(Date date_naissance){
        this.date_naissance = date_naissance;
    }


}
