package ru.job4j.pool;

import ru.job4j.threads.SimpleBlockingQueue;

import java.util.*;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();

    private final int size = Runtime.getRuntime().availableProcessors();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        Thread thread = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
            threads.add(thread);
            thread.start();
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}