package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Сохранить книгу». Содержит бизнес-проверку названия.
public class SaveBookUseCase {

    private final BookRepository bookRepository;

    public SaveBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Проверяет, что книга не null и название непустое (без учёта пробелов),
    // затем делегирует сохранение в репозиторий.
    public boolean execute(Book book) {
        if (book == null || book.getTitle().trim().isEmpty()) return false;
        return bookRepository.saveBook(book);
    }
}