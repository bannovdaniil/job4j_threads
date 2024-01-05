package ru.job4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DCLSingletonTest {

    @DisplayName("Проверка последовательного создания объекта.")
    @Test
    void checkSingleToneInstance() {
        DCLSingleton expected = DCLSingleton.getInstance();

        Assertions.assertEquals(expected, DCLSingleton.getInstance());
    }
}