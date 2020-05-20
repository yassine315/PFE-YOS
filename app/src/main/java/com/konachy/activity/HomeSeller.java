package com.konachy.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.ymwsn.kounachy.R;

public class HomeSeller extends AppCompatActivity {

    private FirebaseUser user;

    private ClientViewModel clientViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.seller_home);

        Toolbar toolbar = findViewById(R.id.toolbar_seller);
        setSupportActionBar(toolbar);

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        }

    @Override
    protected void onSaveInstanceState(Bundle saved){
        super.onSaveInstanceState(saved);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    }

}
