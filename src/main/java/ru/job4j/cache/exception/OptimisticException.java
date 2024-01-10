package ru.job4j.cache.exception;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}