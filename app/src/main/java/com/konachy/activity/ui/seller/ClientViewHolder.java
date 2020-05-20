package com.konachy.activity.ui.seller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ymwsn.kounachy.R;

public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView clientImage;
    private TextView clientName;
    private TextView clientPhone;
    private TextView clientDate;
    private TextView clientSomme;

    private AdapterClients.OnUserClick onUserClick;

    public ClientViewHolder(@NonNull View itemView, AdapterClients.OnUserClick onUserClick) {
        super(itemView);
        clientDate = itemView.findViewById(R.id.date_client);
        clientPhone = itemView.findViewById(R.id.phone_client);
        clientName = itemView.findViewById(R.id.client_name);
        clientImage = itemView.findViewById(R.id.img_user);
        clientSomme = itemView.findViewById(R.id.somme);
        clientImage.setImageResource(R.drawable.icon_client);
        this.onUserClick = onUserClick;
        itemView.setOnClickListener(this);
    }


    public TextView getClientSomme() {
        return clientSomme;
    }

    public TextView getClientName() {
        return clientName;
    }

    public TextView getClientPhone() {
        return clientPhone;
    }

    public TextView getClientDate() {
        return clientDate;
    }

    public void setClientName(TextView clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(TextView clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setClientDate(TextView clientDate) {
        this.clientDate = clientDate;
    }

    public ImageView getClientImage() {
        return clientImage;
    }

    public void setClientImage(ImageView clientImage) {
        this.clientImage = clientImage;
    }

    @Override
    public void onClick(View view) {
        onUserClick.onUserClick(getAdapterPosition());

    }
}
