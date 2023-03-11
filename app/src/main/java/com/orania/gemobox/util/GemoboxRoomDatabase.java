package com.orania.gemobox.util;

import android.content.Context;

import com.orania.gemobox.data.GemoboxDao;
import com.orania.gemobox.entities.Director;
import com.orania.gemobox.entities.Movie;
import com.orania.gemobox.entities.Person;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class, Director.class, Person.class}, version = 4, exportSchema = false)
public abstract class GemoboxRoomDatabase extends RoomDatabase {
    public abstract GemoboxDao allDao();
    public static final int NUMBER_OF_THREADS = 4;

    private static volatile GemoboxRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE table_movie "
                    +"ADD COLUMN movie_release_year INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public static GemoboxRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (GemoboxRoomDatabase.class){
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GemoboxRoomDatabase.class, "gemobox_database")
                            .addMigrations(MIGRATION_3_4)
                            .addCallback(sRoomdatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomdatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {

                    //Log.d("DUPA", "kwas");


                    super.onCreate(db);


                    databaseWriteExecutor.execute(() -> {
                        GemoboxDao gemoboxDao = INSTANCE.allDao();
                        //allDao.deleteAllPerson();

                        //allDao.insertDirector(new Director(12, 12));

                        //Person person = new Person("Ridley", "Scott");
                        //allDao.insertPerson(person);

                        //Movie movie = new Movie("Alien");
                        //allDao.insertMovie(movie);
                    });
                }
            };

}
