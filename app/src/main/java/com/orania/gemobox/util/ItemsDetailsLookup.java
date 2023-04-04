package com.orania.gemobox.util;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.orania.gemobox.adapter.PersonsRCVAdapter;
import com.orania.gemobox.entities.Person;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsDetailsLookup extends ItemDetailsLookup<Person> {
    private final RecyclerView recyclerView;

    public ItemsDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Person> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof PersonsRCVAdapter.ViewHolder) {

                Log.d("KEY", "getItemDetails " + ((PersonsRCVAdapter.ViewHolder) viewHolder).getAdapterPosition());


                return ((PersonsRCVAdapter.ViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }
}
