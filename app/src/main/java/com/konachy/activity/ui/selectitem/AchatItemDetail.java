package com.konachy.activity.ui.selectitem;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.konachy.activity.ui.carnet.AdapterCarnet;
import com.konachy.activity.ui.carnet.CarnetViewHolder;
import com.konachy.beans.Achat;

public class AchatItemDetail extends ItemDetailsLookup<Achat> {

    private  RecyclerView recyclerView;

    public AchatItemDetail(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
        if (view != null){
            final RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
            if (holder instanceof CarnetViewHolder)
               return  new ItemDetails<Achat>() {
                   @Override
                   public int getPosition() {
                       return holder.getAdapterPosition();
                   }

                   @Nullable
                   @Override
                   public Achat getSelectionKey() {
                       return ((AdapterCarnet)(recyclerView.getAdapter())).getKey(getPosition());
                   }
               };
                // return new AchaItemDetailHolder(((AdapterCarnet)recyclerView.getAdapter()),((CarnetViewHolder)holder));
        }
        return null;
    }
}
