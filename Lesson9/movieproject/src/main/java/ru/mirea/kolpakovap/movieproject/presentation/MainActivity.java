
package ru.mirea.kolpakovap.movieproject.presentation;


import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import ru.mirea.kolpakovap.movieproject.R;

import ru.mirea.kolpakovap.movieproject.data.repository.MovieRepositoryImpl;

import ru.mirea.kolpakovap.movieproject.domain.models.Movie;

import ru.mirea.kolpakovap.movieproject.domain.repository.MovieRepository;

import ru.mirea.kolpakovap.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.kolpakovap.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

// Главный экран приложения.
public class MainActivity extends AppCompatActivity {

    // Вызывается при создании активности.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Стандартная инициализация.
        super.onCreate(savedInstanceState);
        // Привязываем разметку activity_main.xml.
        setContentView(R.layout.activity_main);

        // Находим поле ввода названия фильма.
        EditText editTextMovie = findViewById(R.id.editTextMovie);
        // Находим TextView для вывода результата.
        TextView textViewMovie = findViewById(R.id.textViewMovie);

        // Создаём реализацию репозитория, передаём Context.
        MovieRepository movieRepository = new MovieRepositoryImpl(this);

        // Обработчик кнопки «Сохранить любимый фильм».
        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Читаем введённое название.
                String name = editTextMovie.getText().toString();
                // Создаём объект Movie.
                Movie movie = new Movie(2, name);
                // Вызываем use-case сохранения.
                boolean result = new SaveFilmToFavoriteUseCase(movieRepository).execute(movie);
                // Показываем результат на экране.
                textViewMovie.setText(String.format("Save result: %s", result));
            }
        });

        // Обработчик кнопки «Отобразить любимый фильм».
        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем фильм через use-case.
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                // Показываем название сохранённого фильма.
                textViewMovie.setText(String.format("Favorite movie: %s", movie.getName()));
            }
        });
    }
}