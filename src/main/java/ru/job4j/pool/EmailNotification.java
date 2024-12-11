package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        executor.submit(
                () -> {
                    String subject = String.format("subject = Notification %s to email %s.",
                            user.username(), user.email());
                    String body = String.format("body = Add a new event to %s", user.username());
                    send(subject, body, user.email());
                }
        );
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
