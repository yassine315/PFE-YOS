package com.konachy.activity.ui.selectitem;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.SelectionTracker.SelectionObserver;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.konachy.activity.ui.seller.ClientViewModel;
import com.konachy.beans.Achat;
import com.konachy.beans.Carne;
import com.ymwsn.kounachy.R;

import java.util.Date;
import java.util.Iterator;

public class SelectionAchatActiontMode extends SelectionObserver implements ActionMode.Callback {
    private static final String TAG = "SelectionAchatActiontMo";

    private  Toolbar toolbar;

    private SelectionTracker<Achat> selectionTracker;
    private ActionMode actionMode;
    private ClientViewModel clientViewModel;

    private DatabaseReference carnetRef;
    private DatabaseReference carnetArchivRef;

    private Carne carne ;

    public SelectionAchatActiontMode(SelectionTracker<Achat> selectionTracker, Toolbar toolbar,FragmentActivity fragmentActivity) {
        this.selectionTracker = selectionTracker;
        this.toolbar = toolbar;
        this.clientViewModel = new ViewModelProvider(fragmentActivity).get(ClientViewModel.class);
        this.carne = clientViewModel.getCarnet().getValue();
        this.carnetRef = FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET+"/"+
                clientViewModel.getClient().getId());
        this.carnetArchivRef = FirebaseDatabase.getInstance().getReference(Carne.PATH_CARNET_ARCHIVED);
    }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_achats_selected, menu);
            Log.d(TAG,"inflate new menu");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        //menu action mode selecte achats
        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.achats_delete:
                    deleteAchats(selectionTracker.getSelection());
                    finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                finish();
            }

    private void deleteAchats(Selection<Achat> selection) {
        Iterator<Achat> it = selection.iterator();

        Carne carneArchiv = new Carne(carne.getIdMolhanot(), carne.getIdClient(), carne.getDate(), carne.getSomme());
        while (it.hasNext()){
            Achat achat = it.next();
            carneArchiv.getListAchats().put(Long.toString(new Date().getTime()), achat);
            carnetRef.child(Achat.LIST_ACHAT).child(
                    Long.toString(achat.getDate())
            ).removeValue()

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "remove failure");
                            Snackbar.make(toolbar, R.string.message_error_data_base, Snackbar.LENGTH_LONG)
                                    .setAction("delete", null).show();
                        }
                    });
        }
        carnetArchivRef.child(clientViewModel.getClient().getId()).setValue(carneArchiv);

    }
///////////////////////////////////////////////selectionTracker observer//////////////////////////////////////////////////////////
    @Override
    public void onSelectionChanged (){
        super.onSelectionChanged();
        if (selectionTracker.hasSelection() && actionMode ==null)
            actionMode =  toolbar.startActionMode( this);
        if (actionMode != null && !selectionTracker.hasSelection())
            this.finish();
        Log.d(TAG, "on selection changed ");
    }

    @Override
    public void onSelectionRefresh (){
        super.onSelectionRefresh();
        Log.d(TAG, "on selection refresh");
    }

    @Override
    public void onSelectionRestored(){
        super.onSelectionRestored();
        Log.d(TAG, "on selection restored");
    }

    @Override
    public void onItemStateChanged(@NonNull Object key, boolean selected) {
        super.onItemStateChanged(key, selected);
        Log.d(TAG, "selected item have a key"+key.toString()+"selected "+selected);
    }

    private void finish(){
        actionMode.finish();
        actionMode = null;
        selectionTracker.clearSelection();
    }
}
