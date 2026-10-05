package ru.mirea.kolpakovap.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;

import ru.mirea.kolpakovap.data.storage.MovieStorage;
import ru.mirea.kolpakovap.data.storage.models.MovieData;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String PREF_NAME = "movie_prefs";
    private static final String KEY_NAME = "movie_name";
    private static final String KEY_DATE = "movie_date";
    private static final String KEY_ID = "movie_id";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public MovieData get() {
        String name = sharedPreferences.getString(KEY_NAME, "unknown");
        String date = sharedPreferences.getString(KEY_DATE, String.valueOf(LocalDate.now()));
        int id = sharedPreferences.getInt(KEY_ID, -1);
        return new MovieData(id, name, date);
    }

    @Override
    public boolean save(MovieData movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, movie.getName());
        editor.putString(KEY_DATE, LocalDate.now().toString());
        editor.putInt(KEY_ID, 1);
        editor.apply();
        return true;
    }
}