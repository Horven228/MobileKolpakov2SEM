package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.repository.NetworkApi;

// Use-case «Получить книги из сети». Возвращает сырую JSON-строку.
public class GetBooksFromNetworkUseCase {

    private final NetworkApi networkApi;

    public GetBooksFromNetworkUseCase(NetworkApi networkApi) {
        this.networkApi = networkApi;
    }

    // Возвращает JSON как строку — распарсить её можно выше, в presentation,
    // или сделать отдельный use-case для парсинга.
    public String execute() {
        return networkApi.getBooksJson();
    }
}