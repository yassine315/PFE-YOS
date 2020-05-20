package com.konachy.activity.ui.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.konachy.activity.ui.seller.AdapterClients;
import com.konachy.util.DialogAddCarnet;
import com.konachy.util.ObserverClients;
import com.ymwsn.kounachy.R;

public class SellerListFragment extends Fragment implements AdapterClients.OnUserClick {

    private static final String TAG = "SellerListFragment";

    private RecyclerView recyclerUser;
    private ViewModelCarnet viewModelCarnet;
    private AdapterClients adapterUser;
    private ObserverClients observerUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapterUser = new AdapterClients(this);
        observerUser = new ObserverClients(adapterUser);
        viewModelCarnet = new ViewModelProvider(this).get(ViewModelCarnet.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_first, container);
        recyclerUser = view.findViewById(R.id.list_user);
        recyclerUser.setAdapter(adapterUser);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_client, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_seller:
                new DialogAddCarnet().show(getParentFragmentManager(), TAG);
                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUserClick(int position) {

    }
}
