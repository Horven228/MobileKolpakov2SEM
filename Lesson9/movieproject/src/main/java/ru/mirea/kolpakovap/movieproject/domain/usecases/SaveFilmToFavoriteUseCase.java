
package ru.mirea.kolpakovap.movieproject.domain.usecases;


import ru.mirea.kolpakovap.movieproject.domain.models.Movie;

import ru.mirea.kolpakovap.movieproject.domain.repository.MovieRepository;

// Use-case «Сохранить любимый фильм».
public class SaveFilmToFavoriteUseCase {

    // Ссылка на репозиторий.
    private final MovieRepository movieRepository;

    // Внедряем репозиторий через конструктор.
    public SaveFilmToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Сохраняет фильм после проверки названия.
    public boolean execute(Movie movie) {
        // Если название пустое (или из одних пробелов) — не сохраняем.
        if (movie.getName().trim().isEmpty()) {
            return false;
        }
        // Делегируем сохранение репозиторию.
        return movieRepository.saveMovie(movie);
    }
}