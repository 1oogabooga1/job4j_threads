package ru.job4j.threads;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int current;
        int updated;
        do {
            current = count.get();
            updated = current + 1;
        } while (!count.compareAndSet(current, updated));
    }

    public int get() {
        return count.get();
    }
}