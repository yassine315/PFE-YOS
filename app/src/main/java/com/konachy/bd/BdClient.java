package com.konachy.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BdClient extends SQLiteOpenHelper {


    public static  final String TABLE_ACHATS = "achats";
    public static  final String NOM_ACHAT = "nom";
    public static  final String ID_ACHAT = "id";
    public static  final String QUANTITE_ACHAT = "quantite";
    public static  final String ID_CLIENT_ACHAT = "id_client";
    public static  final String ID_MOLHANOT_ACHAT = "id_molhanot";
    public static  final String DATE = "date";


    public static final String SQL_ACHATS =
            "CREATE TABLE " + TABLE_ACHATS + " (" +
                    ID_ACHAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM_ACHAT + " TEXT, " +
                    QUANTITE_ACHAT + " TEXT, " +
                    ID_MOLHANOT_ACHAT + " INTEGER, " +
                    ID_CLIENT_ACHAT + " INTEGER, " +

                    DATE + " TEXT ," ;



    public BdClient(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
