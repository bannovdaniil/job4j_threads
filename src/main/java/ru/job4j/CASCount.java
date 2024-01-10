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
        this.count = new AtomicInteger();
        this.count.set(count);
    }

    public void increment() {
        int currentValue;
        do {
            currentValue = count.get();
        } while (!count.compareAndSet(currentValue, currentValue + 1));
    }

    public int get() {
        return count.get();
    }
}