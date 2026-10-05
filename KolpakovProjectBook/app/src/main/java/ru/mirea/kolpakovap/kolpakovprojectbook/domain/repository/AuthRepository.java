
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository;

// Интерфейс описывает контракт для работы с авторизацией (login, isLoggedIn, logout)
// Реализация будет в слое data (AuthRepositoryImpl).
public interface AuthRepository {

    // Метод входа в систему.
    // Принимает логин и пароль, возвращает true при успехе.
    boolean login(String username, String password);

    // Проверка, авторизован ли пользователь.
    boolean isLoggedIn();

    // Выход из системы.
    void logout();
}