package com.konachy.util;


import android.widget.Switch;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.SortedList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.konachy.activity.ui.carnet.AdapterCarnet;
import com.konachy.beans.Achat;
import com.konachy.beans.Carne;
import com.konachy.beans.Client;
import com.konachy.beans.User;

import java.util.Iterator;

public class ObserverCarnets implements Observer<Carne> {
    private AdapterCarnet adapterCarnet;
    private Client client;
    private Switch enableModif;


    public ObserverCarnets(AdapterCarnet adapterCarnet, Client client) {
        this.adapterCarnet = adapterCarnet;
        this.client = client;
    }

    public  static SortedList.Callback<Achat> sorteCarnet = new SortedList.Callback<Achat>() {

        @Override
        public void onInserted(int position, int count) {

        }

        @Override
        public void onRemoved(int position, int count) {

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {

        }

        @Override
        public int compare(Achat o1, Achat o2) {
            return new Long(o1.getDate()).compareTo(new Long(o2.getDate()));
        }

        @Override
        public void onChanged(int position, int count) {

        }

        @Override
        public boolean areContentsTheSame(Achat oldItem, Achat newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Achat item1, Achat item2) {
            return item1.getDate() == item2.getDate();
        }
    };

    @Override
    public void onChanged(Carne carne) {
        Iterator<Achat> it = carne.getListAchats().values().iterator();
        carne.setSomme(0);
        while (it.hasNext()){
            Achat achat = it.next();
            carne.addAchat(achat.getPrix());
        }

        adapterCarnet.getAchats().replaceAll(carne.getListAchats().values());
        adapterCarnet.notifyDataSetChanged();

        if (enableModif != null){
            enableModif.setChecked(carne.isEnableModif());
        }

        FirebaseDatabase.getInstance()
                .getReference(User.PATH_USERS).child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
                ).child(Client.PATH_CLIENTS).child(client.getId()).child("somme")
                .setValue(carne.getSomme());
    }

    public Switch getEnableModif() {
        return enableModif;
    }

    public void setEnableModif(Switch enableModif) {
        this.enableModif = enableModif;
    }
}
