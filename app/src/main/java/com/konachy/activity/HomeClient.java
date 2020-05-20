package com.konachy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.konachy.activity.ui.homeclient.HomeClientFragment;
import com.ymwsn.kounachy.R;

public class HomeClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_client_activity);
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeClientFragment.newInstance())
                    .commitNow();
        }

         */
    }
}
