package ru.job4j.threads;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

class CountTest {

    /**
     * Алгоритм проверки:
     * - Запускаем нити.
     * - Заставляем главную нить дождаться выполнения наших нитей.
     * - Проверяем результат.
     */
    @RepeatedTest(5)
    void whenExecute2ThreadThen2() throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);

        first.start();
        second.start();

        first.join();
        second.join();

        Assertions.assertEquals(2, count.get());
    }
}