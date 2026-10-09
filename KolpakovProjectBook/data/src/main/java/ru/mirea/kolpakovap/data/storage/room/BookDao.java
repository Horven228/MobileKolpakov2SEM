package ru.mirea.kolpakovap.data.storage.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// DAO (Data Access Object) — интерфейс с SQL-запросами.
// Room генерирует реализацию на этапе компиляции.
// Все запросы — обычный SQL, только с аннотациями.
@Dao
public interface BookDao {

    // Получить все книги из таблицы books.
    @Query("SELECT * FROM books")
    List<BookEntity> getAll();

    // Книга по id. LIMIT 1 — на случай дублирования, но id — primary key,
    // так что результат всегда один.
    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    BookEntity getById(int id);

    // Вставка. REPLACE — если запись с таким id уже есть, она перезаписывается.
    // Это удобно для универсального метода save (insert-or-update).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookEntity book);

    // Обновление существующей записи. Room определит по primary key.
    @Update
    void update(BookEntity book);

    // Удаление по id.
    @Query("DELETE FROM books WHERE id = :id")
    void deleteById(int id);

    // Список избранного: favorite = 1 (Room хранит boolean как INTEGER 0/1).
    @Query("SELECT * FROM books WHERE favorite = 1")
    List<BookEntity> getFavorites();

    // Пометить книгу как избранную.
    @Query("UPDATE books SET favorite = 1 WHERE id = :id")
    void markFavorite(int id);

    // Снять пометку избранного.
    @Query("UPDATE books SET favorite = 0 WHERE id = :id")
    void unmarkFavorite(int id);
}