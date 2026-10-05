
package ru.mirea.kolpakovap.kolpakovprojectbook.data.repository;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CoverRecognizer;

// Возвращает строку «Распознана обложка: …»
// Класс реализует интерфейс CoverRecognizer из domain.
public class CoverRecognizerImpl implements CoverRecognizer {

    // «Распознаёт» обложку книги.
    @Override
    public String recognizeCover(Book book) {
        // Если книга не передана — сообщаем об ошибке.
        if (book == null) return "Книга не найдена";

        // Имитируем результат распознавания.
        return "Распознана обложка: " + book.getTitle() + " (" + book.getAuthor() + ")";
    }
}