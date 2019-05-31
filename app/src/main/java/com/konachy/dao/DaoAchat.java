package com.konachy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.konachy.bd.BdMolhanot;
import com.konachy.com.example.beans.Achat;
import com.konachy.com.example.beans.Carne;
import com.konachy.com.example.beans.Compte;

import java.util.ArrayList;

public class DaoAchat {

    private static Context context;

    public DaoAchat(Context context) {

        this.context = context;
    }

    public  long ajouterAchat(Achat achat){

        BdMolhanot bd = new BdMolhanot(context , Compte.DB,null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(bd.NOM_ACHAT,achat.getNom());
        contentValues.put(bd.QUANTITE_ACHAT,achat.getQuantite());
        contentValues.put(bd.PRIX,achat.getPrix());
        contentValues.put(bd.ID_CLIENT_ACHAT,achat.getIdClient());
        contentValues.put(bd.ID_MOLHANOT_ACHAT,achat.getIdMolhanot());

        long rs = bdsql.insert(bd.TABLE_ACHATS,null,contentValues);

    return  rs;
    }

    public Carne aficherAchats(Carne carne){

        BdMolhanot bd = new BdMolhanot(context , Compte.DB,null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();

        Cursor cursor = bdsql.query(bd.TABLE_ACHATS,new String[]{bd.ID_ACHAT,bd.NOM_ACHAT,bd.QUANTITE_ACHAT,bd.PRIX},bd.ID_CLIENT_ACHAT+"="+carne.getIdClient(),null,null,null,null);
        ArrayList<Achat> rs = new ArrayList<Achat>();
        while (cursor.moveToNext()){
            Achat achat = new Achat(cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getInt(0),carne.getIdClient(),carne.getIdMolhanot());
            rs.add(achat);
        }
        cursor.close();
        bdsql.close();
        bd.close();
        carne.setListAchats(rs);
        return carne;
    }

    public long suprimerAchat(int id){
        BdMolhanot bd = new BdMolhanot(context , Compte.DB,null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();

        long rs = bdsql.delete(bd.TABLE_ACHATS,bd.ID_ACHAT+" = ?",new String[]{Integer.toString(id)});

        return  rs;
    }

    public long suprimrCarne(Carne carne){


        BdMolhanot bd = new BdMolhanot(context , Compte.DB,null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();

        long rs = bdsql.delete(bd.TABLE_ACHATS,bd.ID_CLIENT_ACHAT+"= ? AND "+bd.ID_MOLHANOT_ACHAT +"= ?", new String[]{Integer.toString(carne.getIdClient()),Integer.toString(carne.getIdMolhanot())});


        return  rs;
    }

}
