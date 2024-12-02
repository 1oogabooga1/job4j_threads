package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final int size;

    @GuardedBy("this")
    private final Object monitor = this;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
            monitor.wait();
        }
        queue.add(value);
        monitor.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            monitor.wait();
        }
        monitor.notifyAll();
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
