package ru.mirea.kolpakovap.data.repository;

import ru.mirea.kolpakovap.domain.repository.CurrencyRepository;

// Реализация интерфейса CurrencyRepository из domain.
// Имитирует получение курсов валют от внешнего сервиса.
// Как и NetworkApiImpl, возвращает захардкоженную JSON-строку,
// чтобы продемонстрировать работу с «внешними данными» без реальной сети.
public class CurrencyRepositoryImpl implements CurrencyRepository {

    // Возвращает курс валют в виде JSON-строки.
    @Override
    public String getCurrencyRate() {
        // Захардкоженный ответ, имитирующий JSON от внешнего API.
        // Ключ — код валюты, значение — курс к рублю.
        return "{\"USD\": 92.5, \"EUR\": 100.3}";
    }
}