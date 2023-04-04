package com.orania.gemobox.util;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.orania.gemobox.R;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.model.GemoboxViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;

public class ActionModeController implements ActionMode.Callback {

    private final Context context;
    private final SelectionTracker selectionTracker;
    private final List<Person> allPersons;
    private ArrayList<Person> selectedPersons;
    private GemoboxViewModel gemoboxViewModel;


    public ActionModeController(Context context, SelectionTracker selectionTracker, GemoboxViewModel gemoboxViewModel, List<Person> allPersons) {
        this.context = context;
        this.selectionTracker = selectionTracker;
        this.allPersons = allPersons;
        this.gemoboxViewModel = gemoboxViewModel;
        this.selectedPersons = new ArrayList<>();
    }



    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        Log.d("DUPAKWAS", "onCreateActionMode: " + mode);

        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_action_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_act_delete:
                //Log.d("DUPAKWAS", "onActionItemClicked: Delete");

                for (Person person : allPersons) {
                    if (selectionTracker.isSelected(person)) {
                        selectedPersons.add(person);
                    } else if (selectedPersons.contains(person) && !selectionTracker.isSelected(person)) {
                        selectedPersons.remove(person);
                    }
                }

                for (Person p : selectedPersons) {
                    Log.d("DUPAKWAS", "toDelete: " + p.getPersonFirstName());
                    GemoboxViewModel.deletePerson(p);
                }



                //Intent intentPersonsView = new Intent(context, PersonsActivity.class);
                //context.startActivity(intentPersonsView);



                //mode.finish();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        selectionTracker.clearSelection();
        //mode.finish();
    }
}
