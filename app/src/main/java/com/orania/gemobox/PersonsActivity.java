package com.orania.gemobox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orania.gemobox.adapter.PersonsRCVAdapter;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.model.GemoboxViewModel;
import com.orania.gemobox.relations.MovieDirectorWithPerson;
import com.orania.gemobox.relations.MovieWithDirectors;
import com.orania.gemobox.util.ActionModeController;
import com.orania.gemobox.util.ItemsDetailsLookup;
import com.orania.gemobox.util.PersonsIKProvider;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.OnItemActivatedListener;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PersonsActivity extends AppCompatActivity implements PersonsRCVAdapter.OnPersonClickListener {
    private static final int NEW_PERSON_ACTIVITY_REQUEST_CODE = 1;
    public static final String PERSON_ID = "person_id";
    public static final int SCOPE_MAPPED = 1;

    //GemoboxRoomDatabase db;
    //AllDao allDao;

    private GemoboxViewModel gemoboxViewModel;
    //private TextView textView;
    //private ListView listView;
    //private ArrayList<String> personArrayList;
    //private ArrayAdapter<String> arrayAdapter;
    private RecyclerView recyclerPersonView;
    private PersonsRCVAdapter recyclerPersonViewAdapter;
    private LiveData<List<Person>> personList;

    SelectionTracker selectionTracker;
    private ActionMode actionMode = null;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);

        //textView = findViewById(R.id.text);
        //listView = findViewById(R.id.list_view);
        //personArrayList = new ArrayList<>();
        recyclerPersonView = findViewById(R.id.recycler_person_view);
        recyclerPersonView.setHasFixedSize(true);
        recyclerPersonView.setLayoutManager(new LinearLayoutManager(this));


        //nowe
        gemoboxViewModel = new ViewModelProvider.AndroidViewModelFactory(PersonsActivity.this
                .getApplication())
                .create(GemoboxViewModel.class);

        gemoboxViewModel.getAllMovies().observe(this, movieWithDirectors -> {
            //Log.d("MOVIE", "onCreate: " + movieWithDirectors.get(0).movie.movieTitle);

            StringBuilder builder = new StringBuilder();

            for(MovieWithDirectors mwd: movieWithDirectors){
                Log.d("MOVIE", "Movie Title " + mwd.movie.getMovieTitle());
                    for(MovieDirectorWithPerson mdwp: mwd.movieWithDirectors){
                        for(Person p: mdwp.personList){
                            Log.d("MOVIE", "Movie Director " + p.getPersonFirstName() + " " + p.getPersonLastName());

                            builder.append(p.getPersonFirstName()).append(" ").append(p.getPersonLastName());
                        }
                    }
            }

            //textView.setText(builder.toString());
        });

        gemoboxViewModel.getAllPersons().observe(this, persons -> {
            //Log.d("DUPA", "onCreate: " + persons.get(0).getPersonFirstName());

            //StringBuilder strb = new StringBuilder();
            //for(Person per: persons) {
                //strb.append(per.getPersonFirstName()).append(" ").append(per.getPersonLastName()).append(" ");

                //personArrayList.add(per.getPersonFirstName() + " " + per.getPersonLastName());
            //}
            //create array adapter
            //arrayAdapter = new ArrayAdapter<>(
              //      this,
                //    android.R.layout.simple_list_item_1,
                  //  personArrayList
            //);
            //add to our listview
            //listView.setAdapter(arrayAdapter);

            //textView.setText(strb.toString());


            //Set up adapter
            recyclerPersonViewAdapter = new PersonsRCVAdapter(persons, PersonsActivity.this, this);
            recyclerPersonViewAdapter.setHasStableIds(true);
            recyclerPersonView.setAdapter(recyclerPersonViewAdapter);



            selectionTracker = new SelectionTracker.Builder<Person>(
                    "my-selection-id",
                    recyclerPersonView,
                    new PersonsIKProvider(SCOPE_MAPPED, persons, recyclerPersonViewAdapter, recyclerPersonView),
                    new ItemsDetailsLookup(recyclerPersonView),
                    StorageStrategy.createParcelableStorage(Person.class)
            ).withOnItemActivatedListener(new OnItemActivatedListener() {
                @Override
                public boolean onItemActivated(@NonNull ItemDetailsLookup.ItemDetails item, @NonNull MotionEvent e) {
                    //Log.d("DUPAKWAS", "Selected ID " + item.getSelectionKey());
                    Log.d("GIL", "onItemActivated getPosition " + item.getPosition());

                    //zawolane onPersonClick
                    Person person = Objects.requireNonNull(gemoboxViewModel.allPersons.getValue()).get(item.getPosition());
                    Log.d("GIL", "onPersonClick: " + person.getPersonFirstName());

                    Intent intent = new Intent(PersonsActivity.this, NewPerson.class);
                    intent.putExtra(PERSON_ID, person.getIdPerson());
                    startActivity(intent);
                    //koniec zawolane onPersonClick

                    return true;
                }
            }).build();
            recyclerPersonViewAdapter.setSelectionTracker(selectionTracker);

            selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
                @Override
                public void onItemStateChanged(@NonNull Object key, boolean selected) {

                    Log.d("GIL", "onItemStateChanged: " + key + " getSelection " + selectionTracker.getSelection().size());


                    super.onItemStateChanged(key, selected);
                }

                @Override
                public void onSelectionRefresh() {
                    Log.d("GIL", "onSelectionRefresh");
                    super.onSelectionRefresh();
                }

                @Override
                public void onSelectionChanged() {
                    super.onSelectionChanged();
                    if (selectionTracker.hasSelection() && actionMode == null) {
                        actionMode = startSupportActionMode(new ActionModeController(PersonsActivity.this, selectionTracker, gemoboxViewModel, persons));
                        Log.d("GIL", "onSelectionChanged: " + selectionTracker.getSelection().size());
                    } else if (!selectionTracker.hasSelection() && actionMode != null) {
                        actionMode.finish();
                        actionMode = null;
                    }

                    Iterator<Person> itemIterable = selectionTracker.getSelection().iterator();
                    while (itemIterable.hasNext()) {
                        Log.d("GIL", "itemIterable " + itemIterable.next().getPersonFirstName());
                    }

                }

                @Override
                public void onSelectionRestored() {
                    super.onSelectionRestored();
                }
            });


        });


        FloatingActionButton fab = findViewById(R.id.add_person_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(PersonsActivity.this, NewPerson.class);
            startActivityForResult(intent, NEW_PERSON_ACTIVITY_REQUEST_CODE);
        });
        //koniec nowe

        //db = Room.databaseBuilder(this, GemoboxRoomDatabase.class,"gemobox_database").allowMainThreadQueries().build();
        //allDao = db.allDao();

        //db.clearAllTables();
        //allDao.deleteAllPerson();
        //allDao.deleteAllDirector();
        //allDao.deleteAllMovie();

        //allDao.insertPerson(new Person("Ridley","Scott"));
        //allDao.insertMovie(new Movie("Alien"));
        //allDao.insertDirector(new Director(11, 11));

        //for(MovieWithDirectors mwd: allDao.getAllMoviesWithDirectors()){
            //Log.d("MOVIE", "Movie Title " + mwd.movie.movieTitle);
            //for(MovieDirectorWithPerson mdwp: mwd.movieWithDirectors){
                //for(Person p: mdwp.personList){
                    //Log.d("MOVIE", "Movie Director " + p.getPersonFirstName() + " " + p.getPersonLastName());
                //}
            //}
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PERSON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String fName = data.getStringExtra(NewPerson.F_NAME_REPLY);
            String lName = data.getStringExtra(NewPerson.L_NAME_REPLY);

            Person person = new Person(fName, lName);

            //Log.d("MOVIE", "onActivityResult " + data.getStringExtra(NewPerson.F_NAME_REPLY));
            //Log.d("MOVIE", "onActivityResult " + data.getStringExtra(NewPerson.L_NAME_REPLY));

            GemoboxViewModel.insertPerson(person);

            //odswiezenie widoku personow bo po dodaniu nowej wejscie w tryb selekcji wywalalo sie
            Intent intentPersonsView = new Intent(PersonsActivity.this, PersonsActivity.class);
            startActivityForResult(intentPersonsView, 456);
        }
    }

    @Override
    public void onPersonClick(int position) {
        Person person = Objects.requireNonNull(gemoboxViewModel.allPersons.getValue()).get(position);
        Log.d("MOVIE", "onPersonClick: " + person.getPersonFirstName());

        Intent intent = new Intent(PersonsActivity.this, NewPerson.class);
        intent.putExtra(PERSON_ID, person.getIdPerson());
        startActivity(intent);
    }

    //@Override
    //public void onPersonLongClick(int position) {
        //Log.d("MOVIE", "Long Click From Persons Activity: " + position);
    //}

    //@Override
    //public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        //Log.d("DUPAKWAS", "onCreateActionMode: " + mode);

        //MenuInflater inflater = mode.getMenuInflater();
        //inflater.inflate(R.menu.context_action_menu, menu);
        //return true;
    //}

    //@Override
    //public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        //return false;
    //}

    //@Override
    //public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        //return false;
    //}

    //@Override
    //public void onDestroyActionMode(ActionMode mode) {

    //}
}