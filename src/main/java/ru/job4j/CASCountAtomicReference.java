package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference - класс для атомарных операций со ссылкой на объект.
 * Реализовать неблокирующий счетчик.
 */
@ThreadSafe
public class CASCountAtomicReference {
    private final AtomicReference<Integer> count;

    public CASCountAtomicReference(int count) {
        this.count = new AtomicReference<>();
        this.count.set(count);
    }

    public void increment() {
        count.updateAndGet(x -> x + 1);
    }

    public int get() {
        return count.get();
    }
}