package com.example.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.yassinetest.R;

public class Bd extends SQLiteOpenHelper {
    public static final String TABLE = "konachy";
    public static final String TABLE_CLIENT = "clients";
    public static final String ID_CLIENT = "id";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String ID_MOLHANOT = "id_molhanot";
    public static final String PHONE = "tele";
    public static final String DATE = "date";
    public static final String SQL =
            "CREATE TABLE " + TABLE + " (" +
                    ID_CLIENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM + " TEXT, " +
                    PRENOM + " TEXT, " +
                    ID_MOLHANOT + " TEXT, " +
                    PHONE + " TEXT, " +
                    DATE + " TEXT);";
    public static final String SQL_CLIENT =
            "CREATE TABLE " + TABLE + " (" +
                    ID_CLIENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM + " TEXT, " +
                    PRENOM + " TEXT, " +
                     ID_MOLHANOT+ " TEXT, " +
                    PHONE + " TEXT, " +
                    DATE + " TEXT);";


    public Bd(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
