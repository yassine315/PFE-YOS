package com.konachy.com.example.beans;

public class Achat {
    private String nom ;
    private String quantite ;
    private float prix;
    private int id;
    private int idClient;
    private int idMolhanot;

    public Achat(String nom, String quantite, float prix, int id, int idClient, int idMolhanot) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
        this.id = id;
        this.idClient = idClient;
        this.idMolhanot = idMolhanot;
    }

    public String getNom() {
        return nom;
    }

    public String getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdMolhanot() {
        return idMolhanot;
    }
    public boolean achaValide(){
        if(!nom.equals("")&&prix!=0)return true;
        return false;
    }
}
