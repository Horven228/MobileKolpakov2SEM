package ru.mirea.kolpakovap.domain.repository;

import ru.mirea.kolpakovap.domain.models.User;

// Контракт репозитория авторизации. Лежит в domain,
// чтобы presentation работал с интерфейсом, а не с реализацией Firebase.
public interface AuthRepository {

    // Обобщённый callback для асинхронных операций.
    // Firebase работает асинхронно, поэтому результат логина/регистрации
    // возвращается не сразу, а через методы onSuccess/onError.
    interface Callback<T> {
        void onSuccess(T result);
        void onError(String message);
    }

    // Вход в систему по email/password. Асинхронный.
    void login(String email, String password, Callback<User> callback);

    // Регистрация нового пользователя. Асинхронный.
    void register(String email, String password, Callback<User> callback);

    // Выход из системы. Синхронный — сбрасывает сессию и локальные данные.
    void logout();

    // Получить текущего пользователя. Может вернуть null.
    User getCurrentUser();

    // Проверить, авторизован ли кто-то прямо сейчас.
    boolean isLoggedIn();
}