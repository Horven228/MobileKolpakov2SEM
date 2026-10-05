
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

// Интерфейс описывает контракт для распознавания обложек.
// Реализация будет в слое data (CoverRecognizerImpl).
public interface CoverRecognizer {

    // Распознаёт обложку для переданной книги.
    // Возвращает строку с результатом.
    String recognizeCover(Book book);
}