// Пакет слоя domain — модели предметной области.
package ru.mirea.kolpakovap.domain.models;

// Класс Movie — сущность, представляющая фильм.
public class Movie {

    // Уникальный идентификатор фильма.
    private int id;

    // Название фильма.
    private String name;

    // Конструктор: создаёт фильм с заданными id и названием.
    public Movie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Возвращает ID фильма.
    public int getId() {
        return id;
    }

    // Возвращает название фильма.
    public String getName() {
        return name;
    }
}