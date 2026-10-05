
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import java.util.List;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Возвращает список всех книг
// Use-case «Просмотреть список книг».
public class GetBooksUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public GetBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Возвращает список всех книг.
    public List<Book> execute() {
        // Делегируем получение списка репозиторию.
        return bookRepository.getBooks();
    }
}