package com.konachy.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.konachy.com.example.beans.Carne;
import com.konachy.dao.DaoAchat;
import com.example.yassinetest.R;

public class DialogSupCarne extends DialogFragment {

    DaoAchat daoAchat;
    Carne carne;
    AdapterAchat adapterAchat;
    View viewSomme;

    public void initialiser(Carne carne,DaoAchat daoAchat , AdapterAchat adapterAchat, View viewSomme) {
        this.carne = carne;
        this.daoAchat = daoAchat;
        this.adapterAchat = adapterAchat;
        this.viewSomme = viewSomme;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("etes-vous sur de suprimer ce carne");



        builder.setPositiveButton(R.string.menu_delete, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                daoAchat.suprimrCarne(carne);
                adapterAchat.clear();
                ((TextView)viewSomme).setText(Float.toString(adapterAchat.getSomme()));
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
