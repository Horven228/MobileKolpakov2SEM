package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;

import java.util.List;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Use-case «Просмотреть избранное».
public class GetFavoriteBooksUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public GetFavoriteBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Возвращает список избранных книг.
    public List<Book> execute() {
        // Делегируем получение репозиторию.
        return bookRepository.getFavoriteBooks();
    }
}