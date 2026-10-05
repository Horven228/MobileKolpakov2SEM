
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CoverRecognizer;

// Use-case «Распознать обложку» (ML-модель).
public class RecognizeCoverUseCase {

    // Ссылка на распознаватель обложек.
    private final CoverRecognizer coverRecognizer;

    // Внедряем распознаватель через конструктор.
    public RecognizeCoverUseCase(CoverRecognizer coverRecognizer) {
        this.coverRecognizer = coverRecognizer;
    }

    // Распознаёт обложку для переданной книги.
    public String execute(Book book) {
        // Делегируем распознавание репозиторию-распознавателю.
        return coverRecognizer.recognizeCover(book);
    }
}