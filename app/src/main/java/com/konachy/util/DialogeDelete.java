package com.konachy.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.konachy.activity.ui.carnet.AdapterCarnet;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.ymwsn.kounachy.R;


public class DialogeDelete extends DialogFragment {

    private ClientViewModel clientViewModel;

    public DialogeDelete(ClientViewModel clientViewModel) {
        this.clientViewModel = clientViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.message_alert_delete_all_achats)
                .setPositiveButton(R.string.commit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        clientViewModel.deleteAllAchats();

                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
