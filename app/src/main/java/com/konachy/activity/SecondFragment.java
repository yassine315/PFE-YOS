package com.konachy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.konachy.activity.ui.carnet.AdapterCarnet;
import com.konachy.activity.ui.selectitem.AchaItemKey;
import com.konachy.activity.ui.selectitem.AchatItemDetail;
import com.konachy.activity.ui.selectitem.SelectionAchatActiontMode;
import com.konachy.activity.ui.selectitem.SelectionChecker;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.konachy.beans.Achat;
import com.konachy.beans.Carne;
import com.konachy.util.DialogeDelete;
import com.konachy.util.DialogAddCarnet;
import com.konachy.util.ObserverCarnets;
import com.ymwsn.kounachy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondFragment extends Fragment {

    private static final String TAG = "SecondFragment";

    private FloatingActionButton fab;
    private TextInputEditText achatName;
    private TextInputEditText achatPrice;
    private Spinner quantity;
    private Toolbar toolbar;
    private Switch enableModif;

    private ClientViewModel clientViewModel;
    private AdapterCarnet adabterCarnet;
    private RecyclerView recyclerCarnet;
    private SelectionTracker<Achat> selectionTracker;

    private ObserverCarnets observerCarnets;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference carneRef;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
        toolbar =  getActivity().findViewById(R.id.toolbar_seller);

        firebaseDatabase = FirebaseDatabase.getInstance();
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.carnet_detail, container, false);
        achatName = view.findViewById(R.id.acha_name);
        achatPrice = view.findViewById(R.id.price);
        //initialise the spinner
        quantity = view.findViewById(R.id.quantity);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.number_of_quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantity.setAdapter(adapter);



        recyclerCarnet = view.findViewById(R.id.list_achat);
        adabterCarnet = new AdapterCarnet( new SortedList<Achat>(Achat.class, ObserverCarnets.sorteCarnet));

        observerCarnets = new ObserverCarnets(adabterCarnet, clientViewModel.getClient());
        clientViewModel.getCarnet().observe(this.getViewLifecycleOwner(), observerCarnets);
        recyclerCarnet.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerCarnet.setAdapter(adabterCarnet);

        selectionTracker = new SelectionTracker.Builder<Achat>(
                "selection-id",
                recyclerCarnet,
                new AchaItemKey(ItemKeyProvider.SCOPE_CACHED, ((AdapterCarnet)recyclerCarnet.getAdapter())),
                new AchatItemDetail(recyclerCarnet),
                StorageStrategy.createParcelableStorage(Achat.class))
                .build();

        adabterCarnet.setSelectionChecker(new SelectionChecker() {
            @Override
            public boolean isSelected(Achat achat) {
                return selectionTracker.isSelected(achat);
            }
        });

        if (savedInstanceState != null)
            selectionTracker.onRestoreInstanceState(savedInstanceState);

        fab = container.getRootView().findViewById(R.id.fab);
        fab.setEnabled(false);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SelectionAchatActiontMode selectionAchatActiontMode =
                new SelectionAchatActiontMode(selectionTracker, toolbar, requireActivity());

        selectionTracker.addObserver(selectionAchatActiontMode);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (achatName.getText().length() > 0 && achatPrice.getText().length() > 0) {
                    long time = new Date().getTime();
                    carneRef = firebaseDatabase.getReference(Carne.PATH_CARNET + "/" +
                            clientViewModel.getClient().getId());

                    Achat achat = new Achat(achatName.getText().toString(),
                            quantity.getSelectedItem().toString(),
                            Float.parseFloat(achatPrice.getText().toString()),
                            time);
                    clientViewModel.getCarnet()
                            .getValue()
                            .getListAchats().put(String.valueOf(achat.getDate()), achat);

                    carneRef.setValue(clientViewModel.getCarnet().getValue());
                    carneRef.keepSynced(true);
                    makeEmptyInput();
                }
            }
        });

/*
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
 */
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);

        Log.d(TAG, "on create options menu");
        menu.clear();
        menuInflater.inflate(R.menu.menu_carnet, menu);
        enableModif = (Switch) menu.findItem(R.id.enable_modif).getActionView();
        enableModif.setChecked(clientViewModel.getCarnet().getValue().isEnableModif());
        setEnableInput(enableModif.isChecked());
        enableModif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setEnableInput(b);
                clientViewModel.setEnableModif(b);
            }
        });
        observerCarnets.setEnableModif(enableModif);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.delete_all:
                new DialogeDelete(clientViewModel)
                        .show(this.getParentFragmentManager(),"delete all");
                return true;
            case R.id.enable_modif:
                    clientViewModel.getCarnet()
                            .getValue()
                            .setEnableModif(enableModif.isChecked());
                    setEnableInput(enableModif.isChecked());
                return  true;
            case R.id.shar_carnet:
                String code = new SimpleDateFormat("hhmmss").format(new Date());

                FirebaseDatabase.getInstance().getReference(DialogAddCarnet.CODE).child(code)
                .setValue(clientViewModel.getCarnet().getValue().getIdClient());

                return true;
        }
        return false;
    }

    private void makeEmptyInput(){
        achatName.setText("");
        achatPrice.setText("");
        quantity.setSelection(0);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        if (saveInstanceState != null)
            selectionTracker.onRestoreInstanceState(saveInstanceState);
    }

    private void setEnableInput(boolean enable){
        if (!enable) {
            achatName.setVisibility(View.GONE);
            achatPrice.setVisibility(View.GONE);
            quantity.setVisibility(View.GONE);
        }else {
            achatName.setVisibility(View.VISIBLE);
            achatPrice.setVisibility(View.VISIBLE);
            quantity.setVisibility(View.VISIBLE);
        }
    }
}