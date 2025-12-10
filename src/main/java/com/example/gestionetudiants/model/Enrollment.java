package com.example.gestionetudiants.model;
import java.time.LocalDate;
import java.util.Date;

public class Enrollment {
    int id;
    Student student;
    Classroom classroom;
    int year;
    LocalDate dateInscription;

    public Enrollment(){

    }
    public Enrollment(int id , Student student, Classroom classroom,int year,LocalDate dateInscription){
        this.id= id;
        this.student=student;
        this.classroom = classroom;
        this.year=year;
        this.dateInscription= dateInscription;

    }
    public Enrollment( Student student, Classroom classroom,int year,LocalDate dateInscription){
        this.student=student;
        this.classroom = classroom;
        this.year=year;
        this.dateInscription= dateInscription;

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return student;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    public Classroom getClassroom() {
        return classroom;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
    public LocalDate getDateInscription() {
        return dateInscription;
    }

}
