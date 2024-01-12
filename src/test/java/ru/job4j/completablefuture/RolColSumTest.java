package ru.job4j.completablefuture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.job4j.completablefuture.model.Sums;

import java.util.concurrent.ExecutionException;

class RolColSumTest {

    @DisplayName("Single thread")
    @ParameterizedTest
    @CsvSource({
            "1, '5', '5', '5'",
            "2, '3,4', '4,3', '1,2:3,1'",
            "3, '3,6,12', '8,7,6', '1,1,1:2,2,2:5,4,3'",
            "4, '4,8,12,14', '11,10,9,8', '1,1,1,1:2,2,2,2:3,3,3,3:5,4,3,2'"
    })
    void sum(int size, String expectedRowString, String expectedColString, String matrixString) {
        Sums[] expectedSums = getExpectedSums(size, expectedRowString, expectedColString);
        int[][] matrix = getMatrix(size, matrixString);

        Sums[] resultSum = RolColSum.sum(matrix);

        Assertions.assertArrayEquals(expectedSums, resultSum);
    }

    @DisplayName("CompletableFuture sum")
    @ParameterizedTest
    @CsvSource({
            "1, '5', '5', '5'",
            "2, '3,4', '4,3', '1,2:3,1'",
            "3, '3,6,12', '8,7,6', '1,1,1:2,2,2:5,4,3'",
            "4, '4,8,12,14', '11,10,9,8', '1,1,1,1:2,2,2,2:3,3,3,3:5,4,3,2'",
            "5, '5,10,20,15,19', '12,12,16,12,17', '1,1,1,1,1:2,2,2,2,2:3,3,3,3,8:5,4,3,2,1:1,2,7,4,5'"
    })
    void asyncSum(int size, String expectedRowString, String expectedColString, String matrixString) throws ExecutionException, InterruptedException {
        Sums[] expectedSums = getExpectedSums(size, expectedRowString, expectedColString);
        int[][] matrix = getMatrix(size, matrixString);

        Sums[] resultSum = RolColSum.asyncSum(matrix);

        Assertions.assertArrayEquals(expectedSums, resultSum);
    }

    private static Sums[] getExpectedSums(int size, String expectedRowString, String expectedColString) {
        Sums[] expectedSums = new Sums[size];

        String[] expectedRow = expectedRowString.split(",");
        String[] expectedCol = expectedColString.split(",");
        for (int i = 0; i < size; i++) {
            expectedSums[i] = new Sums();
            expectedSums[i].setColSum(Integer.parseInt(expectedCol[i]));
            expectedSums[i].setRowSum(Integer.parseInt(expectedRow[i]));
        }
        return expectedSums;
    }

    private static int[][] getMatrix(int size, String matrixString) {
        String[] row = matrixString.split(":");
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            String[] col = row[i].split(",");
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Integer.parseInt(col[j]);
            }
        }
        return matrix;
    }
}