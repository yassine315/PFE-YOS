package com.konachy.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.konachy.activity.ui.client.ViewModelCarnet;
import com.konachy.beans.Carne;
import com.ymwsn.kounachy.R;

public class DialogAddCarnet extends DialogFragment {

    public static String CODE = "code";

    private ViewModelCarnet viewModelCarnet;
    private TextInputEditText codeCarnet;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View input = inflater.inflate(R.layout.client_add_carnet_dialog, null);
        codeCarnet = input.findViewById(R.id.code_carnet);
        builder.setView(input).setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(getTag(), "positive button clicked");

                String code = codeCarnet.getText().toString();

                if (code.isEmpty())
                    Toast.makeText( getContext(), R.string.error_code, Toast.LENGTH_LONG);
                else {
                    LoadingDialogue loadingDialogue = new LoadingDialogue(requireActivity());
                    loadingDialogue.startLoadingUser();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            FirebaseDatabase.getInstance().getReference(CODE).child(code)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String idCarnet = dataSnapshot.getValue(String.class);
                                            viewModelCarnet.addCarnet(idCarnet);

                                            loadingDialogue.desmissDialogue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            loadingDialogue.desmissDialogue();
                                            Toast.makeText(getContext(), R.string.error_code, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET)
                                    .child(code).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        viewModelCarnet.addCarnet(code);
                                        loadingDialogue.desmissDialogue();
                                    } else {
                                        Toast.makeText(requireContext(), R.string.error_code, Toast.LENGTH_LONG);
                                        loadingDialogue.desmissDialogue();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(requireContext(), R.string.error_network, Toast.LENGTH_LONG);
                                }
                            });
                        }
                    });
                }

            }
        }).setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(getTag(), "negative button clicked");
            }
        });

        return builder.create();
    }
}
