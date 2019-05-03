package com.example.util;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.com.example.beans.Client;
import com.example.dao.DaoMolhanot;
import com.example.yassinetest.R;

import java.util.Date;

public class DialogAddClient extends DialogFragment {
    private Client client;
    private AdabterClient adabterClient;
    private ListView listView;

    public void initialiser(AdabterClient adabterClient, ListView listView) {
        this.adabterClient = adabterClient ;
        this.listView = listView;

    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.ajouter_client, null);

        builder.setView(view);
        builder.setPositiveButton(R.string.ajouter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                String name = ((EditText) view.findViewById(R.id.boitNameClient)).getText().toString();
                String prenom = ((EditText) view.findViewById(R.id.boitPrenomClient)).getText().toString();
                String tele = ((EditText) view.findViewById(R.id.boitTele)).getText().toString();
                String cni = ((EditText) view.findViewById(R.id.boitCni)).getText().toString();

                client = new Client(0, name, prenom, 1, tele, new Date(), cni);

                DaoMolhanot daoMolhanot = new DaoMolhanot(getContext());
                if(!(name.equals("")|| prenom.equals("") || tele.equals(""))) {
                    long l = daoMolhanot.ajouterClient(client);
                    adabterClient.add(client);
                    listView.setAdapter(adabterClient);
                }


            }
        });
        builder.setNegativeButton(R.string.anuler, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
