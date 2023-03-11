package com.orania.gemobox.data;

import android.app.Application;

import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.relations.MovieWithDirectors;
import com.orania.gemobox.util.GemoboxRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GemoboxRepository {
    private GemoboxDao gemoboxDao;
    private LiveData<List<MovieWithDirectors>> allMovies;
    private LiveData<List<Person>> allPersons;

    public GemoboxRepository(Application application) {
        GemoboxRoomDatabase db = GemoboxRoomDatabase.getDatabase(application);
        gemoboxDao = db.allDao();
        allMovies = gemoboxDao.getAllMoviesWithDirectors();
        allPersons = gemoboxDao.getAllPersons();
    }

    public LiveData<List<MovieWithDirectors>> getAllDataMovies() {
        return allMovies;
    }
    public LiveData<List<Person>> getAllDataPerson() { return allPersons; }

    public void insertPerson(Person person){
        GemoboxRoomDatabase.databaseWriteExecutor.execute(() ->{
            gemoboxDao.insertPerson(person);
        });
    }

    public LiveData<Person> getPerson(long id) {
        return gemoboxDao.getPerson(id);
    }

    public void updatePerson(Person person) {
        GemoboxRoomDatabase.databaseWriteExecutor.execute(() -> gemoboxDao.updatePerson(person));
    }

    public void deletePerson(Person person) {
        GemoboxRoomDatabase.databaseWriteExecutor.execute(() -> gemoboxDao.deletePerson(person));
    }

    public void insertMovie(Movie movie) {
        GemoboxRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gemoboxDao.insertMovie(movie);
            }
        });
    }

    public LiveData<Movie> getMovie(long id) {
        return gemoboxDao.getMovie(id);
    }

    public void updateMovie(Movie movie) {
        GemoboxRoomDatabase.databaseWriteExecutor.execute(() -> gemoboxDao.updateMovie(movie));
    }

    public void deleteMovie(Movie movie) {
        GemoboxRoomDatabase.databaseWriteExecutor.execute(() -> gemoboxDao.deleteMovie(movie));
    }


}
