package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Sums sums)) {
                return false;
            }
            return getRowSum() == sums.getRowSum() && getColSum() == sums.getColSum();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRowSum(), getColSum());
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int colSum = 0;
            int rowSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            result[i] = new Sums(rowSum, colSum);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        CompletableFuture<Sums>[] futures = new CompletableFuture[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            futures[i] = CompletableFuture.supplyAsync(
                    () -> {
                        int colSum = 0;
                        int rowSum = 0;
                        for (int j = 0; j < matrix.length; j++) {
                            rowSum += matrix[index][j];
                            colSum += matrix[j][index];
                        }
                        return new Sums(rowSum, colSum);
                    }
            );
        }
        for (int i = 0; i < matrix.length; i++) {
            try {
                result[i] = futures[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
