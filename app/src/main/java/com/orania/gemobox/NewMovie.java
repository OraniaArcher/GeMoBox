package com.orania.gemobox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.model.GemoboxViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class NewMovie extends AppCompatActivity {
    public static final String MOVIE_TITLE_REPLY = "movie_title_reply";
    public static final String MOVIE_ORDINAL_REPLY = "movie_ordinal_reply";
    public static final String MOVIE_RUNTIME_REPLY = "movie_runtime_reply";
    private static final int SELECT_DIRECTORS_ACTIVITY_REQUEST_CODE = 1;
    private EditText enterMovieTitle;
    private EditText enterMovieOrdinal;
    private EditText enterMovieRuntime;
    private EditText enterMovieReleaseYear;
    private Button saveNewMovieButton;
    private Button salectDirectorsButton;
    private long movieId = 0;
    private Boolean isEditMovie = false;
    private Button updateMovieButton;
    private Button deleteMovieButton;

    private GemoboxViewModel gemoboxViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);

        enterMovieTitle = findViewById(R.id.enter_movie_title);
        enterMovieOrdinal = findViewById(R.id.enter_movie_ordinal);
        enterMovieRuntime = findViewById(R.id.enter_movie_runtime);
        enterMovieReleaseYear = findViewById(R.id.enter_movie_release_year);
        saveNewMovieButton = findViewById(R.id.save_movie_button);
        salectDirectorsButton = findViewById(R.id.select_directors_button);

        gemoboxViewModel = new ViewModelProvider.AndroidViewModelFactory(NewMovie.this
                .getApplication())
                .create(GemoboxViewModel.class);

        if(getIntent().hasExtra(MoviesActivity.MOVIE_ID)) {
            movieId = getIntent().getLongExtra(MoviesActivity.MOVIE_ID, 0);

            gemoboxViewModel.getMovie(movieId).observe(this, new Observer<Movie>() {
                @SuppressLint("ResourceType")
                @Override
                public void onChanged(Movie movie) {
                    if(movie != null) {
                        enterMovieTitle.setText(movie.getMovieTitle());
                        enterMovieOrdinal.setText(movie.getMovieTicket());
                        enterMovieRuntime.setText(String.valueOf(movie.getMovieRuntime()));
                        enterMovieReleaseYear.setText(String.valueOf(movie.getMovieReleaseYear()));
                    }
                }
            });
            isEditMovie = true;

        }

        //Select directors button
        salectDirectorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPersonsView = new Intent(NewMovie.this, PersonsActivity.class);
                startActivityForResult(intentPersonsView, SELECT_DIRECTORS_ACTIVITY_REQUEST_CODE);
            }
        });


        //Save new movie button
        saveNewMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if(!TextUtils.isEmpty(enterMovieTitle.getText())) {
                    String movieTitle = enterMovieTitle.getText().toString();
                    String movieOrdinal = enterMovieOrdinal.getText().toString();
                    Integer movieRuntime = Integer.parseInt(enterMovieRuntime.getText().toString());
                    Integer movieReleaseYear = Integer.parseInt(enterMovieReleaseYear.getText().toString());

                    replyIntent.putExtra(MOVIE_TITLE_REPLY, movieTitle);
                    replyIntent.putExtra(MOVIE_ORDINAL_REPLY, movieOrdinal);
                    replyIntent.putExtra(MOVIE_RUNTIME_REPLY, movieRuntime);
                    replyIntent.putExtra(MOVIE_RUNTIME_REPLY, movieReleaseYear);
                    setResult(RESULT_OK, replyIntent);

                    //Movie movie = new Movie(enterMovieTitle.getText().toString(), enterMovieOrdinal.getText().toString(), 0, 0);
                    //GemoboxViewModel.insertMovie(movie);


                }else {
                    setResult(RESULT_CANCELED, replyIntent);
                    //Toast.makeText(NewMovie.this, R.string.empty_movie_title, Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        //Update button
        updateMovieButton = findViewById(R.id.update_movie_button);
        updateMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieTitle = enterMovieTitle.getText().toString().trim();
                String movieOrdinalSymbol = enterMovieOrdinal.getText().toString().trim();
                Integer movieRuntime = Integer.parseInt(enterMovieRuntime.getText().toString().trim());
                Integer movieReleaseYear = Integer.parseInt(enterMovieReleaseYear.getText().toString());

                if(TextUtils.isEmpty(movieTitle)) {
                    Snackbar.make(enterMovieTitle, R.string.empty_movie_title, Snackbar.LENGTH_SHORT).show();
                }else {
                    Movie movie = new Movie();
                    movie.setIdMovie(movieId);
                    movie.setMovieTitle(movieTitle);
                    movie.setMovieTicket(movieOrdinalSymbol);
                    movie.setMovieRuntime(movieRuntime);
                    movie.setMovieReleaseYear(movieReleaseYear);
                    GemoboxViewModel.updateMovie(movie);
                    finish();
                }
            }
        });

        //Delete button
        deleteMovieButton = findViewById(R.id.delete_movie_button);
        deleteMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieTitle = enterMovieTitle.getText().toString().trim();
                String movieOrdinalSymbol = enterMovieOrdinal.getText().toString().trim();

                if(TextUtils.isEmpty(movieTitle)) {
                    Snackbar.make(enterMovieTitle, R.string.empty_movie_title, Snackbar.LENGTH_SHORT).show();
                }else {
                    Movie movie = new Movie();
                    movie.setIdMovie(movieId);
                    movie.setMovieTitle(movieTitle);
                    movie.setMovieTicket(movieOrdinalSymbol);
                    GemoboxViewModel.deleteMovie(movie);
                    finish();
                }
            }
        });

        //Buttons visibility
        if(isEditMovie) {
            saveNewMovieButton.setVisibility(View.GONE);
        }else {
            updateMovieButton.setVisibility(View.GONE);
            deleteMovieButton.setVisibility(View.GONE);
        }
    }
}