
package ru.mirea.kolpakovap.domain.usecases;


import ru.mirea.kolpakovap.domain.models.Movie;

import ru.mirea.kolpakovap.domain.repository.MovieRepository;

// Use-case «Получить любимый фильм».
public class GetFavoriteFilmUseCase {

    // Ссылка на репозиторий (интерфейс из domain).
    private final MovieRepository movieRepository;

    // Внедряем репозиторий через конструктор (Dependency Injection).
    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Возвращает фильм из репозитория.
    public Movie execute() {
        // Делегируем получение репозиторию.
        return movieRepository.getMovie();
    }
}