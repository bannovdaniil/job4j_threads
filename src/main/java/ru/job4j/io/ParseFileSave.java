package ru.job4j.io;

import java.io.*;

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
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes(), 0, content.getBytes().length);
            o.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}