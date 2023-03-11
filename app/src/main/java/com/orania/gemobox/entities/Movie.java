package com.orania.gemobox.entities;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_movie")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private long idMovie;

    @NotNull
    @ColumnInfo(name = "movie_title")
    private String movieTitle;

    @ColumnInfo(name = "movie_ticket")
    private String movieTicket;

    @ColumnInfo(name = "movie_runtime")
    private int movieRuntime;

    @ColumnInfo(name = "movie_release_year")
    private int movieReleaseYear;

    public Movie() {
    }

    public Movie(@NotNull String movieTitle, String movieTicket, int movieRuntime, int movieReleaseYear) {
        this.movieTitle = movieTitle;
        this.movieTicket = movieTicket;
        this.movieRuntime = movieRuntime;
        this.movieReleaseYear = movieReleaseYear;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setMovieTitle(@NotNull String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMovieTicket(String movieTicket) {
        this.movieTicket = movieTicket;
    }

    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public void setMovieReleaseYear(int movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

    public long getIdMovie() {
        return idMovie;
    }

    @NotNull
    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieTicket() {
        return movieTicket;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public int getMovieReleaseYear() {
        return movieReleaseYear;
    }
}
