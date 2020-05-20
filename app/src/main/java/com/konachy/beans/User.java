package com.konachy.beans;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    public static final  String URL_IMAGE = "url_image";
    public static final String PATH_USERS = "/users";
    protected String name;
    protected String phone;
    protected boolean seller;
    protected long date;
    protected Uri urlImage;

    public User( String name, boolean seller,String phone,long date) {
        this.name = name;
        this.phone = phone;
        this.seller = seller;
        this.date = date;
    }

    public User(Map<String,String> map){
        if(!map.isEmpty())  {
            name = map.get("name");
            phone = map.get("phone");
            seller = Boolean.parseBoolean(map.get("seller"));
            Long time = Long.parseLong(map.get("date"));
            if(time != null)date = time;
        }
    }

    public User() {
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> structure = new HashMap<String, Object>();
        structure.put("name",name);
        structure.put("phone",phone);
        structure.put("seller",Boolean.toString(seller));
        structure.put("date",Long.toString(date));
        structure.put("urlImage", urlImage);
        return structure;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    public long getDate() {
        return date;
    }

    public Uri getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(Uri urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", seller=" + seller +
                ", date=" + date +
                ", urlImage=" + urlImage +
                '}';
    }
}

