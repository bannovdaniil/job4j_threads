package ru.job4j.pool;

import ru.job4j.collections.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * 1. Инициализация пула должна быть по количеству ядер в системе.
 * tasks - это блокирующая очередь. Если в очереди нет элементов, то нить переводится в состояние Thread.State.WAITING.
 * <p>
 * Когда приходит новая задача, всем нитям в состоянии Thread.State.WAITING посылается сигнал проснуться и начать работу.
 * <p>
 * 2. Добавить конструктор, в котором создать нити в количестве size и добавить их в threads. Каждая нить должна запускать метод tasks.poll().
 * 3. Создать метод work(Runnable job) - этот метод должен добавлять задачи в блокирующую очередь tasks.
 * 4. Создать метод shutdown() - этот метод завершит все запущенные задачи.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Runnable job = () -> System.out.printf("My name is: %s , my status is: %s%n",
                Thread.currentThread().getName(),
                Thread.currentThread().getState()
        );

        for (int i = 0; i < 10; i++) {
            threadPool.work(job);
        }

        threadPool.shutdown();
    }
}