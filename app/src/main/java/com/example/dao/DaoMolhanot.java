package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bd.BdMolhanot;
import com.example.com.example.beans.Client;
import com.example.com.example.beans.MolhanotInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaoMolhanot {
    private static Context context;

    public DaoMolhanot(Context context) {
        this.context = context;
    }

    public  long ajouterClient(Client client ){
        BdMolhanot bd = new BdMolhanot( context, MolhanotInfo.DB,null,1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(bd.NOM_CLIENT,client.getNom());
        contentValues.put(bd.PRENOM_CLIENT,client.getPrenom());
        contentValues.put(bd.PHONE_CLIENT,client.getPhone());
        contentValues.put(bd.DATE,client.getDate().toString());
        contentValues.put(bd.ID_MOLHANOT,client.getIdMolhanot());

        long row = bdsql.insert( bd.TABLE_CLIENT, null , contentValues);

        bdsql.close();
        bd.close();

        return row;
    }

    public List<Client> aficher() {
        //ArrayList<Molhanot> list = new ArrayList<Molhanot>();
        BdMolhanot bd = new BdMolhanot(context, MolhanotInfo.DB, null, 1);
        SQLiteDatabase bdsql = bd.getWritableDatabase();
        Cursor cursor =  bdsql.query(bd.TABLE_CLIENT, new String[]{bd.ID_CLIENT,bd.NOM_CLIENT,bd.PRENOM_CLIENT,bd.PHONE_CLIENT,bd.DATE,bd.CNI}, "", null, null, null, null);


        ArrayList<Client> rs = new ArrayList<>();
        while(cursor.moveToNext()) {
            Client m = new Client(cursor.getInt(0), cursor.getString(1), cursor.getString(2),0, cursor.getString(3), new Date(),cursor.getString(5));
            rs.add(m);
        }
        cursor.close();
        bdsql.close();
        bd.close();
    return rs ;
    }


}
