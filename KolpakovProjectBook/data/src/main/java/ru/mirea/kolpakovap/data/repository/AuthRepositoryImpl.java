package ru.mirea.kolpakovap.data.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.kolpakovap.data.storage.sharedprefs.ClientStorage;
import ru.mirea.kolpakovap.domain.models.User;
import ru.mirea.kolpakovap.domain.repository.AuthRepository;

// Реализация репозитория авторизации поверх Firebase Auth.
// Это «второй источник данных» в контрольном задании ПР2, но фактически
// он объединяет два механизма: Firebase (сеть) + SharedPreferences (локально).
public class AuthRepositoryImpl implements AuthRepository {

    // Ссылка на FirebaseAuth — точка входа во все операции входа/регистрации/выхода.
    private final FirebaseAuth firebaseAuth;
    // Локальное хранилище UID и email — переживает перезапуск приложения.
    private final ClientStorage clientStorage;

    // Конструктор принимает Context — он нужен для ClientStorage.
    // FirebaseAuth.getInstance() — синглтон, работает без контекста.
    public AuthRepositoryImpl(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        clientStorage = new ClientStorage(context);
    }

    // Вход по email/password. Асинхронный: результат приходит через Callback.
    @Override
    public void login(String email, String password, Callback<User> callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    FirebaseUser fbUser = result.getUser();
                    if (fbUser != null) {
                        // Оборачиваем FirebaseUser в domain-модель User,
                        // чтобы presentation не зависел от Firebase-классов.
                        User user = new User(fbUser.getUid(), fbUser.getEmail());
                        // Сохраняем данные клиента в SharedPreferences —
                        // чтобы при следующем запуске можно было показать,
                        // кто вошёл, без повторного обращения к Firebase.
                        clientStorage.save(user.getUid(), user.getEmail());
                        callback.onSuccess(user);
                    } else {
                        // Странный случай: успех, но пользователя нет.
                        callback.onError("Не удалось получить данные пользователя");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    // Регистрация нового пользователя. Логика зеркальна login.
    @Override
    public void register(String email, String password, Callback<User> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    FirebaseUser fbUser = result.getUser();
                    if (fbUser != null) {
                        User user = new User(fbUser.getUid(), fbUser.getEmail());
                        clientStorage.save(user.getUid(), user.getEmail());
                        callback.onSuccess(user);
                    } else {
                        callback.onError("Не удалось создать пользователя");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    // Выход из аккаунта. Сбрасываем и сессию Firebase, и локальные данные.
    @Override
    public void logout() {
        firebaseAuth.signOut();
        clientStorage.clear();
    }

    // Получить текущего пользователя по данным Firebase.
    // Может вернуть null, если никто не авторизован.
    @Override
    public User getCurrentUser() {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) return null;
        return new User(fbUser.getUid(), fbUser.getEmail());
    }

    // Проверка, авторизован ли кто-то прямо сейчас.
    @Override
    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
}