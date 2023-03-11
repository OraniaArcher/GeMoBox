package com.orania.gemobox.data;

import com.orania.gemobox.entities.Director;
import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.relations.MovieWithDirectors;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface GemoboxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertPerson(Person p);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertDirector(Director d);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertMovie(Movie m);

    @Transaction
    @Query("SELECT * FROM table_movie")
    LiveData<List<MovieWithDirectors>> getAllMoviesWithDirectors();

    @Transaction
    @Query("SELECT * FROM table_person")
    LiveData<List<Person>> getAllPersons();

    @Transaction
    @Query("SELECT * FROM table_person WHERE table_person.person_id == :id")
    LiveData<Person> getPerson(long id);

    @Transaction
    @Query("SELECT * FROM table_movie WHERE table_movie.movie_id == :id")
    LiveData<Movie> getMovie(long id);

    @Update
    void updatePerson(Person p);

    @Update
    void updateMovie(Movie m);

    @Delete
    void deletePerson(Person p);

    @Delete
    void deleteMovie(Movie m);

    @Query("DELETE FROM table_person")
    void deleteAllPerson();

    @Query("DELETE FROM table_director")
    void deleteAllDirector();

    @Query("DELETE FROM table_movie")
    void deleteAllMovie();

}
