package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;

// Use-case «Регистрация». По структуре аналогичен AuthUseCase.
public class RegisterUseCase {

    private final AuthRepository authRepository;

    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Валидация email/password, затем делегирование в репозиторий.
    public void execute(String email, String password, AuthRepository.Callback<User> callback) {
        // Пустой email — ошибка.
        if (email == null || email.trim().isEmpty()) {
            callback.onError("Email не может быть пустым");
            return;
        }
        // Слишком короткий пароль — ошибка.
        if (password == null || password.length() < 6) {
            callback.onError("Пароль должен быть не короче 6 символов");
            return;
        }
        authRepository.register(email.trim(), password, callback);
    }
}