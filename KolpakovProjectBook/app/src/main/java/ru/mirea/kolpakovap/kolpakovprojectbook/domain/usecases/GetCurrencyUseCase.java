// Пакет слоя domain — use-case классы.
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;

// Импортируем интерфейс CurrencyRepository.
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CurrencyRepository;

// Use-case «Получить курс валют» (внешний сервис).
public class GetCurrencyUseCase {

    // Ссылка на репозиторий курса валют.
    private final CurrencyRepository currencyRepository;

    // Внедряем репозиторий через конструктор.
    public GetCurrencyUseCase(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // Возвращает курс валют в виде JSON-строки.
    public String execute() {
        // Делегируем получение курса репозиторию.
        return currencyRepository.getCurrencyRate();
    }
}