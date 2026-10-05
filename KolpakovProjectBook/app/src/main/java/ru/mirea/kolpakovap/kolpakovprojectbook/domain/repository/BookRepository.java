package ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository;

import java.util.List;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

// Интерфейс описывает контракт для доступа к данным о книгах.
public interface BookRepository {

    // Возвращает список всех книг.
    List<Book> getBooks();

    // Находит книгу по её ID. Возвращает null, если не найдена.
    Book getBookById(int id);

    // Сохраняет книгу (обновляет или добавляет).
    boolean saveBook(Book book);

    // Возвращает список избранных книг.
    List<Book> getFavoriteBooks();

    // Добавляет книгу в избранное.
    boolean addToFavorites(Book book);

    // Удаляет книгу из избранного по ID. ← НОВЫЙ МЕТОД
    boolean removeFromFavoritesById(int id);
}