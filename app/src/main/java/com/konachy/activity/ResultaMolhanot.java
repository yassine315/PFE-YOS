package com.konachy.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.konachy.com.example.beans.Client;
import com.konachy.com.example.beans.Compte;
import com.konachy.com.example.beans.Molhanot;
import com.konachy.dao.DaoMolhanot;
import com.konachy.util.AdabterClient;
import com.konachy.util.DialogAddClient;
import com.example.yassinetest.R;

import java.util.List;


public class ResultaMolhanot extends AppCompatActivity {

    private Toolbar mTopToolbar;
    DaoMolhanot daoMolhanot;
    Molhanot molhanot;

    private List<Client> monListC ;
    private ListView listView;
    private AdabterClient adabterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_molhanot);
        molhanot = new Molhanot(1, Compte.getNom(),Compte.getPrenom(),Compte.getEmail(),Compte.getPhone(),null);
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        daoMolhanot = new DaoMolhanot(this);
         listView = (ListView)findViewById(R.id.list_client);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resulta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_deconecter){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ajouter) {
            DialogAddClient dialogAddClient = new DialogAddClient();
            dialogAddClient.initialiser(adabterClient,listView);
            dialogAddClient.show(getSupportFragmentManager(),null);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        molhanot.setListClient( daoMolhanot.aficher());
        adabterClient = new AdabterClient(this,android.R.layout.simple_list_item_1,molhanot.getListClient());
        listView.setAdapter(adabterClient);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                            @Override
                                            public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
                                                final Client item = (Client) parent.getItemAtPosition(position);
                                                    /*
                                                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                monListC.remove(item);
                                                                adabterClient.notifyDataSetChanged();
                                                                view.setAlpha(0);
                                                            }
                                                        });
                                                       */
                                                Intent intent = new Intent(ResultaMolhanot.this,Detaille.class);
                                                intent.putExtra("nom",item.getNom());
                                                intent.putExtra("idClient",item.getId());

                                                startActivity(intent);

                                            }

                                        });




    }




}
