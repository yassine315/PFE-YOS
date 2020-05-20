package com.konachy.activity.ui.selectitem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import com.konachy.activity.ui.carnet.AdapterCarnet;
import com.konachy.beans.Achat;

public class AchaItemKey extends ItemKeyProvider<Achat> {
    /**
     * Creates a new provider with the given scope.
     *
     * @param scope Scope can't be changed at runtime.
     */

    private AdapterCarnet adapter;

    public AchaItemKey(int scope, AdapterCarnet adapter) {
        super(scope);
        this.adapter = adapter;
    }

    protected AchaItemKey(int scope) {
        super(scope);
    }

    @Nullable
    @Override
    public Achat getKey(int position) {
        return adapter.getKey(position);
    }

    @Override
    public int getPosition(@NonNull Achat key) {
        return adapter.getPosition(key);
    }

}
