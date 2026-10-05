
package ru.mirea.kolpakovap.movieproject.data.repository;


import android.content.Context;

import android.content.SharedPreferences;


import ru.mirea.kolpakovap.movieproject.domain.models.Movie;

import ru.mirea.kolpakovap.movieproject.domain.repository.MovieRepository;

// Класс реализует интерфейс MovieRepository из domain.
// Использует SharedPreferences — данные переживают перезапуск приложения.
public class MovieRepositoryImpl implements MovieRepository {

    // Имя файла настроек, в котором хранится название фильма.
    private static final String PREF_NAME = "movie_prefs";

    // Ключ, под которым хранится название фильма.
    private static final String KEY_MOVIE_NAME = "movie_name";

    // Ссылка на SharedPreferences.
    private final SharedPreferences sharedPreferences;

    // Конструктор принимает Context (передаётся из presentation).
    public MovieRepositoryImpl(Context context) {
        // Получаем доступ к приватному файлу настроек приложения.
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Сохраняет фильм в SharedPreferences.
    @Override
    public boolean saveMovie(Movie movie) {
        // Открываем редактор для записи.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Кладём название фильма по ключу.
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        // Применяем изменения (асинхронно).
        editor.apply();
        // Возвращаем успех.
        return true;
    }

    // Возвращает сохранённый фильм.
    @Override
    public Movie getMovie() {
        // Читаем название из SharedPreferences.
        // Если ничего не сохранено — вернётся "Game of Thrones" по умолчанию.
        String name = sharedPreferences.getString(KEY_MOVIE_NAME, "Game of Thrones");
        // Создаём объект Movie и возвращаем его.
        return new Movie(1, name);
    }
}