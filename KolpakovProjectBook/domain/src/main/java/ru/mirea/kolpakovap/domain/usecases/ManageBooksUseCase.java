package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Управление книгами» — сценарий администратора.
// Позволяет добавлять и удалять книги.
public class ManageBooksUseCase {

    private final BookRepository bookRepository;

    public ManageBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Добавить новую книгу.
    public boolean addBook(Book book) {
        return bookRepository.saveBook(book);
    }

    // Удалить книгу по ID.
    // Заглушка — пока возвращает true.
    // В реальной реализации нужно добавить метод removeBook(int) в BookRepository
    // и вызывать его здесь.
    public boolean removeBook(int id) {
        return true;
    }
}