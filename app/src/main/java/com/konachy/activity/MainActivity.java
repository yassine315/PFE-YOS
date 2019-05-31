package com.konachy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.konachy.com.example.beans.Compte;
import com.example.yassinetest.R;

public class MainActivity extends AppCompatActivity {

    Button inscrire ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //on recupere les info enregestrer
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
          Compte.initialiser(SP.getString(Compte.NOM,"null"),SP.getString(Compte.PRENOM,"null"),SP.getInt(Compte.ID,0),SP.getString(Compte.PASSWORD,"null"),SP.getString(Compte.PHONE,"null"),SP.getString(Compte.STATUT,"null"),SP.getString(Compte.EMAIL,"null"));




        //lors de click sur inscription , on test si l'inscription est deja effectue
        inscrire = (Button) findViewById(R.id.inscrire);
        inscrire.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (Compte.isEmpty()) {
                    Intent secondeActivite = new Intent(MainActivity.this, Inscrire.class);
                    // Puis on lance l'intent !

                    startActivity(secondeActivite);
                } else {

                    Toast.makeText(MainActivity.this, "Tu as déjà inscrit.", Toast.LENGTH_LONG).show();
                }

            }

        });


            Button connection = (Button)findViewById(R.id.Connection);
            connection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tele = ((EditText)findViewById(R.id.loginConnection)).getText().toString();
                    String password = ((EditText)findViewById(R.id.passwordConnection)).getText().toString();

                    if(tele.equals(Compte.getPhone()) && password.equals(Compte.getPassword()) ){
                        if(Compte.getStatut().equals("molhanot") ){
                            Intent intent = new Intent(MainActivity.this, ResultaMolhanot.class);

                            startActivity(intent);
                            finish();
                        }
                        else  if(Compte.getStatut().equals("client")){
                        }
                    }
                    else{
                        if(Compte.getPhone().equals("null") && Compte.getPassword().equals("null")){
                            Toast.makeText(MainActivity.this, "erreur de connexion, tu dois s'inscrire", Toast.LENGTH_LONG).show();

                        }
                        else {
                        ((EditText)findViewById(R.id.loginConnection)).setText("");
                        ((EditText)findViewById(R.id.passwordConnection)).setText("");
                        Toast.makeText(MainActivity.this, "Erreur de connexion, êtes-vous sûr que vous êtes le propriétaire de ce télé !!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
    }


}
