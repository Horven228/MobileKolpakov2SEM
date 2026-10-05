
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Use-case «Просмотреть страницу книги» (получить книгу по ID).
public class GetBookByIdUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public GetBookByIdUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Возвращает книгу по её ID (или null, если не найдена).
    public Book execute(int id) {
        // Делегируем поиск репозиторию.
        return bookRepository.getBookById(id);
    }
}