package com.konachy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.konachy.com.example.beans.Achat;
import com.konachy.com.example.beans.Carne;
import com.konachy.dao.DaoAchat;
import com.konachy.util.AdapterAchat;
import com.konachy.util.DialogSupCarne;
import com.example.yassinetest.R;

public class Detaille extends AppCompatActivity {

    private DaoAchat daoAchat;
    private Toolbar toolbarDetaille;
    private Carne carne;
    private final int idMolahanot = 1;
    ListView listView;
    AdapterAchat adapterAchat ;
    private String nomClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaille);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        int idClient = extra.getInt("idClient");
        nomClient = extra.getString("nom");

        carne = new Carne(idMolahanot,idClient,null,null);
        //((TextView)findViewById(R.id.label_client)).setText(nomClient);

        daoAchat = new DaoAchat(this);

        toolbarDetaille = findViewById(R.id.toolbar_detaille);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        layoutInflater.inflate(R.layout.form_ajout_achat, toolbarDetaille);
        setSupportActionBar(toolbarDetaille);
    }


    @Override
    protected void onStart() {
        super.onStart();

        daoAchat.aficherAchats(carne);
        adapterAchat = new AdapterAchat(this,android.R.layout.simple_list_item_1,carne.getListAchats());
        listView = findViewById(R.id.list_achats);
        listView.setAdapter(adapterAchat);
        ((TextView)findViewById(R.id.somme_prix)).setText(Float.toString(adapterAchat.sommeAchats()));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                adapterAchat.addChecked(i);
                adapterAchat.notifyDataSetChanged();

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_suprimer,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                SparseBooleanArray checked = adapterAchat.getChecked();
                switch (menuItem.getItemId()) {
                    case R.id.delete_achats:
                        for(int i =0;i<checked.size();i++){
                            Achat achat = adapterAchat.getItem(checked.keyAt(i));
                            daoAchat.suprimerAchat(achat.getId());


                        }
                        adapterAchat.deleteCheckeds();
                        listView.setAdapter(adapterAchat);
                        ((TextView)findViewById(R.id.somme_prix)).setText(Float.toString(adapterAchat.getSomme()));
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                adapterAchat.getChecked().clear();
                adapterAchat.notifyDataSetChanged();

            }
        });
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

            Achat achat = new Achat(nom, quantite, prix, -1,carne.getIdClient(), carne.getIdMolhanot());
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
        else  if(id == R.id.action_suprimer_carne){
            DialogSupCarne dialogSupCarne = new DialogSupCarne();
            dialogSupCarne.initialiser(carne,daoAchat,adapterAchat,findViewById(R.id.somme_prix));
            dialogSupCarne.show(getFragmentManager(),null);
            listView.setAdapter(adapterAchat);
        }

        return super.onOptionsItemSelected(item);
    }

    //menu pour suprimer
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_achats) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_suprimer, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.add:
                Toast.makeText(Detaille.this, "ajouter", Toast.LENGTH_LONG).show();

                // add stuff here
                return true;
            case R.id.delete:
                Toast.makeText(Detaille.this, "suprimr", Toast.LENGTH_LONG).show();
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/
}
