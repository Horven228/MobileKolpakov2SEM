package ru.mirea.kolpakovap.domain.models;

// Сущность «Пользователь» на уровне domain.
// Отделена от FirebaseUser — чтобы presentation и domain
// не зависели от классов Firebase напрямую.
public class User {
    // UID пользователя (уникальный идентификатор в Firebase).
    private final String uid;
    // E-mail пользователя.
    private final String email;

    // Конструктор. Поля final — объект неизменяемый после создания.
    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    // Геттеры.
    public String getUid() { return uid; }
    public String getEmail() { return email; }
}