package ru.mirea.kolpakovap.data.storage.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Room-сущность. Соответствует таблице books в SQLite.
// @Entity(tableName = "books") — задаёт имя таблицы.
// Поля класса становятся колонками (если не помечены @Ignore).
@Entity(tableName = "books")
public class BookEntity {

    // Первичный ключ. По умолчанию Room использует его для поиска
    // и не даёт вставить две записи с одинаковым id.
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "author")
    public String author;

    // boolean хранится в SQLite как INTEGER (0/1).
    @ColumnInfo(name = "favorite")
    public boolean favorite;

    // Имя колонки — "saved_date", а поле — savedDate.
    // snake_case для колонок — соглашение SQL.
    @ColumnInfo(name = "saved_date")
    public String savedDate;

    // Room требует либо конструктор без аргументов, либо конструктор,
    // который принимает все поля (как здесь). Room использует его
    // при чтении записей из БД.
    public BookEntity(int id, String title, String author, boolean favorite, String savedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.favorite = favorite;
        this.savedDate = savedDate;
    }
}