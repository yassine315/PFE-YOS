package com.konachy.beans;

import java.util.HashMap;
import java.util.Map;

public class Carne {

    public static String PATH_CARNET = "/carnet";
    public static String PATH_CARNET_ARCHIVED = "/carnet_archived";
    public static String FIALD_ENABLEMODIF = "enableModif";


    private String idMolhanot;
    private String idClient;
    private Map<String, Achat> listAchats;
    private boolean enableModif;
    private float somme;
    private long date;


    public Carne(String idMolhanot, String idClient, Map<String, Achat> listAchats, long date, float somme) {
        this.idMolhanot = idMolhanot;
        this.idClient = idClient;
        this.listAchats = listAchats;
        this.date = date;
        this.somme = somme;
        this.enableModif = true;
    }

    public Carne(String idMolhanot, String idClient, long date, float somme) {
        this.idMolhanot = idMolhanot;
        this.idClient = idClient;
        this.date = date;
        this.somme = somme;
        listAchats = new HashMap<String, Achat>();
        this.enableModif = true;
    }

    public Carne() {
        listAchats = new HashMap<String, Achat>();
        this.enableModif = true;
    }

    public void setIdMolhanot(String idMolhanot) {
        this.idMolhanot = idMolhanot;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public void setListAchats(Map<String, Achat> listAchats) {
        this.listAchats = listAchats;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIdMolhanot() {
        return idMolhanot;
    }

    public String getIdClient() {
        return idClient;
    }

    public Map<String, Achat> getListAchats() {
        return listAchats;
    }

    public long getDate() {
        return date;
    }

    public float getSomme() {
        return somme;
    }

    public void setSomme(float somme) {
        this.somme = somme;
    }

    public boolean isEnableModif() {
        return enableModif;
    }

    public void setEnableModif(boolean enableModif) {
        this.enableModif = enableModif;
    }

    public void addAchat(float price){
        somme += price;
    }

}
