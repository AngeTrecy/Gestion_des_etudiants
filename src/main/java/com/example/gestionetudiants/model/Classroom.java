package com.example.gestionetudiants.model;

public class Classroom
{
    private int id;
    private  String nom;
    private String niveau;

    public Classroom(){}

    public Classroom(String nom, String niveau)
    {
     this.nom=nom;
     this.niveau=niveau;
    }
    public Classroom(int id , String nom, String niveau){
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNiveau(){
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
