
package ru.mirea.kolpakovap.kolpakovprojectbook.presentation;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import ru.mirea.kolpakovap.kolpakovprojectbook.R;

import ru.mirea.kolpakovap.kolpakovprojectbook.data.repository.AuthRepositoryImpl;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.AuthRepository;

import ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases.AuthUseCase;

// Экран авторизации: ввод логина/пароля, переход на главный экран
public class LoginActivity extends AppCompatActivity {

    // Вызывается при создании активности.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Стандартная инициализация.
        super.onCreate(savedInstanceState);
        // Привязываем разметку activity_login.xml.
        setContentView(R.layout.activity_login);

        // Находим поле ввода логина.
        EditText editTextLogin = findViewById(R.id.editTextLogin);
        // Находим поле ввода пароля.
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        // Находим TextView для вывода ошибки.
        TextView textViewError = findViewById(R.id.textViewError);
        // Находим кнопку «Войти».
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Создаём реализацию репозитория авторизации.
        AuthRepository authRepository = new AuthRepositoryImpl();

        // Обработчик нажатия на кнопку «Войти».
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Читаем логин, убираем пробелы по краям.
                String login = editTextLogin.getText().toString().trim();
                // Читаем пароль, убираем пробелы по краям.
                String password = editTextPassword.getText().toString().trim();

                // Вызываем use-case авторизации.
                boolean ok = new AuthUseCase(authRepository).execute(login, password);

                // Если авторизация успешна...
                if (ok) {
                    // Создаём Intent для перехода на MainActivity.
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // Запускаем MainActivity.
                    startActivity(intent);
                    // Закрываем LoginActivity, чтобы нельзя было вернуться назад.
                    finish();
                } else {
                    // Иначе показываем сообщение об ошибке.
                    textViewError.setText("Неверный логин или пароль");
                }
            }
        });
    }
}