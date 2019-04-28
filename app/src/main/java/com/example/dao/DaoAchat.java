package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bd.Bd;
import com.example.bd.BdMolhanot;
import com.example.com.example.beans.Achat;
import com.example.com.example.beans.MolhanotInfo;

import java.util.ArrayList;
import java.util.List;

public class DaoAchat {

    private static Context context;

    public DaoAchat(Context context) {

        this.context = context;
    }

    public  long ajouterAchat(Achat achat){

        BdMolhanot bd = new BdMolhanot(context , MolhanotInfo.DB,null, 1);
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

    public List<Achat> aficherAchats(int idClientP,int idMolhanotP){

        BdMolhanot bd = new BdMolhanot(context , MolhanotInfo.DB,null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();

        Cursor cursor = bdsql.query(bd.TABLE_ACHATS,new String[]{bd.ID_ACHAT,bd.NOM_ACHAT,bd.QUANTITE_ACHAT,bd.PRIX},bd.ID_CLIENT_ACHAT+"="+idClientP,null,null,null,null);
        ArrayList<Achat> rs = new ArrayList<Achat>();
        while (cursor.moveToNext()){
            Achat achat = new Achat(cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getInt(0),idClientP,idMolhanotP);
            rs.add(achat);
        }
        cursor.close();
        bdsql.close();
        bd.close();
        return rs;
    }
}
