package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * В этом задании нужно написать консольную программу - аналог wget.
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * <p>
 * 1. Доделайте каркас класса Wget.
 * 2. В метод main добавьте валидацию входных параметров.
 * 2. Загрузите код в репозиторий и оставьте ссылку на коммит.
 * <p>
 * Файлы качаются в папку DOWNLOAD_PATH, если папки нет она создается.
 * имя файла берется из URL если, имени нет тогда index.html
 * Алгоритм паузы потока:
 * 1. качаем пакет
 * 2. проверяем количество скаченного привысило заданную величину?
 * 3. если ДА
 * 3.1. Расчитываем разницу времени, (1_000.0d * receivedSize / speed) - receivedTime / 1_000_000.0d;
 * 3.2. Проверяем потраченное время больше чем 1 миллисекунда?
 * 3.3. ожидаем расчитаное время
 * 3.4. обнуляем счетчики.
 * 5. перехоиди в 1.
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final String DOWNLOAD_PATH = "download" + File.separator;

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
        File file = getFileFromURL(url);
        long startAt = System.currentTimeMillis();

        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)
        ) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            long receivedSize = 0L;
            long size = 0L;
            long receivedTime = 0L;
            long startDownloadTime = System.nanoTime();
            while (!Thread.currentThread().isInterrupted() && (bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long downloadAt = System.nanoTime();

                output.write(dataBuffer, 0, bytesRead);

                receivedSize += bytesRead;
                size += bytesRead;
                receivedTime += (System.nanoTime() - downloadAt);

                System.out.printf("\rLoading: Read %d bytes : %d nano. ", receivedSize, receivedTime);
                if (receivedSize >= speed) {
                    sleepProcess(receivedTime, receivedSize);
                    receivedSize = 0;
                    receivedTime = 0;
                }
            }
            sleepProcess(receivedTime, receivedSize);
            long time = System.nanoTime() - startDownloadTime;
            System.out.printf("%nSize: %d, Time: %d, Download speed: %.4f%n", size, time, 1_000_000_000.0d * size / time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleepProcess(long receivedTime, long receivedSize) {
        double sleepTime = (1_000.0d * receivedSize / speed) - receivedTime / 1_000_000.0d;
        if (sleepTime > 0) {
            System.out.printf("Sleep time: %f ", sleepTime);
            try {
                Thread.sleep((long) sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private File getFileFromURL(String url) {
        String fileName = "file.tmp";
        try {
            fileName = new File(new URI(url).getPath()).getName();
            if (fileName.isEmpty()) {
                fileName = "index.html";
            }
        } catch (URISyntaxException e) {
        }
        createPathFolder(DOWNLOAD_PATH);
        return new File(DOWNLOAD_PATH + fileName);
    }

    private void createPathFolder(String downloadPath) {
        File path = new File(downloadPath);
        if (!path.exists()) {
            path.mkdirs();
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
