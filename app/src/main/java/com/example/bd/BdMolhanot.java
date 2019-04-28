package com.example.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BdMolhanot extends SQLiteOpenHelper {

    public static  final String TABLE_CLIENT = "clients";
    public static  final String ID_CLIENT = "id";
    public static  final String NOM_CLIENT = "nom";
    public static  final String PRENOM_CLIENT = "prenom";
    public static  final String PHONE_CLIENT = "tele";
    public static  final String ID_MOLHANOT = "id_molhanot";
    public static  final String DATE = "date";
    public static  final String CNI = "cni";


    public static  final String TABLE_ACHATS = "achats";
    public static  final String NOM_ACHAT = "nom";
    public static  final String ID_ACHAT = "id";
    public static  final String PRIX = "prix";
    public static  final String QUANTITE_ACHAT = "quantite";
    public static  final String ID_CLIENT_ACHAT = "id_client";
    public static  final String ID_MOLHANOT_ACHAT = "id_molhanot";


    public static final String SQL_CLIENT =
            "CREATE TABLE " + TABLE_CLIENT + " (" +
                    ID_CLIENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM_CLIENT + " TEXT, " +
                    PRENOM_CLIENT + " TEXT, " +
                    PHONE_CLIENT + " TEXT, " +
                    ID_MOLHANOT + " INTEGER, " +
                    DATE + " TEXT," +
                    CNI +" TEXT );";

    public static final String SQL_ACHATS =
            "CREATE TABLE " + TABLE_ACHATS + " (" +
                    ID_ACHAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM_ACHAT + " TEXT, " +
                    QUANTITE_ACHAT + " TEXT, " +
                    ID_MOLHANOT_ACHAT + " INTEGER, " +
                    ID_CLIENT_ACHAT + " INTEGER, " +
                    PRIX +" REAL,"+
                    DATE + " TEXT ," +
                    "FOREIGN KEY("+ID_CLIENT_ACHAT+") REFERENCES "+TABLE_CLIENT+"("+ID_CLIENT+"));";



    public BdMolhanot(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CLIENT);
        sqLiteDatabase.execSQL(SQL_ACHATS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
