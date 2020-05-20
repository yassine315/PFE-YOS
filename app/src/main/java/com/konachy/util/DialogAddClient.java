package com.konachy.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.konachy.beans.Carne;
import com.konachy.beans.Client;
import com.konachy.beans.User;
import com.ymwsn.kounachy.R;

import java.util.Date;

public class DialogAddClient extends DialogFragment {

    private DatabaseReference dataRefClient;
    private DatabaseReference dataRefCarnet;
    private TextInputEditText userName;
    private TextInputEditText phone;
    private String idUser;

    public static String pathAddClient;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Log.d("add client :", "dialogue box ");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View box = inflater.inflate(R.layout.box_add_customer, null);

        Bundle bundle = getArguments();

        idUser = bundle.getString("id");

        Log.d("build path ", "build path reference data base add client");
        pathAddClient = User.PATH_USERS+"/"+idUser+ClientViewModel.PATH;


        Log.d("firebase database :","get reference add client");
        dataRefClient = FirebaseDatabase.getInstance().getReference(pathAddClient);

        dataRefCarnet = FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET);

        userName = box.findViewById(R.id.add_user_name);
        phone = box.findViewById(R.id.add_phone);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(box)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("action :", "add client clicked");

                        String name = userName.getText().toString();
                        String tele = phone.getText().toString();
                        if (name.equals("") || tele.equals("")){
                            Toast.makeText(getContext(), "أَدْخِل الإسم و الهاتف ", Toast.LENGTH_LONG).show();
                        }
                        else {
                            final long time = new Date().getTime();
                            Client client = new Client(Long.toString(time),name, false, tele, time, 0);
                            Carne carne = new Carne(idUser, Long.toString(time), time, 0);
                            Log.d("firebase : ", "add client and carnet");
                            dataRefClient.child((Long.toString(time))).setValue(client);
                            dataRefCarnet.child(Long.toString(client.getDate())).setValue(carne);
                            dataRefCarnet.child(Long.toString(time)).keepSynced(true);
                        }
                        // FIRE ZE MISSILES!
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

