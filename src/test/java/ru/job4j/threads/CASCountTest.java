package ru.job4j.threads;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CASCountTest {

    @Test
    void whenTwoThreadIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                count::increment
        );
        Thread second = new Thread(
                    count::increment
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(2);
    }

    @Test
    void whenThreeThreadIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                count::increment
        );
        Thread second = new Thread(
                count::increment
        );
        Thread third = new Thread(
                count::increment
        );
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(count.get()).isEqualTo(3);
    }
}