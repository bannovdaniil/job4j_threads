package ru.job4j.concurrent;

/**
 * 4. Режим ожидания. [#231217]
 * 1. Создайте класс ru.job4j.coccurent.Wget и метод main.
 * 2. В методе main необходимо симулировать процесс загрузки. Для этого воспользуйтесь особенностью вывода на консоль.
 * 3. Создайте нить внутри метода main. В теле метода создайте цикл от 0 до 100. Через 1 секунду выводите на консоль
 * информацию о загрузке. Вывод должен быть с обновлением строки.
 */
public class Wget {
    public static void main(String[] args) {
        Thread wget = new Thread(
                () -> {
                    for (int index = 0; index <= 100; index++) {
                        System.out.print("\rLoading : " + index + "%");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        wget.start();
    }
}
