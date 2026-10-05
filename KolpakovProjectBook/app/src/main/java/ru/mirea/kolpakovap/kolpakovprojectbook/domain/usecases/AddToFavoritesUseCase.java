
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Use-case «Добавить в избранное».
public class AddToFavoritesUseCase {

    // Ссылка на репозиторий книг (интерфейс из domain).
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор (Dependency Injection).
    public AddToFavoritesUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Выполняет добавление книги в избранное.
    public boolean execute(Book book) {
        // Защита от null.
        if (book == null) return false;
        // Делегируем работу репозиторию.
        return bookRepository.addToFavorites(book);
    }
}