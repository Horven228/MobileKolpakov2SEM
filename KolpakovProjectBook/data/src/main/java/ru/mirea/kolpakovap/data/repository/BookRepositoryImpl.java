package ru.mirea.kolpakovap.data.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.kolpakovap.data.storage.BookStorage;
import ru.mirea.kolpakovap.data.storage.models.BookData;
import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.BookRepository;

// Репозиторий книг. Связывает domain-модель Book с data-моделью BookData
// через мапперы. Сам данные не хранит — делегирует в BookStorage.
// Благодаря этому можно подменить RoomBookStorage на SharedPrefBookStorage
// или на сетевую реализацию, не меняя этот класс.
public class BookRepositoryImpl implements BookRepository {

    // Хранилище, через которое читаем/пишем данные.
    private final BookStorage bookStorage;

    // Внедрение зависимости через конструктор — классический DI.
    public BookRepositoryImpl(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    // Все книги: читаем из storage, конвертируем каждую BookData → Book.
    @Override
    public List<Book> getBooks() {
        List<BookData> data = bookStorage.getAll();
        List<Book> result = new ArrayList<>();
        for (BookData d : data) result.add(toDomain(d));
        return result;
    }

    // Книга по ID. Если storage вернул null — возвращаем null.
    @Override
    public Book getBookById(int id) {
        BookData d = bookStorage.getById(id);
        return d == null ? null : toDomain(d);
    }

    // Сохранить книгу: Book → BookData → storage.
    @Override
    public boolean saveBook(Book book) {
        if (book == null) return false;
        return bookStorage.save(toData(book));
    }

    // Список избранного: тоже через storage, с конвертацией в domain-модели.
    @Override
    public List<Book> getFavoriteBooks() {
        List<BookData> data = bookStorage.getFavorites();
        List<Book> result = new ArrayList<>();
        for (BookData d : data) result.add(toDomain(d));
        return result;
    }

    // Добавить книгу в избранное.
    @Override
    public boolean addToFavorites(Book book) {
        if (book == null) return false;
        // Сначала убедимся, что книга вообще есть в БД —
        // иначе UPDATE не сработает (нечего обновлять).
        BookData existing = bookStorage.getById(book.getId());
        if (existing == null) {
            // Сохраняем, потом помечаем избранной.
            bookStorage.save(toData(book));
        }
        return bookStorage.addToFavorites(book.getId());
    }

    // Удалить из избранного — storage сам делает UPDATE favorite = 0.
    @Override
    public boolean removeFromFavoritesById(int id) {
        return bookStorage.removeFromFavorites(id);
    }

    // ---- Мапперы ----

    // BookData → Book. Переносим флаг «избранное» отдельно,
    // потому что в domain-модели Book он не задаётся в конструкторе.
    private Book toDomain(BookData d) {
        Book book = new Book(d.getId(), d.getTitle(), d.getAuthor());
        book.setFavorite(d.isFavorite());
        return book;
    }

    // Book → BookData. Дату сохранения проставляем текущую — это деталь
    // хранения, домену о ней знать не нужно.
    private BookData toData(Book book) {
        return new BookData(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.isFavorite(),
                LocalDate.now().toString()
        );
    }
}