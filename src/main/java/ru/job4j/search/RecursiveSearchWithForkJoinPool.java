package ru.job4j.search;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Реализовать параллельный поиск индекса в массиве объектов. В целях оптимизации,
 * если размер массива не больше 10, использовать обычный линейный поиск.
 * Метод поиска должен быть обобщенным.
 */
public class RecursiveSearchWithForkJoinPool<T> extends RecursiveTask<Integer> {
    private static final int FAST_SEARCH_RANGE = 10;
    private final transient T[] array;
    private transient T element;
    private int left;
    private int right;

    private RecursiveSearchWithForkJoinPool(T[] array, T element, int left, int right) {
        this.array = array;
        this.element = element;
        this.left = left;
        this.right = right;
    }

    public static <T> int search(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new RecursiveSearchWithForkJoinPool<>(array, element, 0, array.length - 1));
    }

    private int fastLineSearch() {
        int result = -1;
        for (int i = left; i <= right; i++) {
            if (Objects.equals(array[i], element)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (right - left <= FAST_SEARCH_RANGE) {
            return fastLineSearch();
        }
        int middle = (right - left) / 2;
        RecursiveSearchWithForkJoinPool leftSearch = new RecursiveSearchWithForkJoinPool(array, element, left, middle);
        leftSearch.fork();
        Integer resultLeft = (Integer) leftSearch.join();

        RecursiveSearchWithForkJoinPool rightSearch = new RecursiveSearchWithForkJoinPool(array, element, middle + 1, right);
        rightSearch.fork();
        Integer resultRight = (Integer) rightSearch.join();

        if (resultLeft == -1 && resultRight == -1) {
            return -1;
        } else if (resultLeft == -1) {
            return resultRight;
        } else if (resultRight == -1) {
            return resultLeft;
        }
        
        return Math.min(resultLeft, resultRight);
    }
}
