package ru.mirea.kolpakovap.domain.repository;

// Интерфейс описывает контракт для получения курса валют.
public interface CurrencyRepository {

    // Возвращает курс валют в виде JSON-строки.
    String getCurrencyRate();
}