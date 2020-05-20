package com.konachy.activity.ui.seller;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.konachy.beans.Achat;
import com.konachy.beans.Carne;
import com.konachy.beans.Client;
import com.konachy.beans.User;
import com.konachy.util.ObserverClients;

import java.util.Map;

public class ClientViewModel extends ViewModel {

    public static  final String PATH = "/clients";

    private MutableLiveData<Map<String, Client>> clients;
    private  MutableLiveData<Carne> carnet;
    private Client client;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference clientRef;

    public ClientViewModel(Map<String, Client> clients) {
        this.clients = new MutableLiveData<>(clients);
        chargeData();
    }
    public ClientViewModel(String idUser){
        firebaseDatabase = FirebaseDatabase.getInstance();
        clientRef = firebaseDatabase.getReference(User.PATH_USERS).child(idUser).child(PATH);
        clients = new MutableLiveData<>();
        chargeData();
    }
    public ClientViewModel(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        clientRef = firebaseDatabase.getReference(User.PATH_USERS).child(
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid())
                        .child(PATH);
        clients = new MutableLiveData<>();
        carnet = new MutableLiveData<Carne>(new Carne());
        chargeData();
    }

    public LiveData<Map<String, Client>> getClients() {
        if (clients == null) {
            clients = new MutableLiveData<Map<String, Client>>();
        }
        return clients;
    }

    private void chargeData(){

        clientRef.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Client>> genericType = new GenericTypeIndicator<Map<String, Client>>() {};
                Map<String, Client> data = dataSnapshot.getValue(genericType);

                if (data != null)
                clients.setValue(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Client>> genericType = new GenericTypeIndicator<Map<String, Client>>() {};
                Map<String, Client> data = dataSnapshot.getValue(genericType);
                if (data != null)
                    clients.setValue(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public   void  clientClicked(Client client){
        this.client = client;
        FirebaseDatabase.getInstance()
                .getReference(Carne.PATH_CARNET)
                .child(client.getId()).keepSynced(true);
        FirebaseDatabase.getInstance()
                .getReference(Carne.PATH_CARNET)
                .child(client.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carnet.setValue(dataSnapshot.getValue(Carne.class));
                client.setSomme(carnet.getValue().getSomme());
                Log.d("carnet ", "carnet charged");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FirebaseDatabase.getInstance()
                        .getReference(Carne.PATH_CARNET_ARCHIVED)
                        .child(client.getId()).setValue(carnet.getValue());
                FirebaseDatabase.getInstance()
                        .getReference(Carne.PATH_CARNET)
                        .child(client.getId()).child(Achat.LIST_ACHAT).removeValue();
            }
        });
    }

    public void deleteAllAchats(){
        FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET_ARCHIVED)
                .child(client.getId())
                .setValue(carnet);
        FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET)
                .child(client.getId())
                .child(Achat.LIST_ACHAT)
                .removeValue();
        carnet.getValue().getListAchats().clear();
    }

    public Client getClient() {
        if (this.client == null)
            return new Client();
        return client;
    }

    public MutableLiveData<Carne> getCarnet() {
        return carnet;
    }

    public void setEnableModif(boolean b) {
        FirebaseDatabase.getInstance()
                .getReference(Carne.PATH_CARNET)
                .child(client.getId()).child(Carne.FIALD_ENABLEMODIF).setValue(b);
    }



}
