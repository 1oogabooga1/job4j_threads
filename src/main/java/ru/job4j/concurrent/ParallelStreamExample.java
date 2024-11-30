package ru.job4j.concurrent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class ParallelStreamExample {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 100; i++) {
                            Thread.sleep(100);
                            System.out.print("load: " + i + "%");
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        for (int i = 100; i < 200; i++) {
                            Thread.sleep(100);
                            System.out.print("\r load: " + i + "%");
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        first.start();
        second.start();
    }
}
