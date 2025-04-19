package ru.job4j.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ParallelIndexSearchTest {
    @Test
    void whenArrSizeLessThan10() {
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(3, new Integer[]{1, 2, 3}, 0, 3);
        int result = search.compute();
        assertThat(result).isEqualTo(2);
    }

    @Test
    void whenArrSizeMoreThan10() {
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(10, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
                0, 13);
        int result = search.compute();
        assertThat(result).isEqualTo(9);
    }

    @Test
    void whenElementIsNotFound() {
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(11, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                0, 10);
        int result = search.compute();
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenSearchingStringElement() {
        ParallelIndexSearch<String> search = new ParallelIndexSearch<>("c", new String[]{"a", "b", "c", "d"}, 0, 4);
        int result = search.compute();
        assertThat(result).isEqualTo(2);
    }
}