package ru.job4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class CASCountTest {

    @RepeatedTest(5)
    void doInThread() throws InterruptedException {
        CASCount count = new CASCount(0);

        Runnable increment = () -> {
            for (int i = 0; i < 10000; i++) {
                count.increment();
            }
        };

        Thread one = new Thread(increment);
        Thread two = new Thread(increment);
        Thread three = new Thread(increment);

        one.start();
        two.start();
        three.start();

        one.join();
        two.join();
        three.join();

        Assertions.assertEquals(30000, count.get());
    }

}