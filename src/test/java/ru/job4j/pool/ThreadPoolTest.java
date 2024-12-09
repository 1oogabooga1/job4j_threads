package ru.job4j.pool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class ThreadPoolTest {
    @Test
    void whenOneThreadWorks() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        AtomicInteger integer = new AtomicInteger(0);
        pool.work(integer::incrementAndGet);
        Thread.sleep(500);
        assertEquals(integer.get(), 1);
    }

    @Test
    void whenTwoThreadsWork() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        AtomicInteger integer = new AtomicInteger(0);
        pool.work(integer::incrementAndGet);
        pool.work(integer::incrementAndGet);
        pool.work(integer::incrementAndGet);
        pool.work(integer::incrementAndGet);
        Thread.sleep(500);
        assertThat(integer.get()).isEqualTo(4);
    }

    @Test
    void whenShutDown() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        AtomicInteger integer = new AtomicInteger();
        pool.work(integer::incrementAndGet);
        pool.shutdown();
        pool.work(integer::incrementAndGet);
        Thread.sleep(500);
        assertThat(integer.get()).isEqualTo(1);
    }
}
