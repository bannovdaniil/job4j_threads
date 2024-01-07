package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.cash.model.Account;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализуйте код в классе AccountStorage. Класс AccountStorage должен работает в многопоточном окружении.
 * Допишите новые тесты на метод transfer.
 */
@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            accounts.put(account.id(), account);

            return accounts.containsKey(account.id());
        }
    }

    public void update(Account account) {
        synchronized (accounts) {
            accounts.put(account.id(), account);
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        Optional<Account> account;
        synchronized (accounts) {
            account = Optional.ofNullable(accounts.getOrDefault(id, null));
        }
        return account;
    }

    public void transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            Account accountFrom = getById(fromId).orElseThrow(
                    () -> new IllegalArgumentException("fromID not found"));
            Account accountTo = getById(toId).orElseThrow(
                    () -> new IllegalArgumentException("toID not found"));

            if (accountFrom.amount() < amount) {
                throw new IllegalStateException("Balance less then amount");
            }

            accounts.put(fromId, new Account(fromId, accountFrom.amount() - amount));
            accounts.put(toId, new Account(toId, accountTo.amount() + amount));
        }
    }
}