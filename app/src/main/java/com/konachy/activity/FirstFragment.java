package com.konachy.activity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.konachy.activity.ui.seller.AdapterClients;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.konachy.activity.ui.seller.OnRecylerListener;
import com.konachy.beans.Client;
import com.konachy.util.DialogAddClient;
import com.konachy.util.ObserverClients;
import com.ymwsn.kounachy.R;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment implements AdapterClients.OnUserClick {

    private static final String TAG = "FirstFragment";

    private RecyclerView recyclerView;
    private SearchView searchClient;

    private String idUser;
    private final String KEY_ID_USER = "id";
    private FirebaseUser user;

    private FloatingActionButton fab;
    private DialogAddClient dialogAddClient;
    private AdapterClients adapterClients;
    private ClientViewModel clientViewModel;
    private ObserverClients observerClients;

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        idUser = user.getUid();

        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);

        adapterClients = new AdapterClients(this);
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView = view.findViewById(R.id.list_user);

        fab = container.getRootView().findViewById(R.id.fab);

        observerClients = new ObserverClients(adapterClients);
        clientViewModel.getClients().observe(this.getViewLifecycleOwner(), observerClients);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterClients);
        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OnRecylerListener clientListener = new OnRecylerListener(adapterClients);
        recyclerView.setRecyclerListener(clientListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString(KEY_ID_USER, idUser);
                dialogAddClient = new DialogAddClient();
                dialogAddClient.setArguments(b);
                dialogAddClient.show(getParentFragmentManager(), idUser);

            }
        });
/*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

 */
    }

    @Override
    public void onUserClick(int position) {
        Client user = adapterClients.getItemPosition(position);
        clientViewModel.clientClicked(user);
        NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        Log.d(TAG, user.getId()+" clicked and navigate to secondFragment");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.menu_seller, menu);
        searchClient = (SearchView) menu.findItem(R.id.client_search).getActionView();

        searchClient.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchClient.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterClients.getFilter().filter(s);
                return true;
            }
        });

        searchClient.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapterClients.getClients().replaceAll(clientViewModel.getClients().getValue().values());
                adapterClients.notifyDataSetChanged();
                adapterClients.getSearchClients().clear();
                return false;
            }
        });
        searchClient.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClients.setSearchClients(clientViewModel.getClients().getValue().values());
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.client_search :
                return true;
        }
        return false;
    }
}
