package ru.job4j.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

import java.util.concurrent.atomic.AtomicInteger;

class SimpleBlockingQueueTest {
    /**
     * 1. Стартует потребитель 1 он пытается достать числа из очереди
     * 2. Стартует производитель1 он добавляет числа в очередь от 0 до 100,
     * 3. дожидаемся окончания производителя1.
     * 4. Стартует производитель2 он добавляет числа в очередь от 0 до 100,
     * 5. Стартует потребитель 2 он пытается достать числа из очереди
     * Появляется конкуренция между потоками. поребитель1 и потребитель2
     * дожидаемся окончания работы производитель
     * к этому моменту очередь должна быть пустой, потребители все забрали.
     * 6.отправляем сигнал на прирывание.
     * 7.добавляем число которое не участвовало в процессе.
     * 8.пытаемся его достать. Если число удалось достать, значит тест прошел успешно.
     */
    @RepeatedTest(5)
    void offer(TestInfo testInfo) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        AtomicInteger countOffer = new AtomicInteger();
        AtomicInteger countPoll = new AtomicInteger();

        Runnable producer = () -> {
            for (int i = 0; i < 100; i++) {
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
                    queue.poll();
                    countPoll.incrementAndGet();
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

        producer1.start();
        consumer1.start();
        consumer2.start();
        producer1.join();

        producer2.start();
        consumer3.start();
        producer2.join();

        while (countOffer.get() != countPoll.get()) {
            System.out.println("\rwait - " + testInfo.getDisplayName());
        }

        Assertions.assertEquals(countOffer.get(), countPoll.get());
    }

}