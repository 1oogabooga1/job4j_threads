package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            for (Character p : process) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                System.out.print("\r load: " + p);
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        progress.interrupt();
    }
}
