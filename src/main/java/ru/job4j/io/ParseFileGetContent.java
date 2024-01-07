package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        StringBuilder content = new StringBuilder();
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] data = new byte[1024];
            int size;
            while ((size = inputStream.read(data, 0, data.length)) != -1) {
                for (int i = 0; i < size; i++) {
                    if (filter.test((char) data[i])) {
                        content.append((char) data[i]);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return content.toString();
    }
}