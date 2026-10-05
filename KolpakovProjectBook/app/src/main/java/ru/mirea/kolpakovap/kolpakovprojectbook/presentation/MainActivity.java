package ru.mirea.kolpakovap.kolpakovprojectbook.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.kolpakovap.kolpakovprojectbook.R;
import ru.mirea.kolpakovap.kolpakovprojectbook.data.repository.BookRepositoryImpl;
import ru.mirea.kolpakovap.kolpakovprojectbook.data.repository.CoverRecognizerImpl;
import ru.mirea.kolpakovap.kolpakovprojectbook.data.repository.CurrencyRepositoryImpl;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.models.Book;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.BookRepository;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CoverRecognizer;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.CurrencyRepository;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.AddToFavoritesUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.GetBookByIdUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.GetBooksUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.GetCurrencyUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.GetFavoriteBooksUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.RecognizeCoverUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.RemoveFromFavoritesUseCase;
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.SaveBookUseCase;

// Главный экран приложения.
public class MainActivity extends AppCompatActivity {

    // Репозитории и распознаватель.
    private BookRepository bookRepository;
    private CurrencyRepository currencyRepository;
    private CoverRecognizer coverRecognizer;

    // TextView для вывода результата и EditText для ввода ID.
    private TextView textViewResult;
    private EditText editTextBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаём реализации репозиториев.
        bookRepository = new BookRepositoryImpl();
        currencyRepository = new CurrencyRepositoryImpl();
        coverRecognizer = new CoverRecognizerImpl();

        // Находим TextView и EditText.
        textViewResult = findViewById(R.id.textViewResult);
        editTextBookId = findViewById(R.id.editTextBookId);

        // Находим все кнопки.
        Button btnBooks = findViewById(R.id.buttonBooks);
        Button btnBookById = findViewById(R.id.buttonBookById);
        Button btnSaveBook = findViewById(R.id.buttonSaveBook);
        Button btnAddFavorite = findViewById(R.id.buttonAddFavorite);
        Button btnFavorites = findViewById(R.id.buttonFavorites);
        Button btnRemoveFavorite = findViewById(R.id.buttonRemoveFavorite);
        Button btnCurrency = findViewById(R.id.buttonCurrency);
        Button btnRecognize = findViewById(R.id.buttonRecognize);
        Button btnLogout = findViewById(R.id.buttonLogout);

        // Обработчик кнопки «Список книг».
        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = new GetBooksUseCase(bookRepository).execute();
                StringBuilder sb = new StringBuilder("Книги:\n");
                for (Book b : books) sb.append(b.toString()).append("\n");
                textViewResult.setText(sb.toString());
            }
        });

        // Обработчик кнопки «Показать книгу по ID».
        btnBookById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = findBookById();
                if (book == null) return;
                textViewResult.setText("Книга: " + book.toString());
            }
        });

        // Обработчик кнопки «Сохранить в БД».
        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = findBookById();
                if (book == null) return;
                boolean ok = new SaveBookUseCase(bookRepository).execute(book);
                if (ok) {
                    textViewResult.setText("Сохранено в БД: " + book.toString());
                } else {
                    textViewResult.setText("Не удалось сохранить");
                }
            }
        });

        // Обработчик кнопки «Добавить в избранное».
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = findBookById();
                if (book == null) return;
                boolean ok = new AddToFavoritesUseCase(bookRepository).execute(book);
                if (ok) {
                    textViewResult.setText("В избранном: " + book.toString());
                } else {
                    textViewResult.setText("Не удалось добавить");
                }
            }
        });

        // Обработчик кнопки «Показать избранное».
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем список избранных книг через use-case.
                List<Book> favorites = new GetFavoriteBooksUseCase(bookRepository).execute();
                // Если список пуст — сообщаем.
                if (favorites.isEmpty()) {
                    textViewResult.setText("Избранное пусто");
                    return;
                }
                // Формируем строку для вывода.
                StringBuilder sb = new StringBuilder("Избранное:\n");
                for (Book b : favorites) sb.append(b.toString()).append("\n");
                // Отображаем результат.
                textViewResult.setText(sb.toString());
            }
        });

        // Обработчик кнопки «Удалить из избранного по ID».
        btnRemoveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Читаем ID из поля.
                String input = editTextBookId.getText().toString().trim();
                if (input.isEmpty()) {
                    textViewResult.setText("Введите ID книги");
                    return;
                }
                int id = Integer.parseInt(input);
                // Вызываем use-case удаления из избранного.
                boolean ok = new RemoveFromFavoritesUseCase(bookRepository).execute(id);
                // Показываем результат.
                if (ok) {
                    textViewResult.setText("Удалено из избранного: ID " + id);
                } else {
                    textViewResult.setText("Книга с ID " + id + " не найдена в избранном");
                }
            }
        });

        // Обработчик кнопки «Получить курс валют».
        btnCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rate = new GetCurrencyUseCase(currencyRepository).execute();
                textViewResult.setText("Курс: " + rate);
            }
        });

        // Обработчик кнопки «Распознать обложку по ID».
        btnRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = findBookById();
                if (book == null) return;
                String result = new RecognizeCoverUseCase(coverRecognizer).execute(book);
                textViewResult.setText(result);
            }
        });

        // Обработчик кнопки «Выйти».
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Вспомогательный метод: читает ID, находит книгу.
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