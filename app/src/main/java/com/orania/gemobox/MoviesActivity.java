package com.orania.gemobox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orania.gemobox.adapter.MoviesRCVAdapter;
import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.model.GemoboxViewModel;
import com.orania.gemobox.relations.MovieWithDirectors;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity implements MoviesRCVAdapter.OnMovieClickListener {
    private static final int NEW_MOVIE_ACTIVITY_REQUEST_CODE = 1;
    public static final String MOVIE_ID = "movie_id";
    private GemoboxViewModel gemoboxViewModel;
    private RecyclerView recyclerMovieView;
    private MoviesRCVAdapter recyclerMovieViewAdapter;
    private LiveData<List<MovieWithDirectors>> moviesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recyclerMovieView = findViewById(R.id.recycler_movie_view);
        recyclerMovieView.setHasFixedSize(true);
        recyclerMovieView.setLayoutManager(new LinearLayoutManager(this));

        gemoboxViewModel = new ViewModelProvider.AndroidViewModelFactory(MoviesActivity.this
                .getApplication())
                .create(GemoboxViewModel.class);

        gemoboxViewModel.getAllMovies().observe(this, movieWithDirectors -> {
            //Setup adapter
            recyclerMovieViewAdapter = new MoviesRCVAdapter(movieWithDirectors, MoviesActivity.this, this);
            recyclerMovieView.setAdapter(recyclerMovieViewAdapter);
        });



        FloatingActionButton fab = findViewById(R.id.add_movie_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoviesActivity.this, NewMovie.class);
                startActivityForResult(intent, NEW_MOVIE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String movieTitle = data.getStringExtra(NewMovie.MOVIE_TITLE_REPLY);
            String movieOrdinal = data.getStringExtra(NewMovie.MOVIE_ORDINAL_REPLY);

            Movie movie = new Movie(movieTitle, movieOrdinal, 133, 1999);

            GemoboxViewModel.insertMovie(movie);

            //Log.d("TAG", "onActivityResult: " + data.getStringExtra(NewMovie.MOVIE_TITLE_REPLY));
            //Log.d("TAG", "onActivityResult: " + data.getStringExtra(NewMovie.MOVIE_ORDINAL_REPLY));
        }
    }

    @Override
    public void onMovieClick(int position) {
        Movie movie = gemoboxViewModel.getAllMovies().getValue().get(position).movie;
        Log.d("CLICK", "onMovie object click: " + movie.getMovieTitle());

        Intent intent = new Intent(MoviesActivity.this, NewMovie.class);
        intent.putExtra(MOVIE_ID, movie.getIdMovie());
        startActivity(intent);


    }
}