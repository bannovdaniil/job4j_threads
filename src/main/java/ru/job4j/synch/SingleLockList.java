package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * В этой задаче Вам нужно создать коллекцию, которая будет корректно работать в многопоточный среде.
 *
 * @param <T> - элементы коллекции.
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("list")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public void add(T value) {
        synchronized (list) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    /**
     * Если мы будем работать с ним, то мы получаем ошибку в share visibility.
     * Самый простой и надежный способ сделать копию данных - это использовать snapshots (копирование всех элементов).
     */
    @Override
    public Iterator<T> iterator() {
        synchronized (list) {
            return copy(list).iterator();
        }
    }

    private List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}
