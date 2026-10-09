package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;

// Use-case «Получить текущего пользователя».
// Может использоваться, например, для отображения email в шапке экрана.
public class GetCurrentUserUseCase {

    private final AuthRepository authRepository;

    public GetCurrentUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Возвращает пользователя или null, если никто не авторизован.
    public User execute() {
        return authRepository.getCurrentUser();
    }
}