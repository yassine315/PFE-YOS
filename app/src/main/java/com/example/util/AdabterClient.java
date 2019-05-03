package com.example.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.example.beans.Client;
import com.example.yassinetest.R;

import java.util.HashMap;
import java.util.List;

public class AdabterClient extends ArrayAdapter<Client> {
    HashMap<Client, Integer> mIdMap = new HashMap<Client, Integer>();
/*
    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }


*/
    public AdabterClient(@NonNull Context context, int resource, @NonNull List<Client> objects) {
        super(context, resource, objects);
        for(int i=0; i<objects.size() ; i++){
            mIdMap.put(objects.get(i),i);
        }
    }

    @Override
    public long getItemId(int position) {
        Client item = getItem(position);
        return mIdMap.get(item);
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_client, parent, false);
        Client client = getItem(position);
        ((TextView)rowView.findViewById(R.id.list_nom_client)).setText(client.getNom());
        ((TextView)rowView.findViewById(R.id.list_prenom_client)).setText(client.getPrenom());
        ((TextView)rowView.findViewById(R.id.list_phon_client)).setText(client.getPhone());
    return rowView;
    }

    public void  add(Client client){
        super.add(client);
        mIdMap.put(client,mIdMap.size());
    }
}
