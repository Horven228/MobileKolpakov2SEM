// Пакет слоя data — имитация внешнего сервиса.
package ru.mirea.kolpakovap.kolpakovprojectbook.data.repository;

// Импортируем интерфейс CurrencyRepository из domain.
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CurrencyRepository;

// Возвращает захардкоженную JSON-строку с курсами
// Класс реализует интерфейс CurrencyRepository из domain.
public class CurrencyRepositoryImpl implements CurrencyRepository {

    // Возвращает курс валют.
    @Override
    public String getCurrencyRate() {
        // Захардкоженный ответ, имитирующий JSON от внешнего API.
        return "{\"USD\": 92.5, \"EUR\": 100.3}";
    }
}