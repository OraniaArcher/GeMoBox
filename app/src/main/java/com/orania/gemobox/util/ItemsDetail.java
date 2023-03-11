package com.orania.gemobox.util;

import com.orania.gemobox.entities.Person;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class ItemsDetail extends ItemDetailsLookup.ItemDetails {
    private final int adapterPosition;
    private final Person selectionKey;

    public ItemsDetail(int adapterPosition, Person selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}
