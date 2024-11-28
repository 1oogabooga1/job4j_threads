package ru.job4j.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    private final String dir;

    public Wget(String url, int speed, String dir) {
        this.url = url;
        this.speed = speed;
        this.dir = dir;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(dir);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            int sum = 0;
            var time = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                sum += bytesRead;
                if (sum >= speed) {
                    long interval = System.currentTimeMillis() - time;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    sum = 0;
                    time = System.currentTimeMillis();
                }
                output.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url;
        int speed;
        String dir;
        if (args.length > 2 && !args[0].isEmpty()
                && !args[1].isEmpty() && !args[2].isEmpty()) {
            url = args[0];
            speed = Integer.parseInt(args[1]);
            dir = args[2];
        } else {
            throw new IllegalArgumentException();
        }
        Thread wget = new Thread(new Wget(url, speed, dir));
        wget.start();
        wget.join();
    }
}