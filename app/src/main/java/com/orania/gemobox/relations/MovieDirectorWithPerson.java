package com.orania.gemobox.relations;

import com.orania.gemobox.entities.Director;
import com.orania.gemobox.entities.Person;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MovieDirectorWithPerson {
    @Embedded
    public Director director;

    @Relation(
            entity = Person.class,
            parentColumn = "person_id",
            entityColumn = "person_id"
    )
    public List<Person> personList;

}
