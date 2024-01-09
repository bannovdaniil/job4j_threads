package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference - класс для атомарных операций со ссылкой на объект.
 * Реализовать неблокирующий счетчик.
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount(int count) {
        this.count = new AtomicReference<>();
        this.count.set(count);
    }

    public void increment() {
        Integer currentValue;
        do {
            currentValue = count.get();
        } while (!count.compareAndSet(currentValue, currentValue + 1));
    }

    public int get() {
        return count.get();
    }
}