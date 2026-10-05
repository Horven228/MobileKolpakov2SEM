
package ru.mirea.kolpakovap.kolpakovprojectbook.domain.usecases;


import ru.mirea.kolpakovap.kolpakovprojectbook.domain.repository.AuthRepository;

// Выполняет вход в систему с логином и паролем
// Use-case «Авторизоваться».
public class AuthUseCase {

    // Ссылка на репозиторий авторизации.
    private final AuthRepository authRepository;

    // Внедряем репозиторий через конструктор.
    public AuthUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Выполняет вход в систему с указанными логином и паролем.
    public boolean execute(String username, String password) {
        // Делегируем проверку репозиторию.
        return authRepository.login(username, password);
    }
}