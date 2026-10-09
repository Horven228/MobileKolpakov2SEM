package ru.mirea.kolpakovap.kolpakovprojectbook.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.kolpakovap.kolpakovprojectbook.R;
import ru.mirea.kolpakovap.data.repository.AuthRepositoryImpl;
import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;
import ru.mirea.kolpakovap.domain.usecases.RegisterUseCase;

// Экран регистрации. Пользователь вводит email, пароль и подтверждение пароля.
// После успешной регистрации возвращаемся на экран логина (finish()).
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Находим элементы UI по id из activity_register.xml.
        EditText email = findViewById(R.id.editTextRegisterEmail);
        EditText password = findViewById(R.id.editTextRegisterPassword);
        EditText confirm = findViewById(R.id.editTextRegisterConfirm);
        TextView error = findViewById(R.id.textViewRegisterError);
        Button register = findViewById(R.id.buttonRegister);

        // Создаём реализацию репозитория авторизации.
        // Конструктор требует Context — нужен для FirebaseAuth.
        AuthRepository authRepository = new AuthRepositoryImpl(this);

        // Обработчик кнопки «Зарегистрироваться».
        register.setOnClickListener(v -> {
            String e = email.getText().toString().trim();
            String p = password.getText().toString();
            String c = confirm.getText().toString();

            // Локальная проверка: пароли должны совпадать.
            // Делаем её до обращения к сети/Firebase — быстрее и без лишнего запроса.
            if (!p.equals(c)) {
                error.setText("Пароли не совпадают");
                return;
            }

            // Вызываем use-case регистрации. Он асинхронный,
            // поэтому результат приходит через callback.
            new RegisterUseCase(authRepository).execute(e, p, new AuthRepository.Callback<User>() {
                @Override
                public void onSuccess(User result) {
                    // Успех: показываем Toast с email и закрываем экран регистрации.
                    // Пользователь вернётся на LoginActivity и сможет войти.
                    Toast.makeText(RegisterActivity.this,
                            "Аккаунт создан: " + result.getEmail(),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String message) {
                    // Ошибка (например, email уже занят, слабый пароль):
                    // выводим сообщение под полями ввода.
                    error.setText(message);
                }
            });
        });
    }
}