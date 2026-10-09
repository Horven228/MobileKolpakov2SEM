package ru.mirea.kolpakovap.data.storage.room;

import android.content.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.kolpakovap.data.storage.BookStorage;
import ru.mirea.kolpakovap.data.storage.models.BookData;

// Реализация хранилища через Room (SQLite).
// Класс знает только о DAO и моделях data-слоя.
// Наружу отдаёт BookData, а не BookEntity — благодаря этому
// вышестоящие компоненты не зависят от деталей Room.
public class RoomBookStorage implements BookStorage {

    // DAO — интерфейс доступа к таблице книг, сгенерированный Room.
    private final BookDao bookDao;

    // В конструкторе получаем БД и достаём DAO.
    // AppDatabase.getInstance(context) — синглтон, чтобы не открывать
    // несколько соединений с одной и той же базой.
    public RoomBookStorage(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        bookDao = db.bookDao();
    }

    // Получить все книги. Room возвращает список Entity,
    // превращаем его в список BookData.
    @Override
    public List<BookData> getAll() {
        List<BookEntity> entities = bookDao.getAll();
        List<BookData> result = new ArrayList<>();
        for (BookEntity e : entities) result.add(toData(e));
        return result;
    }

    // Получить книгу по ID. Если не найдена — возвращаем null.
    @Override
    public BookData getById(int id) {
        BookEntity e = bookDao.getById(id);
        return e == null ? null : toData(e);
    }

    // Сохранить книгу. Внутри Room insert с OnConflictStrategy.REPLACE
    // перезапишет запись, если такой id уже есть.
    @Override
    public boolean save(BookData book) {
        bookDao.insert(toEntity(book));
        return true;
    }

    // Удалить книгу по ID.
    @Override
    public boolean deleteById(int id) {
        bookDao.deleteById(id);
        return true;
    }

    // Получить только избранные книги.
    @Override
    public List<BookData> getFavorites() {
        List<BookEntity> entities = bookDao.getFavorites();
        List<BookData> result = new ArrayList<>();
        for (BookEntity e : entities) result.add(toData(e));
        return result;
    }

    // Пометить книгу как избранную (SQL UPDATE favorite = 1).
    @Override
    public boolean addToFavorites(int id) {
        bookDao.markFavorite(id);
        return true;
    }

    // Снять пометку избранного (SQL UPDATE favorite = 0).
    @Override
    public boolean removeFromFavorites(int id) {
        bookDao.unmarkFavorite(id);
        return true;
    }

    // Маппер Entity → BookData.
    // Нужен, чтобы не «протаскивать» Room-сущность наружу из data-слоя.
    private BookData toData(BookEntity e) {
        return new BookData(e.id, e.title, e.author, e.favorite, e.savedDate);
    }

    // Маппер BookData → Entity.
    // Если дата сохранения не задана, подставляем текущую.
    private BookEntity toEntity(BookData d) {
        String date = d.getSavedDate() != null ? d.getSavedDate() : LocalDate.now().toString();
        return new BookEntity(d.getId(), d.getTitle(), d.getAuthor(), d.isFavorite(), date);
    }
}