package ru.job4j.linked;

/**
 * Правила создания Immutable объекта.
 * <p>
 * 1. Все поля отмечены final.
 * 2. Состояние объекта не изменяется после создания объекта.
 */
public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}