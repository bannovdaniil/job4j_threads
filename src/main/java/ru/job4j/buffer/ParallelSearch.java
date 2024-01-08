package ru.job4j.buffer;

import ru.job4j.collections.SimpleBlockingQueue;

/**
 * Изменить код, так, что бы потребитель завершал свою работу. Код нужно изменить в методе main.
 */
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "consumer"
        );

        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3 && !Thread.currentThread().isInterrupted(); index++) {
                        try {
                            queue.offer(index);
                            System.out.println(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "producer"
        );

        consumer.start();
        producer.start();
        producer.join();
        consumer.interrupt();
    }
}