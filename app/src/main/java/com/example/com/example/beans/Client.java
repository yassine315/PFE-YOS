package com.example.com.example.beans;

import java.util.Date;

public class Client {

    private int id;
    private String nom ;
    private String prenom ;
    private int idMolhanot ;
    private String phone ;
    private Date date;
    private String cni;

    public Client(int id, String nom, String prenom, int idMolhanot, String phone, Date date, String cni) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idMolhanot = idMolhanot;
        this.phone = phone;
        this.date = date;
        this.cni = cni;
    }

    public int getIdMolhanot() {
        return idMolhanot;
    }

    public String getCni() {
        return cni;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", idMolhanot=" + idMolhanot +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                ", cni='" + cni + '\'' +
                '}';
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(Date date) {
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

    public String getPhone() {
        return phone;
    }

    public Date getDate() {
        return date;
    }
}
