package ru.job4j.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * 2. Написать тесты на случаи:
 * <p>
 * - разные типы данных
 * - линейный и рекурсивный поиск (малый и большой размеры массива)
 * - элемент не найден
 */
class RecursiveSearchWithForkJoinPoolTest {

    @DisplayName("Search in String array")
    @ParameterizedTest
    @CsvSource({  /*   0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0*/
            "-2, 0,  '-2,-1'",
            "-1, 1,  '0,-1'",
            "111, 5,  '0,1,2,3,4,111,18,19,20,21'",
            "222, 10, '0,1,2,3,4,5,6,7,8,10,222,12,13,14,15,16,17,18,19,20,21'",
            "333, 17,  '0,1,2,3,4,5,6,7,8,1,1,3,4,5,6,1,8,333,20,21'",
            "444, 0,  '444,1,2,3,4,1,6,7,8,10,999,12,13,14,15,16,17,999,19,20,21'",
            "555, 18,  '0,1,2,3,4,5,6,7,8,10,9,12,13,14,15,16,17,18,555,20,21'",
            "666, 22,  '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,666'",
            "777, 13,  '0,1,2,3,4,5,6,7,8,9,10,9,12,777,14,15,16,17,18,19,20,21'",
            "888, 3,  '0,1,2,888,4,5,6,7,8,10,9,888,13,14,999,16,17,18,19,888,9999'",
            "999, -1, '0,1,2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21'"
    })
    void stringSearch(String element, int expected, String arrayString) {
        String[] array = arrayString.split(",");

        int result = RecursiveSearchWithForkJoinPool.search(array, element);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Search in Integer array")
    @ParameterizedTest
    @CsvSource({  /*  0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0*/
            "-2, 0,  '-2,-1'",
            "-1, 1,  '0,-1'",
            "111, 5,  '0,1,2,3,4,111,18,19,20,21'",
            "222, 10, '0,1,2,3,4,5,6,7,8,10,222,12,13,14,15,16,17,18,19,20,21'",
            "333, 17,  '0,1,2,3,4,5,6,7,8,1,1,3,4,5,6,1,8,333,20,21'",
            "444, 0,  '444,1,2,3,4,1,6,7,8,10,999,12,13,14,15,16,17,999,19,20,21'",
            "555, 18,  '0,1,2,3,4,5,6,7,8,10,9,12,13,14,15,16,17,18,555,20,21'",
            "666, 22,  '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,666'",
            "777, 13,  '0,1,2,3,4,5,6,7,8,9,10,9,12,777,14,15,16,17,18,19,20,21'",
            "888, 3,  '0,1,2,888,4,5,6,7,8,10,9,888,13,14,999,16,17,18,19,888,9999'",
            "999, -1, '0,1,2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21'"
    })
    void integerSearch(int element, int expectedPosition, String arrayString) {
        String[] stringArray = arrayString.split(",");
        Integer[] array = new Integer[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            array[i] = Integer.valueOf(stringArray[i]);
        }

        int result = RecursiveSearchWithForkJoinPool.search(array, element);

        Assertions.assertEquals(expectedPosition, result);
    }
}