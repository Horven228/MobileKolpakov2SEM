package ru.mirea.kolpakovap.domain.usecases;

import java.util.List;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Use-case «Получить список избранных книг».
public class GetFavoriteBooksUseCase {

    private final BookRepository bookRepository;

    public GetFavoriteBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Возвращает список книг, помеченных как избранные.
    public List<Book> execute() {
        return bookRepository.getFavoriteBooks();
    }
}