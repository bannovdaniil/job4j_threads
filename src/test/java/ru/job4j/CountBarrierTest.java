package ru.job4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

class CountBarrierTest {

    /**
     * await - стартует первым, и находится в ожидании пока счетчик достигнет 4х.
     * count2 - стартует вторым, увеличивает счетчик на 2.
     * <p>
     * в это время проверяем, что и count1 и await не закончили свою работу,
     * а все также ожидают счетчика == 4,
     * <p>
     * - статус await отличный от TERMINATED.
     * - count1 в статусе NEW
     * <p>
     * запускаем count1 и ждем его завершения.
     * ждем завершения await
     * в конце теста все потоки должны перейти в статус TERMINATED
     */
    @RepeatedTest(100)
    void count(TestInfo testInfo) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(4);

        Thread await = new Thread(() -> {
            System.out.println("Await. I'm start." + testInfo.getDisplayName());
            countBarrier.await();
        }, "Await");

        Thread count1 = new Thread(() -> {
            countBarrier.count();
            countBarrier.count();
            countBarrier.await();
        }, "Count 1");

        Thread count2 = new Thread(() -> {
            countBarrier.count();
            countBarrier.count();
        }, "Count 2");

        await.start();
        count2.start();

        count2.join();

        Assertions.assertNotEquals(Thread.State.TERMINATED, await.getState());
        Assertions.assertEquals(Thread.State.NEW, count1.getState());

        count1.start();
        count1.join();
        await.join();

        Assertions.assertEquals(Thread.State.TERMINATED, count1.getState());
        Assertions.assertEquals(Thread.State.TERMINATED, count2.getState());
        Assertions.assertEquals(Thread.State.TERMINATED, await.getState());
    }
}