package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Удалить из избранного по ID».
public class RemoveFromFavoritesUseCase {

    private final BookRepository bookRepository;

    public RemoveFromFavoritesUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Делегирует удаление в репозиторий.
    public boolean execute(int id) {
        return bookRepository.removeFromFavoritesById(id);
    }
}