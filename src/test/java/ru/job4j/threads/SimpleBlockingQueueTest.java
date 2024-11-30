package ru.job4j.threads;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {
    @Test
    void whenConsumerPollBlankedQueueThenProducerAdd() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(5);
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer("first");
                        queue.offer("second");
                        queue.offer("third");
                        queue.offer("fourth");
                        queue.offer("fifth");
                        queue.offer("sixth");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        assertThat(queue.size()).isEqualTo(5);
        assertThat(queue.poll()).isEqualTo("second");
    }

    @Test
    void whenNotSucceed() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(5);
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer("first");
                        queue.offer("second");
                        queue.offer("third");
                        queue.offer("fourth");
                        queue.offer("fifth");
                        queue.offer("sixth");
                        queue.offer("seventh");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        assertThat(queue.poll()).isEqualTo("first");
        assertThat(queue.size()).isEqualTo(5);
        consumer.start();
        consumer.join();
        assertThat(queue.poll()).isEqualTo("third");
        assertThat(queue.size()).isEqualTo(4);
    }
}