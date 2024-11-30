package ru.job4j.threads;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        while (count < total) {
            try {
                monitor.wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
