package com.konachy.util;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.SortedList;

import com.konachy.activity.ui.seller.AdapterClients;
import com.konachy.beans.Client;
import java.util.Map;

/*
*Class observe list of client to passe an adapter
*
 */
public class ObserverClients implements Observer<Map<String, Client>> {

    private AdapterClients adapterClients;

    @Override
    public void onChanged(Map<String, Client> stringUserMap) {
        adapterClients.getClients().replaceAll(
                stringUserMap.values());
        adapterClients.notifyDataSetChanged();
    }

    public ObserverClients(AdapterClients adapterClients) {
        this.adapterClients = adapterClients;
    }

    public static SortedList.Callback<Client> soretedPriceCallback = new SortedList.Callback<Client>() {

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
        public int compare(Client o1, Client o2) {
            return Float.compare(o1.getSomme(), o2.getSomme());
        }

        @Override
        public void onChanged(int position, int count) {

        }

        @Override
        public boolean areContentsTheSame(Client oldItem, Client newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Client item1, Client item2) {
            return false;
        }
    };

    public static SortedList.Callback<Client> soretedNameCallback = new SortedList.Callback<Client>() {

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
        public int compare(Client o1, Client o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }

        @Override
        public void onChanged(int position, int count) {

        }

        @Override
        public boolean areContentsTheSame(Client oldItem, Client newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Client item1, Client item2) {
            return false;
        }
    };
}