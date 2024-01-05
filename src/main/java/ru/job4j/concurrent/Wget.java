package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * В этом задании нужно написать консольную программу - аналог wget.
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * <p>
 * 1. Доделайте каркас класса Wget.
 * 2. В метод main добавьте валидацию входных параметров.
 * 2. Загрузите код в репозиторий и оставьте ссылку на коммит.
 * <p>
 * Алгоритм паузы потока:
 * 1. качаем пакет
 * 2. находим скорость с которой скачали блок. (количество байт / время)
 * 3. если скорость меньше или такаяже как заданная, переходим в 1.
 * 4. считаем время задержки потока: (расчетное количество - заданное количество) / скорость скачивания. U = s / t ; t = s / U;
 * 4.1. заданное количество: speed т.к. время = 1 ; U = s / 1; => s = U;
 * 4.2. расчетное количество: s = U * t;  =>  s = U * 1; s = U;
 * 5. ожидаем расчитаное время
 * 6. переходим в 1.
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    /**
     * @param url   - ссылка для скачиваемого файла.
     * @param speed - миллисекунды.
     */
    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        File file = new File("tmp.xml");
        long startAt = System.currentTimeMillis();

        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)
        ) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[512];
            int bytesRead;

            while (!Thread.currentThread().isInterrupted() && (bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();

                output.write(dataBuffer, 0, bytesRead);

                long operationTime = (System.nanoTime() - downloadAt);
                long downloadSpeed = bytesRead * 1_000_000 / operationTime;
                long sleepTime = 0;
                if (downloadSpeed > speed) {
                    sleepTime = (downloadSpeed - speed) / speed;
                }
                System.out.printf("Loading: Read %d bytes : %d nano. Sleep time: %d%n", bytesRead, operationTime, sleepTime);
                sleepProcess(sleepTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleepProcess(long sleepTime) {
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args == null
                || args.length != 2
                || args[0] == null
                || args[1] == null
                || !args[0].matches("^http[s]?://.*$")
                || !args[1].matches("\\d+")) {
            throw new IllegalArgumentException("Need two parameters: URL and speed");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
