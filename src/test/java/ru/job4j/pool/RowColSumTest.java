package ru.job4j.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RowColSumTest {
    @Test
    public void whenSyncSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] expected = {
                new RowColSum.Sums(6, 12),
                new RowColSum.Sums(15, 15),
                new RowColSum.Sums(24, 18)
        };

        RowColSum.Sums[] result = RowColSum.sum(matrix);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    public void whenAsyncSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] expected = RowColSum.sum(matrix);
        RowColSum.Sums[] result = RowColSum.asyncSum(matrix);

        assertThat(expected).isEqualTo(result);
    }
}