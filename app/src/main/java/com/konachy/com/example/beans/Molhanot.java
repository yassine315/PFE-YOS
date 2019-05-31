package com.konachy.com.example.beans;

import java.util.Date;
import java.util.List;

public class Molhanot {

    private int id;
    private String nom ;
    private String prenom;
    private  String email;
    private String tele;
    private Date date;
    private List<Client> listClient;


    public Molhanot(int id, String nom, String prenom, String email, String tele, Date date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tele = tele;
        this.date = date;
    }


    public void setListClient(List<Client> listClient) {
        this.listClient = listClient;
    }

    public List<Client> getListClient() {
        return listClient;
    }

    public Molhanot(String nom, String prenom, String email, String tele, Date date) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tele = tele;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTele() {
        return tele;
    }

    public Date getDate() {
        return date;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Molhanot{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", tele='" + tele + '\'' +
                ", date=" + date +
                '}';
    }
}
