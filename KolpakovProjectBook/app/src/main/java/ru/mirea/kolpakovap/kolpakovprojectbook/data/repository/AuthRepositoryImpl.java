// Пакет слоя data — здесь лежат реализации репозиториев.
package ru.mirea.kolpakovap.kolpakovprojectbook.data.repository;

// Импортируем интерфейс из domain, чтобы реализовать его.
import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.AuthRepository;

// Проверяет логин/пароль, хранит флаг входа
// Класс реализует интерфейс AuthRepository из domain.
public class AuthRepositoryImpl implements AuthRepository {

    // Флаг: авторизован ли пользователь.
    private boolean loggedIn = false;

    // Имитация входа в систему.
    @Override
    public boolean login(String username, String password) {
        // Проверяем, что оба поля не null и не пустые.
        if (username != null && !username.isEmpty()
                && password != null && !password.isEmpty()) {
            // Запоминаем, что пользователь вошёл.
            loggedIn = true;
            // Возвращаем успех.
            return true;
        }
        // Данные не прошли проверку — отказ.
        return false;
    }

    // Проверка текущего состояния авторизации.
    @Override
    public boolean isLoggedIn() {
        // Возвращаем сохранённый флаг.
        return loggedIn;
    }

    // Выход из системы — сбрасываем флаг.
    @Override
    public void logout() {
        // Помечаем пользователя как неавторизованного.
        loggedIn = false;
    }
}