package ru.job4j;

/**
 * volatile
 * чтение и запись переменной будет происходить только из RAM памяти процессора. Без кеширования.
 *
 * синглтон - double check locking
 */
public final class DCLSingleton {

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    private DCLSingleton() {
    }

}