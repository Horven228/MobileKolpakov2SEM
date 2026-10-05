// Пакет слоя domain — use-case классы.
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;

// Импортируем сущность Book.
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;
// Импортируем интерфейс BookRepository.
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

// Use-case «Управлять книгами» (доступен администратору).
public class ManageBooksUseCase {

    // Ссылка на репозиторий книг.
    private final BookRepository bookRepository;

    // Внедряем репозиторий через конструктор.
    public ManageBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Добавляет новую книгу.
    public boolean addBook(Book book) {
        // Делегируем сохранение репозиторию.
        return bookRepository.saveBook(book);
    }

    // Удаляет книгу по ID (заглушка — пока возвращает true).
    public boolean removeBook(int id) {
        // Имитация успешного удаления.
        return true;
    }
}