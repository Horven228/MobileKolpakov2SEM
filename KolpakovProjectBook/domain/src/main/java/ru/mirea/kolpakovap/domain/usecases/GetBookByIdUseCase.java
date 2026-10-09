package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Получить книгу по ID».
public class GetBookByIdUseCase {

    private final BookRepository bookRepository;

    public GetBookByIdUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Делегирует получение книги репозиторию.
    // Может вернуть null, если книга с таким ID не найдена.
    public Book execute(int id) {
        return bookRepository.getBookById(id);
    }
}