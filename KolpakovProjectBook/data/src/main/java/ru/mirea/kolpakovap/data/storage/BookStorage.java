package ru.mirea.kolpakovap.data.storage;

import java.util.List;

import ru.mirea.kolpakovap.data.storage.models.BookData;

// Контракт хранилища книг. Лежит в слое data, потому что работает
// исключительно с data-моделями (BookData) и не нужен domain.
// Реализации могут быть разные: Room, SharedPreferences, сеть —
// интерфейс остаётся неизменным.
public interface BookStorage {

    // Все книги в хранилище.
    List<BookData> getAll();

    // Книга по ID, либо null, если не найдена.
    BookData getById(int id);

    // Сохранить книгу (добавить или обновить).
    boolean save(BookData book);

    // Удалить книгу по ID.
    boolean deleteById(int id);

    // Список избранных книг.
    List<BookData> getFavorites();

    // Пометить книгу как избранную.
    boolean addToFavorites(int id);

    // Убрать книгу из избранного.
    boolean removeFromFavorites(int id);
}