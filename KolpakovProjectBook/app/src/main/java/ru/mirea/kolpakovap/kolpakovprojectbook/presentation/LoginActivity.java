package ru.mirea.kolpakovap.kolpakovprojectbook.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.kolpakovap.kolpakovprojectbook.R;
import ru.mirea.kolpakovap.data.repository.AuthRepositoryImpl;
import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;
import ru.mirea.kolpakovap.domain.usecases.AuthUseCase;

// Экран авторизации. Первый экран, который видит пользователь при запуске.
// Отвечает за ввод e-mail и пароля, вызов use-case авторизации, переход
// на MainActivity при успехе или на экран регистрации.
public class LoginActivity extends AppCompatActivity {

    // Ссылка на репозиторий авторизации. Тип — интерфейс из domain,
    // чтобы presentation не зависел от конкретной реализации (Firebase).
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Создаём реализацию репозитория авторизации.
        // Конструктор теперь требует Context — он нужен для работы FirebaseAuth.
        authRepository = new AuthRepositoryImpl(this);

        // Принудительный выход при каждом открытии экрана входа —
        // сессия Firebase НЕ переносится между запусками приложения.
        // Так пользователь при каждом старте обязан залогиниться заново.
        authRepository.logout();

        // Находим все элементы UI по их id из activity_login.xml.
        EditText email = findViewById(R.id.editTextLogin);
        EditText password = findViewById(R.id.editTextPassword);
        TextView error = findViewById(R.id.textViewError);
        Button login = findViewById(R.id.buttonLogin);
        Button guest = findViewById(R.id.buttonGuest);
        TextView register = findViewById(R.id.textViewRegister);

        // Обработчик кнопки «Войти».
        // Вызываем use-case AuthUseCase, передаём введённые email/password
        // и callback, который вызывается по завершении асинхронной операции.
        login.setOnClickListener(v -> new AuthUseCase(authRepository).execute(
                email.getText().toString(),
                password.getText().toString(),
                new AuthRepository.Callback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        // Успех: переходим на главный экран.
                        goToMain();
                    }

                    @Override
                    public void onError(String message) {
                        // Ошибка: показываем сообщение в TextView.
                        error.setText(message);
                    }
                }
        ));

        // Клик по надписи «Регистрация» открывает экран регистрации.
        register.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        // Кнопка «Войти как гость» — переходим на главный экран без авторизации.
        // Гостю часть функций (избранное, распознавание) может быть недоступна.
        guest.setOnClickListener(v -> goToMain());
    }

    // Вспомогательный метод перехода на главный экран.
    // finish() закрывает текущую Activity, чтобы пользователь
    // не мог вернуться назад на экран логина системной кнопкой «Назад».
    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}