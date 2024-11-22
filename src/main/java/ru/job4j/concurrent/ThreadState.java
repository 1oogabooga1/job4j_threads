package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        getNewThread();
        getNewThread();
        System.out.println("Работа завершена");
    }

    private static void getNewThread() {
        Thread second = new Thread(
                () -> { }
        );
        System.out.println(second.getState());
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println(second.getState());
        }
        System.out.println(second.getState());
    }
}
