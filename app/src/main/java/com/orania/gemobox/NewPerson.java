package com.orania.gemobox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.model.GemoboxViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class NewPerson extends AppCompatActivity {
    public static final String F_NAME_REPLY = "f_name_reply";
    public static final String L_NAME_REPLY = "l_name_reply";
    private EditText enterFname;
    private EditText enterLname;
    private Button saveNewPersonButton;
    private long personId = 0;
    private Boolean isEditPerson = false;
    private Button updatePersonButton;
    private Button deletePersonButton;

    private GemoboxViewModel gemoboxViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);

        enterFname = findViewById(R.id.enter_f_name);
        enterLname = findViewById(R.id.enter_l_name);
        saveNewPersonButton = findViewById(R.id.save_person_button);

        gemoboxViewModel = new ViewModelProvider.AndroidViewModelFactory(NewPerson.this
                .getApplication())
                .create(GemoboxViewModel.class);

        if(getIntent().hasExtra(PersonsActivity.PERSON_ID)) {
            personId = getIntent().getLongExtra(PersonsActivity.PERSON_ID, 0);

            gemoboxViewModel.getPerson(personId).observe(this, new Observer<Person>() {
                @Override
                public void onChanged(Person person) {
                    if(person != null) {
                        enterFname.setText(person.getPersonFirstName());
                        enterLname.setText(person.getPersonLastName());
                    }
                }
            });
            isEditPerson = true;
        }

        saveNewPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if(!(TextUtils.isEmpty(enterFname.getText()) && TextUtils.isEmpty(enterLname.getText()))){
                    String fName = enterFname.getText().toString();
                    String lName = enterLname.getText().toString();

                    replyIntent.putExtra(F_NAME_REPLY, fName);
                    replyIntent.putExtra(L_NAME_REPLY, lName);
                    setResult(RESULT_OK, replyIntent);


                    //Person person = new Person(enterFname.getText().toString(), enterLname.getText().toString());
                    //GemoboxViewModel.insertPerson(person);
                }else{
                    //Toast.makeText(NewPerson.this, R.string.empty, Toast.LENGTH_SHORT).show();

                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });

        //Update button
        updatePersonButton = findViewById(R.id.update_person_button);
        updatePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long idPerson = personId;
                String fName = enterFname.getText().toString().trim();
                String lName = enterLname.getText().toString().trim();

                if(TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName)) {
                    Snackbar.make(enterFname, R.string.empty, Snackbar.LENGTH_SHORT).show();
                }else {
                    Person person = new Person();
                    person.setIdPerson(idPerson);
                    person.setPersonFirstName(fName);
                    person.setPersonLastName(lName);
                    GemoboxViewModel.updatePerson(person);
                    finish();

                    Intent intentPersonsView = new Intent(NewPerson.this, PersonsActivity.class);
                    startActivityForResult(intentPersonsView, 123);
                }

            }
        });

        //Delete button
        deletePersonButton = findViewById(R.id.delete_person_button);
        deletePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = enterFname.getText().toString().trim();
                String lName = enterLname.getText().toString().trim();

                if(TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName)) {
                    Snackbar.make(enterFname, R.string.empty, Snackbar.LENGTH_SHORT).show();
                }else {
                    Person person = new Person();
                    person.setIdPerson(personId);
                    person.setPersonFirstName(fName);
                    person.setPersonLastName(lName);
                    GemoboxViewModel.deletePerson(person);
                    finish();

                    Intent intentPersonsView = new Intent(NewPerson.this, PersonsActivity.class);
                    startActivityForResult(intentPersonsView, 123);
                }
            }
        });


        //Visibility of buttons
        if(isEditPerson) {
            saveNewPersonButton.setVisibility(View.GONE);
        }else {
            updatePersonButton.setVisibility(View.GONE);
            deletePersonButton.setVisibility(View.GONE);
        }


    }
}