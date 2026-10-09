package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.repository.CurrencyRepository;

// Use-case «Получить курс валют» (внешний сервис).
public class GetCurrencyUseCase {

    private final CurrencyRepository currencyRepository;

    public GetCurrencyUseCase(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // Возвращает курс валют в виде JSON-строки.
    public String execute() {
        return currencyRepository.getCurrencyRate();
    }
}