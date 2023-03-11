package com.orania.gemobox.model;

import android.app.Application;

import com.orania.gemobox.data.GemoboxRepository;
import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.relations.MovieWithDirectors;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GemoboxViewModel extends AndroidViewModel {

    public static GemoboxRepository repository;
    public final LiveData<List<MovieWithDirectors>> allMovies;
    public final LiveData<List<Person>> allPersons;


    public GemoboxViewModel(@NonNull Application application) {
        super(application);
        repository = new GemoboxRepository(application);
        allMovies = repository.getAllDataMovies();
        allPersons = repository.getAllDataPerson();
    }

    public LiveData<List<MovieWithDirectors>> getAllMovies() {
        return allMovies;
    }
    public LiveData<List<Person>> getAllPersons() { return allPersons; }

    public static void insertPerson(Person person) {
        repository.insertPerson(person);
    }

    public LiveData<Person> getPerson(long id) {
        return repository.getPerson(id);
    }

    public static void updatePerson(Person person) {
        repository.updatePerson(person);
    }

    public static void deletePerson(Person person) {
        repository.deletePerson(person);
    }

    public static void insertMovie(Movie movie) { repository.insertMovie(movie);}

    public LiveData<Movie> getMovie(long id) {
        return repository.getMovie(id);
    }

    public static void updateMovie(Movie movie) {
        repository.updateMovie(movie);
    }

    public static void deleteMovie(Movie movie) {
        repository.deleteMovie(movie);
    }
}
