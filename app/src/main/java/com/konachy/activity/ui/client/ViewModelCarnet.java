package com.konachy.activity.ui.client;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.konachy.beans.Carne;
import com.konachy.beans.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelCarnet extends ViewModel {

    private MutableLiveData<Map<String, Carne>> carnets;

    private List<Long> carnetsId;

    private DatabaseReference clientRef;
    private DatabaseReference carnetRef;

    public ViewModelCarnet() {
        clientRef = FirebaseDatabase.getInstance()
                .getReference(User.PATH_USERS+"/"
                + FirebaseAuth.getInstance().getCurrentUser().getUid());
        carnetRef = FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET);
        chargeCarnet();
    }


    public void chargeCarnet(){
        clientRef.child(Carne.PATH_CARNET).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Long>> genericTypeIndicator = new GenericTypeIndicator<List<Long>>() {};
                carnetsId = dataSnapshot.getValue(genericTypeIndicator);
                chargeCarnetDetail(carnetsId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void chargeCarnetDetail(List<Long> carnetsId) {

        for (Long l : carnetsId){
            carnetRef.child(Long.toString(l)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ( !carnets.getValue().containsKey(dataSnapshot.getKey())){
                        carnets.getValue().put(dataSnapshot.getKey(), (Carne)dataSnapshot.getValue());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    public void addCarnet(String idCarnet){
        this.carnetsId.add(Long.parseLong(idCarnet));

        Map<String, Object> listIdCarnet = new HashMap<>();
        listIdCarnet.put(Carne.PATH_CARNET, carnetsId.toArray());
        clientRef.updateChildren(listIdCarnet);

        carnetRef.child(idCarnet).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Carne newCarnet = dataSnapshot.getValue(Carne.class);
                carnets.getValue().put(idCarnet, newCarnet);
                carnets.notify();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public MutableLiveData<Map<String, Carne>> getCarnets() {
        return carnets;
    }
}
