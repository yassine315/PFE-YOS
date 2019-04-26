package com.example.dao;

import android.content.Context;

import com.example.bd.Bd;
import com.example.com.example.beans.MolhanotInfo;

public class DaoAchat {

    private static Context context;

    public DaoAchat(Context context) {

        this.context = context;
    }

    public  long ajouterAchat(int id){

        Bd bd = new Bd(context , MolhanotInfo.DB,null, 1);


    return  0;
    }

}
