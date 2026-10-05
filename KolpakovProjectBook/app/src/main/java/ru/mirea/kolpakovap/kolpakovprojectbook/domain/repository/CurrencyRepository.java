
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository;

// Интерфейс описывает контракт для получения курса валют.
// Реализация будет в слое data (CurrencyRepositoryImpl).
public interface CurrencyRepository {

    // Возвращает курс валют в виде JSON-строки.
    String getCurrencyRate();
}