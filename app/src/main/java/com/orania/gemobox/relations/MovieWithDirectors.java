package com.orania.gemobox.relations;

import com.orania.gemobox.entities.Director;
import com.orania.gemobox.entities.Movie;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MovieWithDirectors {
    @Embedded
    public Movie movie;

    @Relation(
            entity = Director.class,
            parentColumn = "movie_id",
            entityColumn = "movie_id"
    )
    public List<MovieDirectorWithPerson> movieWithDirectors;


}
