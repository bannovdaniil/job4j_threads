package ru.job4j.ref;

import ru.job4j.ref.model.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Программист решил расширить класс UserCache и добавил в него новый метод findAll.
 * Исправьте ошибку в этом коде.
 */
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public void add(User user) {
        users.put(user.getId(), user);
    }

    public User findById(int id) {
        if (!users.containsKey(id)) {
            throw new NoSuchElementException();
        }
        return User.of(id, users.get(id).getName());
    }

    public List<User> findAll() {
        return users.values().stream()
                .map(user -> User.of(user.getId(), user.getName()))
                .toList();
    }
}
