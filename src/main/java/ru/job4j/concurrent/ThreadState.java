package ru.job4j.concurrent;

/**
 * 3. Состояние нити. [#229175]
 * NEW - нить создана, но не запущена.
 * RUNNABLE - нить запущена и выполняется.
 * BLOCKED - нить заблокирована.
 * WAITING - нить ожидает уведомления.
 * TIMED_WAITING - нить ожидает уведомление в течении определенного периода.
 * TERMINATED - нить завершила работу.
 * <p>
 * Thread.getState();
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.print("");
        }

        System.out.println("Работа завершенна.");
    }
}