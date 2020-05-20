package com.konachy.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Client extends User {

    public static String PATH_CLIENTS = "clients";
    private String id;
    private float somme;

    //private List<String> sellers ;


    public Client() {
    }

    public Client(String id, String name, boolean seller, String phone, long date, float somme) {
        super( name, seller, phone, date);
        this.id = id;
        this.somme = somme ;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSomme(float somme) {
        this.somme = somme;
    }

    public String getId() {
        return id;
    }

    public float getSomme() {
        return somme;
    }


    public void addAchat(float price){
        somme += price;
    }

}
