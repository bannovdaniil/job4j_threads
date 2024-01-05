package ru.job4j;

/**
 * 1. Синхронизация общих ресурсов. [#1096]
 * synchronized
 * - В случае нестатического метода монитором будет объект этого класса.
 * - В случае со статическом методом монитором будет сам класс.
 * Это код содержит ошибку атомарности. Поправьте код, загрузите изменения в github.
 */
public final class Cache {
    private static Cache cache;

    public static synchronized Cache getInstance() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    private Cache() {
    }
}