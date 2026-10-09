package ru.mirea.kolpakovap.domain.repository;

// Контракт для работы с внешним сетевым API.
// Лежит в domain, потому что это часть бизнес-логики приложения —
// получить данные извне. Конкретная реализация (Retrofit, OkHttp)
// находится в data.
public interface NetworkApi {
    // Возвращает JSON-строку со списком книг.
    String getBooksJson();
}