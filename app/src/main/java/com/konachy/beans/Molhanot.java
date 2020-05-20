package com.konachy.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Molhanot extends User {

    public static String SELLERS = "Sellers";

    private Map<String,User> clients;

    public Molhanot(String name, boolean seller, String phone, long date, Map<String, User> listClient) {
        super(name, seller, phone, date);
        this.clients = listClient;
    }

    public Molhanot() {
        this.clients = new HashMap<String, User>();
    }
}
