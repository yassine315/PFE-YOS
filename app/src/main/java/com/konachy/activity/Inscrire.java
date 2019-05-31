package com.konachy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.konachy.com.example.beans.Compte;
import com.example.yassinetest.R;

public class Inscrire extends AppCompatActivity {

    Button inscrire ;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscrire);
        //DaoMolhanot daoMolhanot = new DaoMolhanot(this);
        inscrire =  findViewById(R.id.inscrireButton);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom = ((EditText)findViewById(R.id.name)).getText().toString();
                String prenom = ((EditText)findViewById(R.id.prenom)).getText().toString();
                String email = ((EditText)findViewById(R.id.email)).getText().toString();
                String tele = ((EditText)findViewById(R.id.tele)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                int id = ((RadioGroup)findViewById(R.id.statut)).getCheckedRadioButtonId();
                String statut = ((RadioButton)findViewById(id)).getText().toString();






                //Molhanot molhanot = new Molhanot(nom,prenom,email,tele,new Date());

                //Long ya = daoMolhanot.ajouterMolhanot(molhanot);



                        if( !(nom.contentEquals("")|| prenom.contentEquals("") || tele.contentEquals("")|| password.contentEquals(""))) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //on enregestrer les donnes dans le tele
                            editor.putString(Compte.NOM,nom);
                            editor.putString(Compte.PRENOM,prenom);
                            editor.putString(Compte.PASSWORD,password);
                            editor.putString(Compte.PHONE,tele);
                            editor.putInt(Compte.ID,1);
                            editor.putString(Compte.STATUT,statut);
                            editor.putString(Compte.EMAIL,email);

                            editor.commit();

                            //on enregester dans Compte
                            Compte.initialiser(sharedPreferences.getString(Compte.NOM,"null"),sharedPreferences.getString(Compte.PRENOM,"null"),sharedPreferences.getInt(Compte.ID,0),sharedPreferences.getString(Compte.PASSWORD,"null"),sharedPreferences.getString(Compte.PHONE,"null"),sharedPreferences.getString(Compte.STATUT,"null"),sharedPreferences.getString(Compte.EMAIL,"null"));



                            Intent intent = new Intent(Inscrire.this, MainActivity.class);

                            startActivity(intent);
                            finish();

                        }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
