package ru.job4j.ref;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.job4j.ref.model.User;

class UserCacheTest {
    private UserCache userCache;

    @BeforeEach
    void setUp() {
        userCache = new UserCache();
        userCache.add(new User(1, "First"));
        userCache.add(new User(2, "Second"));
    }

    @Test
    void add() {
        int expectedSize = 3;
        userCache.add(new User(3, "Third"));
        Assertions.assertEquals(expectedSize, userCache.findAll().size());
    }

    @ParameterizedTest
    @CsvSource({
            "Second, 2",
            "First, 1"
    })
    void findById(String expectedName, int id) {
        Assertions.assertEquals(expectedName, userCache.findById(id).getName());
    }

    @Test
    void findAll() {
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, userCache.findAll().size());
    }
}