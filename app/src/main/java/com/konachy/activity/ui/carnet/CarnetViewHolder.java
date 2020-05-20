package com.konachy.activity.ui.carnet;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.konachy.beans.Achat;
import com.ymwsn.kounachy.R;

public class CarnetViewHolder extends RecyclerView.ViewHolder {

    private TextView nameAchat;
    private TextView price;
    private TextView quantuty;

    public CarnetViewHolder(@NonNull View itemView) {
        super(itemView);
        nameAchat = itemView.findViewById(R.id.item_achat_name);
        quantuty = itemView.findViewById(R.id.item_quantity);
        price = itemView.findViewById(R.id.item_price);

    }

    public void bindTo(Achat achat, boolean selected){
        this.price.setText(Float.toString(achat.getPrix()));
        this.quantuty.setText(achat.getQuantite());
        this.nameAchat.setText(achat.getNom());
        this.itemView.setActivated(selected);
    }

}
