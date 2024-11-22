package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        Thread progress = new Thread(
                () -> {
                    try {
                        char[] process = new char[]{'-', '\\', '|', '/'};
                        while (!Thread.currentThread().isInterrupted()) {
                            for (Character p : process) {
                                Thread.sleep(500);
                                System.out.print("\r load: " + p);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        progress.start();
        try {
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress.interrupt();
    }

    public static void main(String[] args) {

        ConsoleProgress progress = new ConsoleProgress();
        progress.run();
    }
}
