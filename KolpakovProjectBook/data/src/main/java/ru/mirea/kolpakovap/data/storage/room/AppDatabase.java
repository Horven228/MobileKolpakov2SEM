package ru.mirea.kolpakovap.data.storage.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Главный класс Room-базы. Аннотация @Database сообщает Room,
// какие Entity входят в БД и какая версия схемы.
// entities = {BookEntity.class} — в базе одна таблица books.
// version = 1 — при изменении схемы номер надо увеличивать,
//              иначе Room не поймёт, что структура изменилась.
// exportSchema = false — не сохранять JSON со схемой (для учебного проекта ок).
@Database(entities = {BookEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Синглтон. volatile — чтобы изменения были видны всем потокам.
    private static volatile AppDatabase INSTANCE;

    // Абстрактный метод, через который получаем DAO.
    // Room сам сгенерирует реализацию.
    public abstract BookDao bookDao();

    // Получить единственный экземпляр БД.
    // Двойная проверка на null + synchronized — классический паттерн
    // «thread-safe lazy singleton».
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    // applicationContext — чтобы не держать Activity в памяти.
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    // Имя файла БД на устройстве.
                                    "kolpakov_books.db"
                            )
                            // allowMainThreadQueries — только для учебного проекта!
                            // В продакшене так делать нельзя: все операции с БД
                            // должны идти в фоновом потоке, иначе UI будет фризить.
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}