package ru.mirea.kolpakovap.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

// Хранилище данных клиента на SharedPreferences.
// Используется как альтернатива Room/сети для простых пар «ключ-значение»:
// UID пользователя и email, полученные после успешного входа через Firebase.
// По заданию ПР2 — это «первый способ обработки данных».
public class ClientStorage {

    // Имя файла настроек — приватный для приложения.
    private static final String PREF_NAME = "client_prefs";
    // Ключ для UID пользователя (Firebase UID).
    private static final String KEY_UID = "client_uid";
    // Ключ для email пользователя.
    private static final String KEY_EMAIL = "client_email";

    // Ссылка на SharedPreferences, полученная в конструкторе.
    private final SharedPreferences prefs;

    // Конструктор принимает Context из presentation или data-репозитория.
    public ClientStorage(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Сохранить данные клиента после входа.
    // apply() — асинхронная запись, достаточно для таких мелких данных.
    public void save(String uid, String email) {
        prefs.edit()
                .putString(KEY_UID, uid)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    // Получить email последнего входа (или null, если не сохранён).
    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    // Получить uid последнего входа (или null, если не сохранён).
    public String getUid() {
        return prefs.getString(KEY_UID, null);
    }

    // Очистить данные при выходе. clear() удаляет всё содержимое
    // файла client_prefs, включая email и uid.
    public void clear() {
        prefs.edit().clear().apply();
    }
}