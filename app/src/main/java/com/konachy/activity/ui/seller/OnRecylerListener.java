package com.konachy.activity.ui.seller;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.konachy.beans.User;
import com.ymwsn.kounachy.R;

public class OnRecylerListener implements RecyclerView.RecyclerListener,  View.OnTouchListener {

    private AdapterClients adapter;
    private int position;

    public OnRecylerListener(AdapterClients adapter) {
        this.adapter = adapter;
    }

    public OnRecylerListener() {

    }

    @Override
    public void onViewRecycled(@NonNull final RecyclerView.ViewHolder holder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        position = motionEvent.getActionIndex();
        return false;
    }
}
