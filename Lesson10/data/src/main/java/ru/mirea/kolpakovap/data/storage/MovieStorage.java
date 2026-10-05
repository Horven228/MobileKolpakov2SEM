package ru.mirea.kolpakovap.data.storage;

import ru.mirea.kolpakovap.data.storage.models.MovieData;

public interface MovieStorage {
    MovieData get();
    boolean save(MovieData movie);
}