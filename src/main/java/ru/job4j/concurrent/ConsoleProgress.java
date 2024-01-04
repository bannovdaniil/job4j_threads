package ru.job4j.concurrent;

/**
 * 5. Прерывание нити [#1019]
 * Thread.interrupt()
 * Thread.currentThread().isInterrupted()
 * <p>
 * если используются методы sleep(), join(), wait() или аналогичные временно блокирующие поток методы, то нужно в блоке catch вызвать прерывание.
 */
public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        final char[] process = new char[]{'-', '\\', '|', '/'};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.printf("\rload: %s", process[index++]);
                if (index >= process.length) {
                    index = 0;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }
}
