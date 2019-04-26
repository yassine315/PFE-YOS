package com.example.yassinetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;


import com.example.dao.DaoMolhanot;

public class Detaille extends AppCompatActivity {

    DaoMolhanot daoMolhanot ;
    Toolbar toolbarDetaille ;
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaille);

        daoMolhanot = new DaoMolhanot(this);
        toolbarDetaille = findViewById(R.id.toolbar_detaille);
        setSupportActionBar(toolbarDetaille);
    LayoutInflater layoutInflater = LayoutInflater.from(this);

    View myCustomView = layoutInflater.inflate(R.layout.form_ajout_achat,null);

    toolbarDetaille.addView(myCustomView);
    toolbarDetaille.showContextMenu();
    }



}
