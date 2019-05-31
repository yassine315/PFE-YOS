package com.konachy.com.example.beans;

import java.util.Date;
import java.util.List;

public class Carne {
    private int idMolhanot;
    private int idClient;
    private List<Achat> listAchats;
    private Date date;

    public Carne(int idMolhanot, int idClient, List<Achat> listAchats, Date date) {
        this.idMolhanot = idMolhanot;
        this.idClient = idClient;
        this.listAchats = listAchats;
        this.date = date;
    }

    public int getIdMolhanot() {
        return idMolhanot;
    }

    public int getIdClient() {
        return idClient;
    }

    public List<Achat> getListAchats() {
        return listAchats;
    }

    public Date getDate() {
        return date;
    }


    public void setIdMolhanot(int idMolhanot) {
        this.idMolhanot = idMolhanot;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setListAchats(List<Achat> listAchats) {
        this.listAchats = listAchats;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void remove(Achat achat){
        listAchats.remove(achat);
    }





}
