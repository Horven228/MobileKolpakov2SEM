package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;

// Use-case «Авторизация». Содержит валидацию входных данных
// и делегирует сам вход в репозиторий.
public class AuthUseCase {

    private final AuthRepository authRepository;

    public AuthUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Проверяем email и пароль на минимальные требования,
    // затем вызываем репозиторий. Результат асинхронный — через callback.
    public void execute(String email, String password, AuthRepository.Callback<User> callback) {
        // Пустой email — сразу ошибка, без обращения к Firebase.
        if (email == null || email.trim().isEmpty()) {
            callback.onError("Email не может быть пустым");
            return;
        }
        // Firebase требует пароль не короче 6 символов — проверяем заранее.
        if (password == null || password.length() < 6) {
            callback.onError("Пароль должен быть не короче 6 символов");
            return;
        }
        // Всё хорошо — делегируем.
        authRepository.login(email.trim(), password, callback);
    }
}