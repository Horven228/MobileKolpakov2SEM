package ru.mirea.kolpakovap.domain.usecases;

import java.util.List;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Получить список всех книг».
public class GetBooksUseCase {

    private final BookRepository bookRepository;

    public GetBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Делегирует получение списка репозиторию.
    public List<Book> execute() {
        return bookRepository.getBooks();
    }
}