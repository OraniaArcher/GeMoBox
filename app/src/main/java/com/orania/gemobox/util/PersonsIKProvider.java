package com.orania.gemobox.util;

import android.util.Log;

import com.orania.gemobox.adapter.PersonsRCVAdapter;
import com.orania.gemobox.entities.Person;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class PersonsIKProvider extends ItemKeyProvider<Person> {
    private final List<Person> allPersons;
    private PersonsRCVAdapter recyclerPersonViewAdapter;
    private RecyclerView recyclerPersonView;

    public PersonsIKProvider(int scope, List<Person> allPersonsList, PersonsRCVAdapter recyclerPersonViewAdapter, RecyclerView recyclerPersonView) {
        super(scope);
        this.allPersons = allPersonsList;
        this.recyclerPersonViewAdapter = recyclerPersonViewAdapter;
        this.recyclerPersonView = recyclerPersonView;
    }

    @Nullable
    @Override
    public Person getKey(int position) {

        Log.d("GIL", "getKey " + position);
        Log.d("GIL", "recyclerPersonViewAdapter.getItemId(position) " + recyclerPersonViewAdapter.getItemId(position));

        Person retPer = null;

        for (int i = 0; i < allPersons.size(); i++) {
            Person p = allPersons.get(i);
            if (p.getIdPerson() == recyclerPersonViewAdapter.getItemId(position)) {
                retPer = p;
            }
        }

        //return allPersons.get(position);

        return retPer;
    }

    @Override
    public int getPosition(@NonNull Person key) {

        Log.d("GIL", "getPosition " + key);

        RecyclerView.ViewHolder viewHolder = recyclerPersonView.findViewHolderForItemId(key.getIdPerson());

        //Log.d("GIL", "viewHolder.getLayoutPosition " + viewHolder.getLayoutPosition());

        //return allPersons.indexOf(key);

        return viewHolder == null ? RecyclerView.NO_POSITION : viewHolder.getLayoutPosition();
    }
}
