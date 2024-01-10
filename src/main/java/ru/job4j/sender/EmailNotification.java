package ru.job4j.sender;

import ru.job4j.sender.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService
 * 1. Реализовать сервис для рассылки почты. Создайте класс EmailNotification.
 * 2. В классе будет метод emailTo(User user) - он должен через ExecutorService отправлять почту.
 * Так же добавьте метод close() - он должен закрыть pool. То есть в классе EmailNotification
 * должно быть поле pool, которые используется в emailTo и close().
 * 3. Модель User описывают поля username, email.
 * 4. Метод emailTo должен брать данные пользователя и подставлять в шаблон
 * subject = Notification {username} to email {email}.
 * body = Add a new event to {username}
 * <p>
 * 5. Создайте метод public void send(String subject, String body, String email) - он должен быть пустой.
 * 6. Через ExecutorService создайте задачу, которая будет создавать данные для пользователя и передавать их в метод send.
 */
public class EmailNotification {
    private static final String SUBJECT = "Notification {username} to email {email}.";
    private static final String BODY = "Add a new event to {username}";
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Должен через ExecutorService отправлять почту.
     * Метод emailTo должен брать данные пользователя и подставлять в шаблон
     *
     * @param user
     */
    public void emailTo(User user) {
        pool.submit(() -> {
                    String subject = SUBJECT
                            .replace("{username}", user.name())
                            .replace("{email}", user.email());
                    String body = BODY.replace("{username}", user.name());

                    send(subject, body, user.email());
                }
        );
    }

    /**
     *
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Отсылает почту, в этой задаче, по условиям он пустой.
     *
     * @param subject - тема письма
     * @param body    - тело письма
     * @param email   - адрес получателя.
     */
    public void send(String subject, String body, String email) {

    }

}
