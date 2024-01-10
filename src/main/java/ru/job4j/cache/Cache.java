package ru.job4j.cache;

import ru.job4j.cache.exception.OptimisticException;
import ru.job4j.cache.model.Base;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * CAS методы потокобезопасной коллекции ConcurrentHashMap
 * <p>
 * 1. Реализуйте методы update, delete.
 * 2. Напишите модульные тесты. Они будут не многопоточные, а последовательные.
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    /**
     * Обновляет данные в кеше.
     * происходит увеличение значения версии в модели.
     *
     * @param model - модель данных
     * @throws OptimisticException - если версия в кеше не соответствует версии переданной модели
     */
    public void update(Base model) {
        memory.computeIfPresent(model.id(),
                (key, m) -> {
                    if (model.version() != m.version()) {
                        throw new OptimisticException("Version is not valid.");
                    }
                    return new Base(key, model.name(), m.version() + 1);
                }
        );
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}