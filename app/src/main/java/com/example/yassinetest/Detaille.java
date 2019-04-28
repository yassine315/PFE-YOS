package com.example.yassinetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.com.example.beans.Achat;
import com.example.dao.DaoAchat;
import com.example.dao.DaoMolhanot;
import com.example.util.AdapterAchat;
import com.example.util.DialogAddClient;

import java.util.ArrayList;
import java.util.List;

public class Detaille extends AppCompatActivity {

    private DaoAchat daoAchat;
    private Toolbar toolbarDetaille;
    private int idClient;
    private final int idMolahanot = 1;
    ListView listView;
    AdapterAchat adapterAchat ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaille);

        idClient = getIntent().getIntExtra("idClient", -1);
        //((TextView)findViewById(R.id.label_client)).setText(getIntent().getCharSequenceExtra("nomClient"));

        daoAchat = new DaoAchat(this);

        toolbarDetaille = findViewById(R.id.toolbar_detaille);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        layoutInflater.inflate(R.layout.form_ajout_achat, toolbarDetaille);
        setSupportActionBar(toolbarDetaille);
    }


    @Override
    protected void onStart() {
        super.onStart();

        List<Achat> listAchats = daoAchat.aficherAchats(idClient,idMolahanot);
         adapterAchat = new AdapterAchat(this,android.R.layout.simple_list_item_1,listAchats);
        listView = findViewById(R.id.list_achats);
        listView.setAdapter(adapterAchat);
        ((TextView)findViewById(R.id.somme_prix)).setText(Float.toString(adapterAchat.sommeAchats()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detaille, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ajouter_achat) {
            String nom = ((EditText) findViewById(R.id.input_achat)).getText().toString();
            String strFloat = ((EditText)findViewById(R.id.input_prix_achat)).getText().toString();
            float prix = Float.parseFloat(strFloat.equals("")?"0":strFloat);
            String quantite = ((EditText) findViewById(R.id.input_quantite)).getText().toString();

            Achat achat = new Achat(nom, quantite, prix, -1, idClient, idMolahanot);
            if(achat.achaValide()) {
                ((EditText) findViewById(R.id.input_achat)).setText("");
                ((EditText) findViewById(R.id.input_prix_achat)).setText("");
                ((EditText) findViewById(R.id.input_quantite)).setText("");
                daoAchat.ajouterAchat(achat);
                adapterAchat.add(achat);
                listView.setAdapter(adapterAchat);
                ((TextView)findViewById(R.id.somme_prix)).setText(Float.toString(adapterAchat.getSomme()));
                }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
