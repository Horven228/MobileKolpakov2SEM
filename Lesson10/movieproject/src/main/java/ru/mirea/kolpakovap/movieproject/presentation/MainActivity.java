package ru.mirea.kolpakovap.movieproject.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.kolpakovap.movieproject.R;
import ru.mirea.kolpakovap.data.repository.MovieRepositoryImpl;
import ru.mirea.kolpakovap.data.storage.MovieStorage;
import ru.mirea.kolpakovap.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.kolpakovap.domain.models.Movie;
import ru.mirea.kolpakovap.domain.repository.MovieRepository;
import ru.mirea.kolpakovap.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.kolpakovap.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextMovie = findViewById(R.id.editTextMovie);
        TextView textViewMovie = findViewById(R.id.textViewMovie);

        MovieStorage movieStorage = new SharedPrefMovieStorage(this);
        MovieRepository movieRepository = new MovieRepositoryImpl(movieStorage);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextMovie.getText().toString();
                Movie movie = new Movie(2, name);
                boolean result = new SaveFilmToFavoriteUseCase(movieRepository).execute(movie);
                textViewMovie.setText(String.format("Save result: %s", result));
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                textViewMovie.setText(String.format("Favorite movie: %s", movie.getName()));
            }
        });
    }
}