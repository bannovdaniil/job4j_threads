package ru.job4j.completablefuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CompletableFuture
 * 1. Дан каркас класса. Ваша задача подсчитать суммы по строкам и столбцам квадратной матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 * <p>
 * 2. Реализовать последовательную версию программы
 * 3. Реализовать асинхронную версию программы. i - я задача считает сумму по i столбцу и i строке
 * 4. Написать тесты
 */
public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i].rowSum += matrix[i][j];
                sums[j].colSum += matrix[i][j];
            }
        }
        showMatrix(matrix);
        return sums;
    }

    private static void showMatrix(int[][] matrix) {
        System.out.printf("=> size: %1$dx%1$d%n", matrix.length);
        int[] itog = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int sumR = 0;
            StringJoiner sj = new StringJoiner(", ", "[", "]");
            for (int j = 0; j < matrix[i].length; j++) {
                sumR += matrix[i][j];
                itog[j] += matrix[i][j];
                sj.add(String.valueOf(matrix[i][j]));
            }
            System.out.println(sj + " = " + sumR);
        }
        System.out.println(Arrays.toString(itog));
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        AtomicInteger rowIndex = new AtomicInteger();
        AtomicInteger colIndex = new AtomicInteger();

        List<CompletableFuture> cfs = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            cfs.add(CompletableFuture.runAsync(() -> {
                        int row = rowIndex.getAndIncrement();
                        sums[row].rowSum = sumRow(matrix, row).join();
                    }
            ));
            cfs.add(CompletableFuture.runAsync(() -> {
                        int col = colIndex.getAndIncrement();
                        sums[col].colSum = sumCol(matrix, col).join();
                    }
            ));
        }

        CompletableFuture.allOf(cfs.toArray(cfs.toArray(new CompletableFuture[0]))).get();

        showMatrix(matrix);
        return sums;
    }

    public static CompletableFuture<Integer> sumRow(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[row][i];
            }
            return sum;
        });
    }

    public static CompletableFuture<Integer> sumCol(int[][] matrix, int col) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[i][col];
            }
            return sum;
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

}