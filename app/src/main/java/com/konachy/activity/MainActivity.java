package com.konachy.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.konachy.beans.User;
import com.konachy.util.LoadingDialogue;
import com.ymwsn.kounachy.R;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int MY_TEQUEST_CODE = 31577;
    private List<AuthUI.IdpConfig> proveders;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    private User userProspect;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dataRef;
    private LoadingDialogue loadingDialogueUser;

    private TextInputEditText username;
    private TextInputEditText phone;
    private RadioGroup status;
    private Button next;

    private List<String> permissions;
    private boolean inProgress;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private int chekedSeller;
    private static final String KEY_CHEKED_SELLER = "key_cheked_seller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (savedInstanceState != null)
            status.check(chekedSeller);
        if (user == null) {
            Log.d(TAG, "user is null new auth");
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(proveders)
                            .setTheme(R.style.LoginTheme)
                            .build(), MY_TEQUEST_CODE);}
        else {
            chargeData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == MY_TEQUEST_CODE) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            dataRef.child(user.getUid()).keepSynced(true);

            if (resultCode == RESULT_OK) {
                user = firebaseAuth.getCurrentUser();
                //FirebaseUserMetadata metadata = user.getMetadata();
                if(response.isNewUser()){
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                    dataRef.child(user.getUid()).child(User.URL_IMAGE).setValue(user.getPhotoUrl());
                    if (!user.getPhoneNumber().isEmpty() || !user.getDisplayName().isEmpty()) {
                        phone.setText(user.getPhoneNumber());
                        username.setText(user.getDisplayName());
                    }

                }else{
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                    chargeData();
                }
            } else {
                Toast.makeText(MainActivity.this, "error ", Toast.LENGTH_LONG).show();
                onCreate(null);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean seller = (status.getCheckedRadioButtonId() == R.id.seller) ? true : false;
                String surname = username.getText().toString();
                String tele = phone.getText().toString();

                User userProspect = new User(surname, seller, tele, new Date().getTime());

                if (surname != null || tele != null || status.getCheckedRadioButtonId() == R.id.seller || status.getCheckedRadioButtonId() == R.id.client) {
                    dataRef.child(user.getUid()).keepSynced(true);
                    dataRef.child(user.getUid()).updateChildren(userProspect.toMap());
                    if (seller)
                        startActivity(new Intent(MainActivity.this, HomeSeller.class));
                    else
                        startActivity(new Intent(MainActivity.this, HomeClient.class));
                    MainActivity.this.finish();
                } else {
                    Toast.makeText(MainActivity.this, "أَدْخِل الإسم و الهاتف ", Toast.LENGTH_LONG).show();
                }
                MainActivity.this.finish();
            }
        });
    }

    private void chargeData() {

        Log.d(TAG, "charge user in data base");
        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            loadingDialogueUser.startLoadingUser();
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataRef.child(user.getUid()).orderByValue().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (userProspect==null) {
                                userProspect = dataSnapshot.getValue(User.class);
                                Log.d(TAG, "user charged =" + userProspect);
                                if (loadingDialogueUser.getAlertDialog().isShowing())
                                    loadingDialogueUser.desmissDialogue();
                                if (userProspect.isSeller())
                                    startActivity(new Intent(MainActivity.this, HomeSeller.class));
                                else
                                    startActivity(new Intent(MainActivity.this, HomeClient.class));
                                MainActivity.this.finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    dataRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (userProspect==null) {
                                userProspect = dataSnapshot.getValue(User.class);
                                Log.d(TAG, "user charged =" + userProspect);
                                if (loadingDialogueUser.getAlertDialog().isShowing())
                                    loadingDialogueUser.desmissDialogue();
                                if (userProspect.isSeller())
                                    startActivity(new Intent(MainActivity.this, HomeSeller.class));
                                else
                                    startActivity(new Intent(MainActivity.this, HomeClient.class));
                                MainActivity.this.finish();}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

    }

    private void init() {
        Log.d(TAG, "init the component");
        next = findViewById(R.id.button_next);
        username = findViewById(R.id.surname);
        phone = findViewById(R.id.phone);
        status = findViewById(R.id.status);

        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        dataRef = FirebaseDatabase.getInstance().getReference(User.PATH_USERS);
        loadingDialogueUser = new LoadingDialogue(this);

        permissions = Arrays.asList("public_profile", "email");

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                chekedSeller = status.getCheckedRadioButtonId();
            }
        });

        proveders = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().setPermissions(permissions).build()
        );
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, inProgress);
        outState.putInt(KEY_CHEKED_SELLER, status.getCheckedRadioButtonId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
        chekedSeller = savedInstanceState.getInt(KEY_CHEKED_SELLER);
    }
}

