package ru.job4j.cash;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.job4j.cash.model.Account;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @DisplayName("Get not exists id.")
    @Test
    void whenGetNoExistIdThenException() {
        AccountStorage storage = new AccountStorage();
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenAdd() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        Account firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        Account firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @DisplayName("When if don't exist then Exception")
    @ParameterizedTest
    @CsvSource({
            "1, 2, 100, 100, 20, 80, 120",
            "2, 1, 210, 110, 30, 140, 180"
    })
    void whenTransfer(int fromId, int toId,
                      int fromAmount, int toAmount,
                      int transferAmount,
                      int expectedFrom, int expectedTo) {

        AccountStorage storage = new AccountStorage();

        storage.add(new Account(fromId, fromAmount));
        storage.add(new Account(toId, toAmount));
        storage.transfer(fromId, toId, transferAmount);

        Account firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));

        assertThat(firstAccount.amount()).isEqualTo(expectedFrom);
        assertThat(secondAccount.amount()).isEqualTo(expectedTo);
    }

    @DisplayName("When if don't exist then IllegalArgumentException")
    @ParameterizedTest
    @CsvSource({
            "3, 1, 100, 'fromID not found'",
            "1, 3, 100, 'toID not found'"
    })
    void whenTransferAndIdNotFoundThenException(int fromId, int toId, int amount, String expectMessage) {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));

        Assertions.assertThatThrownBy(() -> storage.transfer(fromId, toId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectMessage);
    }

    @DisplayName("When balance less amount Then IllegalStateException")
    @Test
    void whenBalanceLessThenException() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 1));
        storage.add(new Account(2, 2));

        Assertions.assertThatThrownBy(() -> storage.transfer(1, 2, 100))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Balance less then amount");
    }

}