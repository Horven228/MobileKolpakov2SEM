package ru.mirea.kolpakovap.kolpakovprojectbook.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;

public class BookRepositoryImpl implements BookRepository {

    private final List<Book> books = new ArrayList<>();
    private final List<Book> favorites = new ArrayList<>();

    public BookRepositoryImpl() {
        books.add(new Book(1, "Война и мир", "Л. Н. Толстой"));
        books.add(new Book(2, "Преступление и наказание", "Ф. М. Достоевский"));
        books.add(new Book(3, "Мастер и Маргарита", "М. А. Булгаков"));
        books.add(new Book(4, "1984", "Джордж Оруэлл"));
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    @Override
    public boolean saveBook(Book book) {
        if (book == null) return false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.set(i, book);
                return true;
            }
        }
        books.add(book);
        return true;
    }

    @Override
    public List<Book> getFavoriteBooks() {
        return favorites;
    }

    @Override
    public boolean addToFavorites(Book book) {
        if (book == null) return false;
        book.setFavorite(true);
        if (!favorites.contains(book)) {
            favorites.add(book);
        }
        return true;
    }

    // Удаляет книгу из избранного по ID. ← НОВЫЙ МЕТОД
    @Override
    public boolean removeFromFavoritesById(int id) {
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == id) {
                favorites.get(i).setFavorite(false);
                favorites.remove(i);
                return true;
            }
        }
        return false;
    }
}