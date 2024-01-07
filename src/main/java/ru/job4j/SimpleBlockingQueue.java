package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Реализуйте шаблон Producer Consumer.
 * <p>
 * Для этого вам необходимо реализовать собственную версию bounded blocking queue.
 * Это блокирующая очередь, ограниченная по размеру. В данном шаблоне Producer помещает данные в очередь,
 * а Consumer извлекает данные из очереди.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;
    @GuardedBy("queue")
    private final Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
        synchronized (queue) {
            queue.offer(value);
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T value = null;
        while (value == null) {
            synchronized (queue) {
                value = queue.poll();
            }
            if (value == null) {
                synchronized (monitor) {
                    System.out.println("wait => " + Thread.currentThread().getName());
                    monitor.wait();
                }
            }
        }
        return value;
    }
}