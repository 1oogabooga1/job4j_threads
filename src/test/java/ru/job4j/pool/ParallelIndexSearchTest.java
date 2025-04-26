package ru.job4j.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ParallelIndexSearchTest {
    @Test
    void whenArrSizeLessThan10() {
        int result = ParallelIndexSearch.search(new Integer[]{1, 2, 3}, 3);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void whenArrSizeMoreThan10() {
        int result = ParallelIndexSearch.search(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, 10);
        assertThat(result).isEqualTo(9);
    }

    @Test
    void whenElementIsNotFound() {
        int result = ParallelIndexSearch.search(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 11);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenSearchingStringElement() {
        int result = ParallelIndexSearch.search( new String[]{"a", "b", "c", "d"}, "c");
        assertThat(result).isEqualTo(2);
    }
}