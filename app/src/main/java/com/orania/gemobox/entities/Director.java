package com.orania.gemobox.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "table_director", foreignKeys = {
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = {"movie_id"},
                        childColumns = {"movie_id"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Person.class,
                        parentColumns = {"person_id"},
                        childColumns = {"person_id"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class Director {
    public long getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(long idDirector) {
        this.idDirector = idDirector;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "director_id")
    private long idDirector;

    @ColumnInfo(name = "movie_id", index = true)
    private long idMovie;

    @ColumnInfo(name = "person_id", index = true)
    private long idPerson;

    public Director() {
    }

    public Director(long idMovie, long idPerson) {
        this.idMovie = idMovie;
        this.idPerson = idPerson;
    }
}
