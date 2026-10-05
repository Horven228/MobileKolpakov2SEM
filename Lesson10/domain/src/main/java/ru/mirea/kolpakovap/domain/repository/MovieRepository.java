
package ru.mirea.kolpakovap.domain.repository;


import ru.mirea.kolpakovap.domain.models.Movie;

// Интерфейс описывает контракт для доступа к данным о фильме.
// Реализация будет в слое data (MovieRepositoryImpl).
public interface MovieRepository {

    // Сохраняет фильм. Возвращает true при успехе.
    boolean saveMovie(Movie movie);

    // Возвращает сохранённый фильм.
    Movie getMovie();
}