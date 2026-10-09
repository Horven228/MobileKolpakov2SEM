package ru.mirea.kolpakovap.kolpakovprojectbook.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.kolpakovap.data.network.NetworkApiImpl;
import ru.mirea.kolpakovap.data.repository.AuthRepositoryImpl;
import ru.mirea.kolpakovap.data.repository.BookRepositoryImpl;
import ru.mirea.kolpakovap.data.repository.CoverRecognizerImpl;
import ru.mirea.kolpakovap.data.repository.CurrencyRepositoryImpl;
import ru.mirea.kolpakovap.data.storage.room.RoomBookStorage;
import ru.mirea.kolpakovap.domain.models.Book;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;
import ru.mirea.kolpakovap.domain.repository.BookRepository;
import ru.mirea.kolpakovap.domain.repository.CoverRecognizer;
import ru.mirea.kolpakovap.domain.repository.CurrencyRepository;
import ru.mirea.kolpakovap.domain.repository.NetworkApi;
import ru.mirea.kolpakovap.domain.usecases.AddToFavoritesUseCase;
import ru.mirea.kolpakovap.domain.usecases.GetBookByIdUseCase;
import ru.mirea.kolpakovap.domain.usecases.GetBooksFromNetworkUseCase;
import ru.mirea.kolpakovap.domain.usecases.GetBooksUseCase;
import ru.mirea.kolpakovap.domain.usecases.GetCurrencyUseCase;
import ru.mirea.kolpakovap.domain.usecases.GetFavoriteBooksUseCase;
import ru.mirea.kolpakovap.domain.usecases.LogoutUseCase;
import ru.mirea.kolpakovap.domain.usecases.RecognizeCoverUseCase;
import ru.mirea.kolpakovap.domain.usecases.RemoveFromFavoritesUseCase;
import ru.mirea.kolpakovap.domain.usecases.SaveBookUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.R;

// Главный экран приложения. Набор кнопок, каждая из которых
// демонстрирует отдельный сценарий: работа с БД (Room), сеть (NetworkApi),
// SharedPreferences (сессия пользователя), распознавание обложек.
public class MainActivity extends AppCompatActivity {

    // Репозитории и сеть. Типы — интерфейсы из domain, чтобы
    // presentation не зависел от конкретных реализаций.
    private BookRepository bookRepository;
    private CurrencyRepository currencyRepository;
    private CoverRecognizer coverRecognizer;
    private AuthRepository authRepository;
    private NetworkApi networkApi;

    // UI-элементы: вывод результата и поле ввода ID.
    private TextView textViewResult;
    private EditText editTextBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаём реализации репозиториев.
        // BookRepositoryImpl теперь принимает RoomBookStorage — данные
        // хранятся в SQLite через Room, а не в памяти.
        bookRepository = new BookRepositoryImpl(new RoomBookStorage(this));
        currencyRepository = new CurrencyRepositoryImpl();
        coverRecognizer = new CoverRecognizerImpl();
        authRepository = new AuthRepositoryImpl(this);
        networkApi = new NetworkApiImpl();

        // Первичное заполнение БД тестовыми книгами — только если база пуста.
        // Делаем в отдельном потоке, потому что Room запрещает работу в UI-потоке.
        new Thread(() -> {
            if (bookRepository.getBooks().isEmpty()) {
                bookRepository.saveBook(new Book(1, "Война и мир", "Л. Н. Толстой"));
                bookRepository.saveBook(new Book(2, "Преступление и наказание", "Ф. М. Достоевский"));
                bookRepository.saveBook(new Book(3, "Мастер и Маргарита", "М. А. Булгаков"));
                bookRepository.saveBook(new Book(4, "1984", "Джордж Оруэлл"));
            }
        }).start();

        // Находим UI-элементы.
        textViewResult = findViewById(R.id.textViewResult);
        editTextBookId = findViewById(R.id.editTextBookId);

        Button btnBooks = findViewById(R.id.buttonBooks);
        Button btnBookById = findViewById(R.id.buttonBookById);
        Button btnSaveBook = findViewById(R.id.buttonSaveBook);
        Button btnAddFavorite = findViewById(R.id.buttonAddFavorite);
        Button btnFavorites = findViewById(R.id.buttonFavorites);
        Button btnRemoveFavorite = findViewById(R.id.buttonRemoveFavorite);
        Button btnCurrency = findViewById(R.id.buttonCurrency);
        Button btnNetworkBooks = findViewById(R.id.buttonNetworkBooks);
        Button btnRecognize = findViewById(R.id.buttonRecognize);
        Button btnLogout = findViewById(R.id.buttonLogout);

        // Список книг — вызов use-case, который дёргает репозиторий,
        // а тот через RoomBookStorage читает данные из SQLite.
        btnBooks.setOnClickListener(v -> {
            List<Book> books = new GetBooksUseCase(bookRepository).execute();
            StringBuilder sb = new StringBuilder("Книги:\n");
            for (Book b : books) sb.append(b.toString()).append("\n");
            textViewResult.setText(sb.toString());
        });

        // Книга по ID — вспомогательный метод findBookById() читает
        // число из EditText и возвращает объект Book или null.
        btnBookById.setOnClickListener(v -> {
            Book book = findBookById();
            if (book == null) return;
            textViewResult.setText("Книга: " + book.toString());
        });

        // Сохранить в БД — SaveBookUseCase сначала проверяет,
        // что у книги непустое название, и только потом делегирует в репозиторий.
        btnSaveBook.setOnClickListener(v -> {
            Book book = findBookById();
            if (book == null) return;
            boolean ok = new SaveBookUseCase(bookRepository).execute(book);
            textViewResult.setText(ok ? "Сохранено в БД: " + book : "Не удалось сохранить");
        });

        // Добавить в избранное — AddToFavoritesUseCase помечает книгу
        // флагом isFavorite и добавляет её в список избранного.
        btnAddFavorite.setOnClickListener(v -> {
            Book book = findBookById();
            if (book == null) return;
            boolean ok = new AddToFavoritesUseCase(bookRepository).execute(book);
            textViewResult.setText(ok ? "В избранном: " + book : "Не удалось добавить");
        });

        // Показать избранное — GetFavoriteBooksUseCase возвращает
        // список книг, помеченных как избранные.
        btnFavorites.setOnClickListener(v -> {
            List<Book> favorites = new GetFavoriteBooksUseCase(bookRepository).execute();
            if (favorites.isEmpty()) {
                textViewResult.setText("Избранное пусто");
                return;
            }
            StringBuilder sb = new StringBuilder("Избранное:\n");
            for (Book b : favorites) sb.append(b.toString()).append("\n");
            textViewResult.setText(sb.toString());
        });

        // Удалить из избранного по ID — читаем ID, вызываем use-case,
        // который попросит репозиторий удалить книгу из списка избранного.
        btnRemoveFavorite.setOnClickListener(v -> {
            String input = editTextBookId.getText().toString().trim();
            if (input.isEmpty()) {
                textViewResult.setText("Введите ID книги");
                return;
            }
            int id = Integer.parseInt(input);
            boolean ok = new RemoveFromFavoritesUseCase(bookRepository).execute(id);
            textViewResult.setText(ok
                    ? "Удалено из избранного: ID " + id
                    : "Книга с ID " + id + " не найдена в избранном");
        });

        // Получить курс валют — CurrencyRepositoryImpl возвращает
        // захардкоженную JSON-строку (имитация внешнего API).
        btnCurrency.setOnClickListener(v -> {
            String rate = new GetCurrencyUseCase(currencyRepository).execute();
            textViewResult.setText("Курс: " + rate);
        });

        // Получить книги из сети (мок) — NetworkApiImpl возвращает
        // строку в формате JSON, имитирующую ответ сервера.
        btnNetworkBooks.setOnClickListener(v -> {
            String json = new GetBooksFromNetworkUseCase(networkApi).execute();
            textViewResult.setText("JSON от NetworkApi:\n" + json);
        });

        // Распознать обложку по ID — RecognizeCoverUseCase делегирует
        // в CoverRecognizerImpl, который имитирует вывод ML-модели.
        btnRecognize.setOnClickListener(v -> {
            Book book = findBookById();
            if (book == null) return;
            String result = new RecognizeCoverUseCase(coverRecognizer).execute(book);
            textViewResult.setText(result);
        });

        // Выйти — сбрасываем сессию Firebase + чистим SharedPreferences.
        // Флаги NEW_TASK | CLEAR_TASK полностью очищают стек Activity,
        // чтобы пользователь не мог вернуться на MainActivity кнопкой «Назад».
        btnLogout.setOnClickListener(v -> {
            new LogoutUseCase(authRepository).execute();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Вспомогательный метод: читает ID из EditText, ищет книгу через use-case.
    // Возвращает null и пишет сообщение об ошибке, если ID пуст или книга не найдена.
    private Book findBookById() {
        String input = editTextBookId.getText().toString().trim();
        if (input.isEmpty()) {
            textViewResult.setText("Введите ID книги");
            return null;
        }
        int id = Integer.parseInt(input);
        Book book = new GetBookByIdUseCase(bookRepository).execute(id);
        if (book == null) {
            textViewResult.setText("Книга с ID " + id + " не найдена");
            return null;
        }
        return book;
    }
}