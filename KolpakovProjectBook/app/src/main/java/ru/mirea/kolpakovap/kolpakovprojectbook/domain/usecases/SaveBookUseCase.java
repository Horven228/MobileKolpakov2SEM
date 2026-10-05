
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Сохраняет книгу после проверки названия
// Use-case «Сохранить в БД».
public class SaveBookUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public SaveBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Сохраняет книгу после проверки входных данных.
    public boolean execute(Book book) {
        // Проверяем, что книга не null и название не пустое (без учёта пробелов).
        if (book == null || book.getTitle().trim().isEmpty()) return false;
        // Делегируем сохранение репозиторию.
        return bookRepository.saveBook(book);
    }
}