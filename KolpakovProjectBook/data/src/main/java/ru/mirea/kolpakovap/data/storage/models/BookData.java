package ru.mirea.kolpakovap.data.storage.models;

// Модель данных слоя data. Используется только внутри storage-реализаций
// (RoomBookStorage, SharedPrefBookStorage и т.п.).
// Отделена от domain.models.Book, чтобы слой data не зависел от domain
// в обратную сторону и был полностью автономным модулем.
public class BookData {
    // Уникальный идентификатор книги.
    private int id;
    // Название.
    private String title;
    // Автор.
    private String author;
    // Флаг, находится ли книга в избранном.
    private boolean favorite;
    // Дата сохранения записи (строка формата ISO).
    private String savedDate;

    // Конструктор: создаёт модель с полным набором полей.
    public BookData(int id, String title, String author, boolean favorite, String savedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.favorite = favorite;
        this.savedDate = savedDate;
    }

    // Геттеры — доступ к полям только на чтение.
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isFavorite() { return favorite; }
    public String getSavedDate() { return savedDate; }
}