package com.orania.gemobox.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_person")
public class Person implements Parcelable {

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    @NotNull
    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(@NotNull String personFirstName) {
        this.personFirstName = personFirstName;
    }

    @NotNull
    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(@NotNull String personLastName) {
        this.personLastName = personLastName;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")
    private long idPerson;

    @NotNull
    @ColumnInfo(name = "person_first_name")
    private String personFirstName;

    @NotNull
    @ColumnInfo(name = "person_last_name")
    private String personLastName;

    public Person() {
    }

    public Person(@NotNull String personFirstName, @NotNull String personLastName) {
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
    }

    protected Person(Parcel in) {

        Log.d("GIL", "Parcel in ");

        idPerson = in.readLong();
        personFirstName = in.readString();
        personLastName = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idPerson);
        dest.writeString(personFirstName);
        dest.writeString(personLastName);
    }
}
