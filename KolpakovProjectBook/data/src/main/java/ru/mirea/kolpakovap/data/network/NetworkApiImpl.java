package ru.mirea.kolpakovap.data.network;

import ru.mirea.kolpakovap.domain.repository.NetworkApi;

// Реализация интерфейса NetworkApi из domain.
// Имитирует ответ внешнего REST-сервиса, возвращающего JSON со списком книг.
// В реальном приложении здесь был бы HTTP-запрос через Retrofit/OkHttp,
// но по заданию ПР2 достаточно «замоканных» данных.
public class NetworkApiImpl implements NetworkApi {

    // Возвращает строку в формате JSON, как будто её вернул сервер.
    @Override
    public String getBooksJson() {
        // Замоканный JSON-ответ, имитирующий данные от внешнего сервиса.
        // Структура массива объектов: id, title, author, year.
        return "["
                + "{\"id\":5,\"title\":\"Тень и кость\",\"author\":\"Ли Бардуго\",\"year\":2012},"
                + "{\"id\":6,\"title\":\"Атомные привычки\",\"author\":\"Джеймс Клир\",\"year\":2018},"
                + "{\"id\":7,\"title\":\"Дизайн привычных вещей\",\"author\":\"Дональд Норман\",\"year\":1988},"
                + "{\"id\":8,\"title\":\"Искусство цвета\",\"author\":\"Иоханнес Иттен\",\"year\":1961}"
                + "]";
    }
}