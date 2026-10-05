package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Use-case «Удалить из избранного по ID».
public class RemoveFromFavoritesUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public RemoveFromFavoritesUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Удаляет книгу из избранного по ID.
    public boolean execute(int id) {
        // Делегируем удаление репозиторию.
        return bookRepository.removeFromFavoritesById(id);
    }
}