
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.models;

// Класс Book — сущность, представляющая книгу.
public class Book {

    // Уникальный идентификатор книги.
    private int id;

    // Название книги.
    private String title;

    // Автор книги.
    private String author;

    // Флаг: находится ли книга в избранном.
    private boolean isFavorite;

    // Конструктор: создаёт книгу с заданными id, названием и автором.
    // По умолчанию книга не в избранном.
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isFavorite = false;
    }

    // Возвращает ID книги.
    public int getId() { return id; }

    // Возвращает название книги.
    public String getTitle() { return title; }

    // Возвращает автора книги.
    public String getAuthor() { return author; }

    // Возвращает true, если книга в избранном.
    public boolean isFavorite() { return isFavorite; }

    // Устанавливает флаг избранного.
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    // Переопределяем toString() для удобного вывода книги в UI.
    @Override
    public String toString() {
        return title + " — " + author;
    }
}