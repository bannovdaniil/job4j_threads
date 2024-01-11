package ru.job4j.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class SimpleBlockingQueueTest {
    /**
     * Прежде чем останавливать потребителей, необходимо убедиться, что они вытащили все числа из очереди
     * while (countOffer.get() != expectedList.size())
     */
    @RepeatedTest(5)
    void offer(TestInfo testInfo) throws InterruptedException {
        List<Integer> expectedList = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        AtomicInteger countOffer = new AtomicInteger();

        Runnable producer = () -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    queue.offer(i);
                    countOffer.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    expectedList.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread consumer1 = new Thread(consumer, "consumer1");
        Thread consumer2 = new Thread(consumer, "consumer2");
        Thread consumer3 = new Thread(consumer, "consumer3");

        Thread producer1 = new Thread(producer, "producer1");
        Thread producer2 = new Thread(producer, "producer2");

        consumer1.start();
        producer1.start();
        producer1.join();

        consumer1.interrupt();
        consumer1.join();

        Assertions.assertEquals(countOffer.get(), expectedList.size());
  /*      while (countOffer.get() != expectedList.size()) {
            System.out.println("\rwait - " + testInfo.getDisplayName());
        }
*/
    }

}