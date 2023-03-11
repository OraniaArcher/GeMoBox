package com.orania.gemobox.util;

import android.view.MotionEvent;
import android.view.View;

import com.orania.gemobox.adapter.PersonsRCVAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsDetailsLookup extends ItemDetailsLookup {
    private final RecyclerView recyclerView;

    public ItemsDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof PersonsRCVAdapter.ViewHolder) {
                return ((PersonsRCVAdapter.ViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }
}
