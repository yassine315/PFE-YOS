package com.konachy.activity.ui.seller;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.konachy.beans.Client;
import com.konachy.util.ObserverClients;
import com.squareup.picasso.Picasso;
import com.ymwsn.kounachy.R;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AdapterClients extends RecyclerView.Adapter<ClientViewHolder> implements Filterable {

    private SortedList<Client> clients;
    private Set<Client> searchClients;

    private OnUserClick onUserClick;


    public void setClients(SortedList<Client> clients) {
        if (clients == null)
            this.clients = new SortedList<Client>(Client.class, ObserverClients.soretedPriceCallback);
        this.clients = clients;
        }
    public AdapterClients(OnUserClick onUserClick){
        super();
        this.onUserClick = onUserClick;
        clients = new SortedList<Client>(Client.class, ObserverClients.soretedPriceCallback);
        searchClients = new HashSet<>();
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view, onUserClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {

    final Client user = clients.get(position);
        Locale locale = new Locale("fr","FR");
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,locale);
        String date = format.format(new Date(user.getDate()));
        holder.getClientDate().setText(date);
        holder.getClientName().setText(String.valueOf(user.getName()));
        holder.getClientPhone().setText(String.valueOf(user.getPhone()));
        holder.getClientSomme().setText(Float.toString(user.getSomme()));
        Uri imag = user.getUrlImage();
        if (imag != null)
        Picasso.get().load(user.getUrlImage()).into(holder.getClientImage());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    public Client getItemPosition(int position){
        return clients.get(position);

    }

    public SortedList<Client> getClients() {
        return clients;
    }

    @Override
    public Filter getFilter() {
        return filterClients;
    }

    private Filter filterClients = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Client> filtredList = new ArrayList<Client>();
            if (charSequence.length() == 0 || charSequence == null)
                filtredList.addAll(searchClients);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Client client: searchClients){
                    if (client.getName().toLowerCase().contains(filterPattern))
                        filtredList.add(client);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clients.replaceAll((List<Client>)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public Set<Client> getSearchClients() {
        return searchClients;
    }

    public void setSearchClients(Collection<Client> searchClients) {
        this.searchClients.addAll(searchClients);
    }

    public interface OnUserClick{
        void onUserClick(int position);
    }


}
