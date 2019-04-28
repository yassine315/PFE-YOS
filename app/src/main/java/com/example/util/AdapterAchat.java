package com.example.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.example.beans.Achat;
import com.example.com.example.beans.Client;
import com.example.yassinetest.R;

import java.util.HashMap;
import java.util.List;

public class AdapterAchat extends ArrayAdapter<Achat> {
    HashMap<Achat, Integer> mIdMap = new HashMap<Achat, Integer>();
    float somme;

    public AdapterAchat(@NonNull Context context, int resource, @NonNull List<Achat> objects) {
        super(context, resource, objects);
        for(int i=0; i<objects.size() ; i++){
            mIdMap.put(objects.get(i),i);
        }
        somme = sommeAchats();
    }

    @Override
    public long getItemId(int position) {
        Achat item = getItem(position);
        return mIdMap.get(item);
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_achat, parent, false);
        Achat achat = getItem(position);
        ((TextView)rowView.findViewById(R.id.id_achat)).setText(Integer.toString(position+1));
        ((TextView)rowView.findViewById(R.id.nom_achat)).setText(achat.getNom());
        ((TextView)rowView.findViewById(R.id.quantite_achat)).setText(achat.getQuantite());
        ((TextView)rowView.findViewById(R.id.prix_achat)).setText(Float.toString(achat.getPrix()));
        return rowView;
    }
    public void add(Achat achat){
        super.add(achat);
        mIdMap.put(achat,mIdMap.size());
        somme += achat.getPrix();

    }
    public  float getSomme(){return somme;}

    public float sommeAchats(){
        float somme = 0;
        for(int i=0 ; i<mIdMap.size();i++){
            somme += getItem(i).getPrix();
        }
        return  somme;
    }

}
