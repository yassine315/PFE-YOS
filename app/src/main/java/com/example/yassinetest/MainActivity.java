package com.example.yassinetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.com.example.beans.MolhanotInfo;

public class MainActivity extends AppCompatActivity {

    Button inscrire ;

    MolhanotInfo molhanotInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //on recupere les info enregestrer
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
          MolhanotInfo.initialiser(SP.getString(MolhanotInfo.NOM,"null"),SP.getString(MolhanotInfo.PRENOM,"null"),SP.getInt(MolhanotInfo.ID,0),SP.getString(MolhanotInfo.PASSWORD,"null"),SP.getString(MolhanotInfo.PHONE,"null"),SP.getString(MolhanotInfo.STATUT,"null"),SP.getString(MolhanotInfo.EMAIL,"null"));




        //lors de click sur inscription , on test si l'inscription est deja effectue
        inscrire = (Button) findViewById(R.id.inscrire);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MolhanotInfo.isEmpty()) {
                    Intent secondeActivite = new Intent(MainActivity.this, Inscrire.class);


                    // Puis on lance l'intent !

                    startActivity(secondeActivite);
                } else {

                    Toast.makeText(MainActivity.this, "tu as deja inscrit", Toast.LENGTH_LONG).show();
                }

            }

        });


            Button connection = (Button)findViewById(R.id.Connection);
            connection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tele = ((EditText)findViewById(R.id.loginConnection)).getText().toString();
                    String password = ((EditText)findViewById(R.id.passwordConnection)).getText().toString();

                    if(tele.equals(MolhanotInfo.getPhone()) && password.equals(MolhanotInfo.getPassword())){
                        Intent intent = new Intent(MainActivity.this, ResultaMolhanot.class);

                        startActivity(intent);
                    }
                    else{
                        if(MolhanotInfo.getPhone().equals("null") && MolhanotInfo.getPassword().equals("null")){
                            Toast.makeText(MainActivity.this, "erreure de connecxion, tu dois s'inscrir", Toast.LENGTH_LONG).show();

                        }
                        else {
                        ((EditText)findViewById(R.id.loginConnection)).setText("");
                        ((EditText)findViewById(R.id.passwordConnection)).setText("");
                        Toast.makeText(MainActivity.this, "erreure de connecxion , etes vous sur que vous etes le proprietaire de ce tele", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
    }

/*
    @Override
    protected void onStart() {
        super.onStart();



        inscrire = (Button) findViewById(R.id.inscrire);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                if (MolhanotInfo.isEmpty()) {
                    Intent secondeActivite = new Intent(MainActivity.this, Inscrire.class);


                    // Puis on lance l'intent !
                    startActivity(secondeActivite);
                } else {

                    Toast.makeText(MainActivity.this, "tu as deja inscrit", Toast.LENGTH_LONG).show();
                }

            }
        });
    }*/
}
