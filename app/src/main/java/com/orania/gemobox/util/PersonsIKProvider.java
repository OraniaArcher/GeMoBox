package com.orania.gemobox.util;

import com.orania.gemobox.entities.Person;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class PersonsIKProvider extends ItemKeyProvider {
    private final List<Person> allPersons;

    public PersonsIKProvider(int scope, List<Person> allPersonsList) {
        super(scope);
        this.allPersons = allPersonsList;
    }

    @Nullable
    @Override
    public Object getKey(int position) {
        return allPersons.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key) {
        return allPersons.indexOf(key);
    }
}
