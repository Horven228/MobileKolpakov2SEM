package ru.mirea.kolpakovap.data.repository;

import java.time.LocalDate;

import ru.mirea.kolpakovap.data.storage.MovieStorage;
import ru.mirea.kolpakovap.data.storage.models.MovieData;
import ru.mirea.kolpakovap.domain.models.Movie;
import ru.mirea.kolpakovap.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return movieStorage.save(mapToStorage(movie));
    }

    @Override
    public Movie getMovie() {
        return mapToDomain(movieStorage.get());
    }

    private MovieData mapToStorage(Movie movie) {
        return new MovieData(movie.getId(), movie.getName(), LocalDate.now().toString());
    }

    private Movie mapToDomain(MovieData data) {
        return new Movie(data.getId(), data.getName());
    }
}