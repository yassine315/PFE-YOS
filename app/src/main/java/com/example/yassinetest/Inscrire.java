package com.example.yassinetest;

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

import com.example.bd.BdMolhanot;
import com.example.com.example.beans.Molhanot;
import com.example.com.example.beans.MolhanotInfo;
import com.example.com.example.beans.StatutKonachy;
import com.example.dao.DaoMolhanot;

import java.util.Date;

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


                 SharedPreferences.Editor editor = sharedPreferences.edit();
                 //on enregestrer les donnes dans le tele
                 editor.putString(MolhanotInfo.NOM,nom);
                 editor.putString(MolhanotInfo.PRENOM,prenom);
                 editor.putString(MolhanotInfo.PASSWORD,password);
                 editor.putString(MolhanotInfo.PHONE,tele);
                 editor.putInt(MolhanotInfo.ID,1);
                 editor.putString(MolhanotInfo.STATUT,statut);
                 editor.putString(MolhanotInfo.EMAIL,email);

                 editor.commit();

                //on enregester dans MolhanotInfo
                MolhanotInfo.initialiser(sharedPreferences.getString(MolhanotInfo.NOM,"null"),sharedPreferences.getString(MolhanotInfo.PRENOM,"null"),sharedPreferences.getInt(MolhanotInfo.ID,0),sharedPreferences.getString(MolhanotInfo.PASSWORD,"null"),sharedPreferences.getString(MolhanotInfo.PHONE,"null"),sharedPreferences.getString(MolhanotInfo.STATUT,"null"),sharedPreferences.getString(MolhanotInfo.EMAIL,"null"));




                //Molhanot molhanot = new Molhanot(nom,prenom,email,tele,new Date());

                //Long ya = daoMolhanot.ajouterMolhanot(molhanot);



                        if( !(nom.contentEquals("")|| prenom.contentEquals("") || tele.contentEquals("")|| password.contentEquals(""))) {
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
