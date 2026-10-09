package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Добавить книгу в избранное».
// Отвечает только за этот сценарий — ничего лишнего.
public class AddToFavoritesUseCase {

    // Репозиторий — интерфейс из domain.
    private final BookRepository bookRepository;

    // Внедрение зависимости через конструктор (DI).
    public AddToFavoritesUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Выполнить: проверить на null и делегировать в репозиторий.
    public boolean execute(Book book) {
        if (book == null) return false;
        return bookRepository.addToFavorites(book);
    }
}