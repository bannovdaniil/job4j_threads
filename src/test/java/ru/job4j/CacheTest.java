package ru.job4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тестирования сниглТона.
 */
class CacheTest {

    @DisplayName("Проверка последовательного создания объекта.")
    @Test
    void checkSingleToneInstance() {
        Cache expected = Cache.getInstance();

        Assertions.assertEquals(expected, Cache.getInstance());
    }
}