package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Поправьте код с ошибками в коде.
 * + Избавиться от get set за счет передачи File в конструктор.
 * + Ошибки в многопоточности. Сделать класс Immutable. Все поля final.
 * + Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.
 * + Нарушен принцип единой ответственности. Тут нужно сделать два класса.
 * + Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)
 */
public class ParseFileGetContent {
    private final File file;

    public ParseFileGetContent(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContentWithPredicate(character -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithPredicate(character -> character < 0x80);
    }

    private synchronized String getContentWithPredicate(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            byte[] data = new byte[1024];
            int size = 0;
            while ((size = input.read(data, 0, data.length)) > 0) {
                for (int i = 0; i < size; i++) {
                    if (filter.test((char) data[i])) {
                        output.append((char) data[i]);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return output.toString();
    }
}