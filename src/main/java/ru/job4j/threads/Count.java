package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * В этом уроке мы добавим библиотеку jcip, которая поможет отслеживать ошибки в многопоточном окружении.
 * <p>
 * - В заголовке класса указать аннотацию @ThreadSafe.
 * - Для поля состояния использовать аннотацию @GuardedBy.
 * - В аннотации GuardedBy - указать монитор.
 */
@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized void increment() {
        this.value++;
    }

    public synchronized int get() {
        return this.value;
    }
}