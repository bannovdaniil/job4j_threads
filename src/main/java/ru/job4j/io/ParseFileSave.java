package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Поправьте код с ошибками в коде.
 * + Избавиться от get set за счет передачи File в конструктор.
 * - Ошибки в многопоточности. Сделать класс Immutable. Все поля final.
 * + Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.
 * + Нарушен принцип единой ответственности. Тут нужно сделать два класса.
 * - Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)
 */
public class ParseFileSave {
    private final File file;

    public ParseFileSave(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(content.getBytes(), 0, content.getBytes().length);
            outputStream.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}