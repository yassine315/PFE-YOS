package com.konachy.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.ymwsn.kounachy.R;

public class LoadingDialogue {

    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialogue(Activity activity) {
        this.activity = activity;
    }


    public void startLoadingUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_bar_loading_user, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void desmissDialogue(){
        if (alertDialog != null)
        alertDialog.dismiss();
    }


    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
}
