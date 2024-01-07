package ru.job4j.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

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
    void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

        Runnable producer = () -> {
            for (int i = 0; i < 100; i++) {
                queue.offer(i);
            }
        };

        Runnable consumer = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int getNum = queue.poll();
                    System.out.println(Thread.currentThread().getName() + " getNum = " + getNum);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread consumer1 = new Thread(consumer, "consumer1");
        Thread consumer2 = new Thread(consumer, "consumer2");
        Thread producer1 = new Thread(producer, "producer1");
        Thread producer2 = new Thread(producer, "producer2");

        consumer1.start();
        producer1.start();
        producer1.join();

        producer2.start();
        consumer2.start();
        producer2.join();

        consumer1.interrupt();
        while (consumer2.getState() != Thread.State.WAITING) {
            Thread.sleep(1);
        }

        consumer2.interrupt();
        while (consumer2.getState() != Thread.State.TERMINATED) {
            Thread.sleep(1);
        }

        queue.offer(777);
        Assertions.assertEquals(777, queue.poll());
    }

}