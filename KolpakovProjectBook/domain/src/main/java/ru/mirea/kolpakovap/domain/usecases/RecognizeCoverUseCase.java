package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.CoverRecognizer;

// Use-case «Распознать обложку». Делегирует в CoverRecognizer.
public class RecognizeCoverUseCase {

    private final CoverRecognizer coverRecognizer;

    public RecognizeCoverUseCase(CoverRecognizer coverRecognizer) {
        this.coverRecognizer = coverRecognizer;
    }

    // Возвращает строку с результатом распознавания.
    // Реализация — в data-слое (сейчас заглушка, в будущем — TensorFlow Lite).
    public String execute(Book book) {
        return coverRecognizer.recognizeCover(book);
    }
}