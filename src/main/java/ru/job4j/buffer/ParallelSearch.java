package ru.job4j.buffer;

import ru.job4j.collections.SimpleBlockingQueue;

/**
 * Изменить код, так, что бы потребитель завершал свою работу. Код нужно изменить в методе main.
 */
public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
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
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3 && !Thread.currentThread().isInterrupted(); index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    while (consumer.getState() != Thread.State.WAITING
                            && consumer.getState() != Thread.State.TERMINATED
                    ) {
                        try {
                            consumer.join(500);
                        } catch (InterruptedException e) {
                            consumer.interrupt();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();

    }
}
