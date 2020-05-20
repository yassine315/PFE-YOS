package com.konachy.activity.ui.carnet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.konachy.activity.ui.selectitem.SelectionChecker;
import com.konachy.beans.Achat;
import com.ymwsn.kounachy.R;


public class AdapterCarnet extends RecyclerView.Adapter<CarnetViewHolder> {
    private SelectionChecker selectionChecker;
    private SortedList<Achat> achats;
    
    public AdapterCarnet(SortedList<Achat> achats) {
        this.achats = achats;
    }

    @Override
    public CarnetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carnet, parent, false);
        return new CarnetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarnetViewHolder holder, int position) {
        holder.bindTo(achats.get(position), selectionChecker.isSelected(achats.get(position)));
        if (holder.itemView.isActivated()){
            holder.itemView.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return achats.size();
    }

    public SortedList<Achat> getAchats() {
        return achats;
    }

    public void setAchats(SortedList<Achat> achats) {
        this.achats = achats;
    }

    public int getPosition(Achat key){
        return achats.indexOf(key);
    }

    public Achat getKey(int position){
        return achats.get(position);
    }

    public void setSelectionChecker(SelectionChecker selectionChecker) {
        this.selectionChecker = selectionChecker;
    }
}
