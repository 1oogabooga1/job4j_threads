package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<V> extends RecursiveTask<Integer> {
    private final V toFind;

    private final V[] array;

    private final int start;

    private final int end;

    public ParallelIndexSearch(V toFind, V[] array, int start, int end) {
        this.toFind = toFind;
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        if (end - start >= 10) {
            int mid = (start + end) / 2;
            ParallelIndexSearch<V> leftSearch = new ParallelIndexSearch<>(toFind, array, start, mid);
            ParallelIndexSearch<V> rightSearch = new ParallelIndexSearch<>(toFind, array, mid, end);
            leftSearch.fork();
            rightSearch.fork();
            int left = leftSearch.join();
            int right = rightSearch.join();
            return (left != -1) ? left : right;
        } else {
            for (int i = start; i < end; i++) {
                if (toFind.equals(array[i])) {
                    result = i;
                }
            }
        }
        return result;
    }
}
