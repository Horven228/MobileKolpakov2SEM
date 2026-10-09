package ru.mirea.kolpakovap.domain.usecases;

import ru.mirea.kolpakovap.domain.repository.AuthRepository;

// Use-case «Выход из системы».
public class LogoutUseCase {

    private final AuthRepository authRepository;

    public LogoutUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Сбрасывает сессию. Внутри AuthRepositoryImpl
    // это означает Firebase signOut + очистку SharedPreferences.
    public void execute() {
        authRepository.logout();
    }
}