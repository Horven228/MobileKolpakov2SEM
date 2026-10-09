package ru.mirea.kolpakovap.data.repository;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.CoverRecognizer;

// Заглушка распознавателя обложек. По заданию ПР2 — имитация ML-модели.
// В реальном приложении здесь был бы вызов TensorFlow Lite
// или ML Kit с загрузкой модели и обработкой изображения.
public class CoverRecognizerImpl implements CoverRecognizer {

    // «Распознаёт» обложку. Пока просто формирует строку из
    // названия и автора — это демонстрация контракта,
    // а не настоящая работа с моделью.
    @Override
    public String recognizeCover(Book book) {
        if (book == null) return "Книга не найдена";
        return "Распознана обложка: " + book.getTitle() + " (" + book.getAuthor() + ")";
    }
}