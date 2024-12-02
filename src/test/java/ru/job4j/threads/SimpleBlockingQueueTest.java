package ru.job4j.threads;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

    @Test
    void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(i -> {
                    try {
                        queue.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                })
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}