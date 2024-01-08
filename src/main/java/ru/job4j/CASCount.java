package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger - класс для атомарных операций со ссылкой на объект.
 * Реализовать неблокирующий счетчик.
 */
@ThreadSafe
public class CASCount {
    private final AtomicInteger count;

    public CASCount(int count) {
        this.count = new AtomicInteger(count);
    }

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }
}